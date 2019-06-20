package com.example.yunchebao.drive.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.yunchebao.MyApplication;
import com.comment.EvaluationChoiceImageView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;

import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import com.payencai.library.util.ToastUtil;
import com.tool.FileUtil;
import com.tool.GlideImageEngine;

import com.tool.StringUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class AddOrderCommentActivity extends AppCompatActivity {
    @BindView(R.id.addimgs)
    EvaluationChoiceImageView mEvaluationChoiceImageView;
    @BindView(R.id.sb_score)
    SimpleRatingBar sb_score;
    @BindView(R.id.sb_driver)
    SimpleRatingBar sb_driver;
    @BindView(R.id.et_comment)
    EditText et_comment;
    List<Uri> mSelected;
    List<String> images;
     String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_comment);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id= getIntent().getStringExtra("id");
        initView();
    }
    private void addComment(){
        String imgs = StringUtils.listToString2(images,',');
        String comment=et_comment.getEditableText().toString();
        float shopScore=  sb_score.getRating();
        float driverScore=  sb_driver.getRating();
        String orderId=id;
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        params.put("content",comment);
        params.put("shopScore",shopScore);
        params.put("driverScore",driverScore);
        params.put("imgs",imgs);
        HttpProxy.obtain().post(PlatformContans.SubstituteDriving.addSubstituteDrivingComment, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ToastUtil.showToast(AddOrderCommentActivity.this,"评价成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 188 && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
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
                    final String data = object.getString("data");
                    images.add(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEvaluationChoiceImageView.addImage(file.getAbsolutePath());

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initView() {
        images=new ArrayList<>();
        mEvaluationChoiceImageView.setOnClickAddImageListener(new EvaluationChoiceImageView.OnClickAddImageListener() {
            @Override
            public void onClickAddImage() {
                Matisse.from(AddOrderCommentActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideImageEngine())
                        .forResult(188);
            }
        });
        mEvaluationChoiceImageView.setOnClickDeleteImageListener(new EvaluationChoiceImageView.OnClickDeleteImageListener() {
            @Override
            public void onClickDeleteImage(int position) {

                 images.remove(position);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_submit})
    void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_submit:
                if(TextUtils.isEmpty(et_comment.getEditableText().toString())){
                    ToastUtil.showToast(this,"评论内容不能为空");
                    return;
                }
                addComment();
                break;
        }
    }
}
