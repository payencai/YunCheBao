package com.example.yunchebao.babycircle.selfdrive;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bbcircle.DrivingSelfReplaySuccessActivity;
import com.bbcircle.PublishInputActivity;
import com.bbcircle.ReplySettingActivity;
import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.BottomMenuDialog;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class SelfDrivingRepublishActivity extends NoHttpFragmentBaseActivity implements OnDateSetListener {
    private Context ctx;
    TimePickerDialog mDialogYearMonth1, mDialogYearMonth2;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @BindView(R.id.time1)
    TextView time1Text;
    @BindView(R.id.time2)
    TextView time2Text;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.et_title)
    TextView et_title;
    @BindView(R.id.cameraIcon)
    ImageView cameraIcon;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    int timeTag = 1;
    String image;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_driving_publish);
        initView();
        initWebView();
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
    public static int compare_date(String DATE1, String DATE2) {


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
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
    private void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ctx = this;
        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCategery();
            }
        });
        initTimePickerView();
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelfDrivingRepublishActivity.this, PublishInputActivity.class);
                intent.putExtra("html",content);
                startActivityForResult(intent,3);
            }
        });
    }

    private void startCategery() {
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(mIntent, 1);
    }
    String url;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            cropPhoto(data.getData());
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
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mWebView.reload();
                mWebView.setVisibility(View.VISIBLE);
                content=data.getStringExtra("data");
                //description.setText(data.getStringExtra("data"));
            }
        }
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
                            Glide.with(SelfDrivingRepublishActivity.this).load(data).into(iv_img);
                            iv_img.setVisibility(View.VISIBLE);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private String[] items = new String[]{"设置地点（线下活动）", "线上活动"};
    private BottomMenuDialog bottomDialog;

    private void alertPanel(Context ctx) {
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.addMenu(items[0], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地选择
//                picSelect();
                bottomDialog.dismiss();
            }
        });
        builder.addMenu(items[1], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
//                picCamera();
                bottomDialog.dismiss();
            }
        });
        bottomDialog = builder.create();
        bottomDialog.show();
    }

    private void initTimePickerView() {
        mDialogYearMonth1 = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("活动开始时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .setWheelItemTextSize(12)
                .build();

        mDialogYearMonth2 = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("活动结束时间")
                .setCyclic(false)
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .setWheelItemTextSize(12)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
        String text = getDateToString(millseconds);
        switch (timeTag) {
            case 1:
                time1Text.setText(text);
                break;
            case 2:
                time2Text.setText(text);
                break;
        }
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    private void alertTimePicker(int type) {
        switch (type) {
            case 1:
                mDialogYearMonth1.show(getSupportFragmentManager(), "all");
                break;
            case 2:
                mDialogYearMonth1.show(getSupportFragmentManager(), "all");
                break;
        }

    }

    private void postMsg() {
        String start=time1Text.getText().toString() + ":00";
        String end= time2Text.getText().toString() + ":00";
        if(compare_date(start,end)>=0){
            ToastUtil.showToast(this,"开始时间不能大于结束时间！");
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        String now=simpleDateFormat.format(date);
        if(compare_date(now,start)>=0){
            ToastUtil.showToast(this,"发布时间应不能选择小于今天的");
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("title", et_title.getEditableText().toString());
        params.put("image", image);
        params.put("content", content);
        params.put("startTime", start);
        params.put("endTime",end);
        params.put("address", MyApplication.getaMapLocation().getAddress());
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.BabyCircle.addSelfDrivingCircle, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                //ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                finish();
                // ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplyDescriptionActivity.class, ActivityConstans.Animation.FADE);
            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    @OnClick({R.id.back, R.id.text1, R.id.text2, R.id.settingLay, R.id.addressLay, R.id.description, R.id.time1, R.id.time2})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.settingLay:
                ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplySettingActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.addressLay:
                alertPanel(ctx);
                break;
            case R.id.text1:
                if (MyApplication.isLogin) {
                    if (TextUtils.isEmpty(et_title.getText().toString().trim())) {
                        ToastUtil.showToast(this,"标题不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(time1Text.getText().toString())) {
                        ToastUtil.showToast(this,"时间不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(time2Text.getText().toString())) {
                        ToastUtil.showToast(this,"时间不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(image)) {
                        ToastUtil.showToast(this,"封面图片不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showToast(this,"详情不能为空");
                        return;
                    }
                    postMsg();
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                //ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, DrivingSelfReplaySuccessActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.text2:
                ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.description:
                //ActivityAnimationUtils.commonTransition(SelfDrivingRepublishActivity.this, ReplyDescriptionActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.time1:
                timeTag = 1;
                if (mDialogYearMonth1 != null) {
                    alertTimePicker(1);
                }
                break;
            case R.id.time2:
                timeTag = 2;
                if (mDialogYearMonth2 != null) {
                    alertTimePicker(2);
                }
                break;
        }
    }
}
