package com.vipcenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class IDCardCertificationActivity extends NoHttpBaseActivity {
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.iv_id1)
    ImageView iv_id1;
    @BindView(R.id.iv_id2)
    ImageView iv_id2;
    @BindView(R.id.tv_submit)
            TextView tv_submit;
    String image1, image2;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.id_card_certification_layout);
        initView();
        getIDInfo();
    }

    private void getIDInfo() {
        String token =MyApplication.token;
        HttpProxy.obtain().get(PlatformContans.User.getIdentityVerification, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        jsonObject = jsonObject.getJSONObject("data");
                        if(jsonObject!=null){
                            et_name.setText(jsonObject.getString("name"));
                            String idno=jsonObject.getString("idNo");
                            if(!TextUtils.isEmpty(idno))
                            et_number.setText(idno.substring(0,2)+"*******"+idno.substring(idno.length()-2,idno.length()));
                            et_name.setEnabled(false);
                            et_name.setFocusable(false);
                            et_number.setEnabled(false);
                            et_number.setFocusable(false);
                            ll_add.setVisibility(View.GONE);
                        }

                    } else {
                        ll_add.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "身份证认证");
        ButterKnife.bind(this);
        iv_id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                startCategery();
            }
        });
        iv_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                startCategery();
            }
        });
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput())
                   postMsg();
            }
        });
    }
   private boolean checkInput(){
        boolean isOk=true;
        if(TextUtils.isEmpty(et_name.getEditableText().toString())){
            isOk=false;
            ToastUtil.showToast(this,"姓名不能为空");
            return isOk;
        }
       if(TextUtils.isEmpty(et_number.getEditableText().toString())){
           isOk=false;
           ToastUtil.showToast(this,"身份证号码不能为空");
           return isOk;
       }
       if(TextUtils.isEmpty(image1)||TextUtils.isEmpty(image2)){
           isOk=false;
           ToastUtil.showToast(this,"身份证照片不能为空");
           return isOk;
       }
        return  isOk;
   }
    /**
     * 裁剪图片
     */
    Uri photoOutputUri;

    private void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));
        startActivityForResult(cropPhotoIntent, 2);
    }

    private void postMsg() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", et_name.getEditableText().toString());
        params.put("idNo", et_number.getEditableText().toString());
        params.put("positiveImg", image1);
        params.put("backImg", image2);
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.User.addIdentityVerification, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if(code==0){
                        ToastUtil.showToast(IDCardCertificationActivity.this,"认证成功");
                        finish();
                    }else{
                        ToastUtil.showToast(IDCardCertificationActivity.this,msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplyDescriptionActivity.class, ActivityConstans.Animation.FADE);
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            cropPhoto(data.getData());
        }
        if (requestCode == 2 && data != null) {
            File file = new File(photoOutputUri.getPath());
            if (file.exists()) {
                upImage(PlatformContans.Commom.uploadImg, file);
            } else {
                Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 1);
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
                Log.e("upload", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.e("upload", "onResponse: " + string);
                try {
                    JSONObject object = new JSONObject(string);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    if (flag == 1) {
                        image1 = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(IDCardCertificationActivity.this).load(image1).into(iv_id1);
                            }
                        });

                    } else {
                        image2 = data;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(IDCardCertificationActivity.this).load(image2).into(iv_id2);
                            }
                        });

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
