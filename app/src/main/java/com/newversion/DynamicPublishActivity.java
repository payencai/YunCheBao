package com.newversion;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bbcircle.view.SampleCoverVideo;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.vipcenter.EnteringActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DynamicPublishActivity extends AppCompatActivity {

    @BindView(R.id.et_dynamic_text)
    EditText etDynamicText;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @BindView(R.id.sampleCoverVideo)
    SampleCoverVideo sampleCoverVideo;
    @BindView(R.id.gv_dynamic_photos)
    GridView gvDynamicPhotos;
    @BindView(R.id.ll_look_permission)
    LinearLayout llLookPermission;
    @BindView(R.id.ll_user_location)
    LinearLayout ll_user_location;
    @BindView(R.id.rootView)
    LinearLayout rootView;
    @BindView(R.id.tv_look_permission)
    TextView tvLookPermission;
    @BindView(R.id.tv_location)
    TextView tv_location;
    @BindView(R.id.frame_video_player)
    FrameLayout frameVideoPlayer;
    private ArrayList<String> pathList = new ArrayList<>();
    private ArrayList<File> fileList = new ArrayList<>();
    private int kind = 1;
    private String looks;
    private String unLooks;
    private String users;

    private String mediatype;

    private Handler popupHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
                    popupWindow.update();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_publish);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initPopWindow();

        mediatype = getIntent().getStringExtra("mediatype");
        if (mediatype.equals("text")) {
            gvDynamicPhotos.setVisibility(View.GONE);
            frameVideoPlayer.setVisibility(View.GONE);
        } else if (mediatype.equals("pic")) {
            pathList = getIntent().getStringArrayListExtra("pathList");
            //etDynamicText.setText(pathList.get(0));
            if (pathList.size() > 0) {
                frameVideoPlayer.setVisibility(View.GONE);
                gvDynamicPhotos.setVisibility(View.VISIBLE);
                SelectPicGridAdapter adapter = new SelectPicGridAdapter(this, pathList);
                gvDynamicPhotos.setAdapter(adapter);
            }

            popupHandler.sendEmptyMessageDelayed(0, 1000);
            uploadImages(pathList);
        } else if (mediatype.equals("video")) {
            gvDynamicPhotos.setVisibility(View.GONE);
            frameVideoPlayer.setVisibility(View.VISIBLE);
            String videoPath = getIntent().getStringExtra("videoPath");
            sampleCoverVideo.setUpLazy(videoPath, true, null, null, "");
            sampleCoverVideo.getTitleTextView().setVisibility(View.GONE);
            sampleCoverVideo.getBackButton().setVisibility(View.GONE);

            sampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_play.setVisibility(View.GONE);
                    sampleCoverVideo.startWindowFullscreen(v.getContext(), false, true);
                }
            });
            sampleCoverVideo.loadCoverImage(videoPath, R.mipmap.pic1);
            //etDynamicText.setText(videoPath);
        }

    }

    private PopupWindow popupWindow;

    private void initPopWindow() {
        View contentView = LayoutInflater.from(DynamicPublishActivity.this).inflate(
                R.layout.popup_loading_view, null);

        popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);

        popupWindow.setTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
    }

    private String content;

    @OnClick({R.id.tv_cancel_publish, R.id.tv_publish_dynamic, R.id.ll_look_permission,R.id.ll_user_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel_publish:
                onBackPressed();
                break;
            case R.id.tv_publish_dynamic:
                content = etDynamicText.getEditableText().toString();
                if (mediatype.equals("text")) {
                    if (TextUtils.isEmpty(content)) {
                        ToastUtil.showToast(DynamicPublishActivity.this, "请输入要发布的文字内容");
                        return;
                    }
                } else if (mediatype.equals("pic")) {

                } else if (mediatype.equals("video")) {

                }
                publishDynamic();
                break;
            case R.id.ll_look_permission:
                startActivityForResult(new Intent(DynamicPublishActivity.this, DynamicLookPermissionActivity.class), 2);
                break;
            case R.id.ll_user_location:
                startActivityForResult(new Intent(DynamicPublishActivity.this, X5WebviewActivity.class), 1);
                break;
        }
    }

    private AddressBean mAddressBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (requestCode) {
                case 1:
                    mAddressBean = (AddressBean) data.getSerializableExtra("address");
                    String address = mAddressBean.getPoiaddress();
                    Log.e("mAddressBean", mAddressBean.toString());
                    tv_location.setText(address);
                    break;
                case 2:
                    kind = data.getIntExtra("kind",1);
                    if(kind == 2){
                        tvLookPermission.setText("私密");

                    }else if(kind == 3){
                        looks = data.getStringExtra("ids");
                        users = data.getStringExtra("users");
                        tvLookPermission.setText(users);


                    }else if(kind == 4){
                        unLooks = data.getStringExtra("ids");
                        users = data.getStringExtra("users");
                        tvLookPermission.setText("除去  "+users);

                    }else {
                        tvLookPermission.setText("公开");
                    }

                    break;
            }
        }


    }

    private ArrayList<String> imgsUrl = new ArrayList();

    /***
     * 上传多张图片
     * @param pathList
     */
    private void uploadImages(ArrayList<String> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
            File file = new File(pathList.get(i));
            Log.e("path", file.getAbsolutePath());
            if (file.exists()) {
                //fileList.add(file);
               upImage(PlatformContans.Commom.uploadImg, file);
            }
        }
       // upImages(PlatformContans.Commom.uploadImgs, fileList);
    }

    public void upImage(String url, File file) {

        OkHttpClient mOkHttpClent = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getPath(),
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
                    final String data = object.getString("data");
                    imgsUrl.add(data);
                    if(imgsUrl.size()==pathList.size()){
                        imgs = listToString(imgsUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                popupWindow.dismiss();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private String imgs;

    /**
     * 上传图片
     */
    public void upImages(String url, ArrayList<File> files) {
        OkHttpClient mOkHttpClent = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300, TimeUnit.SECONDS)
                .build();

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for (int i = 0; i < files.size(); i++) {
            builder.addFormDataPart("images", files.get(i).getPath(),
                    RequestBody.create(MediaType.parse("image/png"), files.get(i)));
        }

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
                    imgs = object.getString("data");
                    popupWindow.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发布动态
     */
    private void publishDynamic() {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(content)) {
            params.put("content", content);
        }

        if (!TextUtils.isEmpty(imgs)) {
            params.put("imgs", imgs);
        }
        if (mAddressBean!=null) {
            params.put("address", mAddressBean.getPoiaddress());
            params.put("longitude", mAddressBean.getLatlng().getLng()+"");
            params.put("latitude", mAddressBean.getLatlng().getLat()+"");
        }

        params.put("type", 1);//（1.普通朋友圈，2.转发链接）

        params.put("kind", kind);//查看权限（1.公开，2私密，3.部分可见，4.不给谁看）

        Log.e("kind",kind+"");

        if(kind == 3){
            if(!TextUtils.isEmpty(looks)){
                params.put("looks", looks);
            }
        }

        if(kind == 4){
            if(!TextUtils.isEmpty(unLooks)){
                params.put("unLooks", unLooks);
            }
        }

        HttpProxy.obtain().post(PlatformContans.CommunicationCircle.addCommunicationCircle, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String resultCode = jsonObject.getString("resultCode");
                    if (resultCode.equals("0")) {
                        Toast.makeText(DynamicPublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Toast.makeText(DynamicPublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(DynamicPublishActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**List转String逗号分隔*/
    private String listToString(ArrayList<String> list) {

        StringBuilder stringBuilder = new StringBuilder();
        for(String str : list){
            stringBuilder.append(str);
            stringBuilder.append(",");
        }
        String strs = stringBuilder.toString();
        strs = strs.substring(0, strs.length() - 1);

        return strs;
    }

}
