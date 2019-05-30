package com.bbcircle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.mediapicker.PickerActivity;
import com.payencai.library.mediapicker.PickerConfig;
import com.payencai.library.mediapicker.entity.Media;
import com.payencai.library.util.VideoUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class CarsShowRepublishActivity extends NoHttpBaseActivity {
    @BindView(R.id.editText1)
    EditText et_title;
    @BindView(R.id.description)
    TextView et_detail;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.rl_video)
    RelativeLayout rl_video;
    @BindView(R.id.cameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.text1)
    TextView tv_pub;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    String content;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_show_publish);
        initView();
    }
    ArrayList<Media> defaultSelect = new ArrayList<>();
    private void chooseVideo() {
        Intent intent = new Intent(CarsShowRepublishActivity.this, PickerActivity.class);
        intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
        long maxSize = 10485760L;//long long long long类型
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 10MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, 1);  //default 40 (Optional)
        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, defaultSelect); //(Optional)默认选中的照片
        startActivityForResult(intent, 2);
    }
    String video;
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
                            Glide.with(CarsShowRepublishActivity.this).load(data).into(iv_img);
                            rl_video.setVisibility(View.VISIBLE);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
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
        mWebView.setWebViewClient(new MyWebViewClient(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && data != null) {
            defaultSelect = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            //Log.e("select", "select.size" + defaultSelect.get(0).extension);
            if(defaultSelect.size()>0){
                time=timeParse(defaultSelect.get(0).time);
                Bitmap bitmap = VideoUtil.voidToFirstBitmap(defaultSelect.get(0).path);
                String path = VideoUtil.bitmapToStringPath(CarsShowRepublishActivity.this, bitmap);
                Log.e("path", path);
                File fileimg = new File(path);
                upImage(PlatformContans.Commom.uploadImg,fileimg);
                File filevideo = new File(defaultSelect.get(0).path);
                upLoadVideo(PlatformContans.Commom.uploadVideo, filevideo);
            }
        }
        if(requestCode==3){
            if(data!=null){

                url=getHtmlData(data.getStringExtra("data"));
                mWebView.loadData(url, "text/html;charset=utf-8","utf-8");
                mWebView.setVisibility(View.VISIBLE);
                mWebView.reload();
                content=data.getStringExtra("data");
                //description.setText(data.getStringExtra("data"));
            }
        }
    }
    /**
     * 获取本地视频的第一帧
     *
     * @param localPath
     * @return
     */
//    public static Bitmap getLocalVideoBitmap(String localPath) {
//        Bitmap bitmap = null;
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        try {
//            //根据文件路径获取缩略图
//            retriever.setDataSource(localPath);
//            //获得第一帧图片
//            bitmap = retriever.getFrameAtTime();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } finally {
//            retriever.release();
//        }
//        return bitmap;
//    }

    String image;
    String time;


    private void postMsg() {
        Map<String, Object> params = new HashMap<>();
        params.put("title", et_title.getEditableText().toString());
        params.put("image", image);
        params.put("content", content);
        params.put("video", video);
        params.put("time", time);
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addCarShowCircle, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ActivityAnimationUtils.commonTransition(CarsShowRepublishActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                // ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplyDescriptionActivity.class, ActivityConstans.Animation.FADE);
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    public void upLoadVideo(String url, File file) {
        OkHttpClient mOkHttpClent = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "file",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file));
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
                    video = data;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "汽车秀");
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVideo();
            }
        });
        et_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CarsShowRepublishActivity.this,PublishInputActivity.class),3);
            }
        });
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin)
                {
                    if (TextUtils.isEmpty(et_title.getText().toString())) {
                        return;
                    }
                    if (TextUtils.isEmpty(et_detail.getText().toString())) {
                        return;
                   }
                    if (TextUtils.isEmpty(video)) {
                        return;
                    }
                    if (TextUtils.isEmpty(image)) {
                        return;
                    }


                    postMsg();
                } else {
                    startActivity(new Intent(CarsShowRepublishActivity.this, RegisterActivity.class));
                }
            }
        });
    }

    //
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

        }
    }
}
