package com.vipcenter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;
import com.tool.UIControlUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class FeedbackSubmitActivity extends NoHttpBaseActivity {
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.et_phone)
    EditText et_phone;
    String image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_submit_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"意见反馈");
        ButterKnife.bind(this);
    }
    public void upImage(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image",
                        RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("upload", "onResponse: error" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    image = object.getString("data");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(FeedbackSubmitActivity.this).load(image).into(iv_img);

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 188 && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            File fileByUri = FileUtil.getFileByUri(Matisse.obtainResult(data).get(0), this);
//            压缩文件
            Luban.with(this)
                    .load(fileByUri)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            //evaluationBeans.get(mTempPosition).getEvaluationImages().add(0,file);

                            upImage(PlatformContans.Commom.uploadImg, file);
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }).launch();

        }
    }
    @OnClick({R.id.back,R.id.nextBtn,R.id.iv_img})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.nextBtn:
                String tel=et_phone.getEditableText().toString();
                String content=et_content.getEditableText().toString();
                if(TextUtils.isEmpty(tel)){
                    ToastUtil.showToast(this,"请输入手机号");
                    return;
                }
                if(TextUtils.isEmpty(content)){
                    ToastUtil.showToast(this,"请输入内容");
                    return;
                }

                addComment(tel,content);
                break;
            case R.id.iv_img:
                Matisse.from(FeedbackSubmitActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideImageEngine())
                        .forResult(188);
                break;
        }
    }
    private void addComment(String tel,String content){
        Map<String,Object> params=new HashMap<>();
        params.put("image",image);
        params.put("content",content);
        params.put("tel",tel);
        HttpProxy.obtain().post(PlatformContans.FeedBack.addFeedBack, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ToastUtil.showToast(FeedbackSubmitActivity.this,"反馈成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
