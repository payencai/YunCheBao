package com.bbcircle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.RegisterActivity;

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
 * Created by sdhcjhss on 2017/12/23.
 */

public class DriverFriendsRepublishActivity extends NoHttpBaseActivity {
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.cameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.editText1)
    EditText et_name;
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.description)
    TextView et_detail;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_friends_publish);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategery();
            }
        });
        et_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(DriverFriendsRepublishActivity.this,PublishInputActivity.class),3);
            }
        });
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    static class MyWebViewClient extends WebViewClient {

        private Activity activity;

        public MyWebViewClient(Activity activity) {
            this.activity = activity;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWebView.setWebViewClient(new SelfDrivingRepublishActivity.MyWebViewClient(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

    }
    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            cropPhoto(data.getData());
//            iv_img.setImageURI(data.getData());
//            iv_img.setVisibility(View.VISIBLE);
        }
        if (requestCode == 2 && data != null) {
           // cropPhoto(data.getData());

            File file = new File(photoOutputUri.getPath());
            if (file.exists()) {
                //Bitmap bitmap = BitmapFactory.decodeFile(photoOutputUri.getPath());
                //server_head.setImageBitmap(bitmap);
                upImage(PlatformContans.Commom.uploadImg, file);
            } else {
                Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
            }

        }
        if(requestCode==3){
            if(data!=null){
                mWebView.loadData(getHtmlData(data.getStringExtra("data")), "text/html;charset=utf-8","utf-8");
                mWebView.setVisibility(View.VISIBLE);
                mWebView.reload();
                content=data.getStringExtra("data");
                //description.setText(data.getStringExtra("data"));
            }
        }
    }

    /**
     * 裁剪图片
     */
    Uri photoOutputUri;
    String image;
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
                    image = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(DriverFriendsRepublishActivity.this).load(data).into(iv_img);
                            iv_img.setVisibility(View.VISIBLE);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void postMsg() {
        Map<String, Object> params = new HashMap<>();
        params.put("title", et_title.getEditableText().toString());
        params.put("image", image);
        params.put("content",content);
        params.put("circleName",et_name.getEditableText().toString());
        params.put("address", MyApplication.getaMapLocation().getAddress());
        Log.e("result",params.toString());
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addCarCommunicationCircle, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ActivityAnimationUtils.commonTransition(DriverFriendsRepublishActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                // ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplyDescriptionActivity.class, ActivityConstans.Animation.FADE);
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }




    @OnClick({R.id.back, R.id.text1})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.text1:
                if (MyApplication.isLogin)
                {
                    if (TextUtils.isEmpty(et_title.getText().toString())) {
                        return;
                    }

                    if (TextUtils.isEmpty(et_name.getText().toString())) {
                        return;
                    }
                    postMsg();
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
               // ActivityAnimationUtils.commonTransition(DriverFriendsRepublishActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                break;

        }
    }
}
