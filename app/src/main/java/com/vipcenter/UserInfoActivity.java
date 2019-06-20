package com.vipcenter;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.GlideImageEngine;
import com.tool.UIControlUtils;
import com.tool.WheelView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class UserInfoActivity extends NoHttpBaseActivity {
    @BindView(R.id.sd_head)
    CircleImageView sd_head;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @BindView(R.id.tv_baohao)
    TextView tv_baohao;
    @BindView(R.id.iv_switch)
    ImageView iv_switch;
    int position=0;
    String sex="男";
    private List<String> cartypes = new ArrayList<>();
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        initView();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && data != null) {
            List<String> paths=Matisse.obtainPathResult(data);
            File file = new File(paths.get(0));
            if (file.exists()) {
                upImage(PlatformContans.Commom.uploadImg, file);
            } else {
                Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
            }

        }


    }
    private void showSexDialog() {
        cartypes.clear();
        cartypes.add("男");
        cartypes.add("女");
        View view = getLayoutInflater().inflate(R.layout.dialog_washcar_type, null);
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        WheelView wv = (WheelView) view.findViewById(R.id.wheelview);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                updateSex(sex);
            }
        });
        wv.setOffset(1);
        wv.setItems(cartypes);
        wv.setSeletion(position);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                 sex=item;
                 Log.d("ddd", "[Dialog]selectedIndex: " + position + ", item: " + item);
            }
        });
        //wv.setSeletion(0);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

    }
    private void showNickDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nick, null);
        //获得dialog的window窗口
        //将自定义布局加载到dialog上
        TextView tv_confirm= (TextView) dialogView.findViewById(R.id.tv_confirm);
        EditText et_nick= (EditText) dialogView.findViewById(R.id.et_nick);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String name=et_nick.getEditableText().toString();
                if(!TextUtils.isEmpty(name)){
                    updateNick(name);
                    tv_nickname.setText(name);
                }
            }
        });
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = (int) (display.getWidth()*0.7);
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }
    public void upImage(String url, File file) {
        NetUtils.getInstance().upLoadImage(url, file, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                Log.e("upload", "onResponse: " + response);
                try {
                    JSONObject object = new JSONObject(response);
                    int resultCode = object.getInt("resultCode");
                    final String data = object.getString("data");
                    image = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Glide.with(DriverFriendsRepublishActivity.this).load(data).into(iv_img);
                            Glide.with(UserInfoActivity.this).load(image)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                    .into(sd_head);
                            updateHead();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.e("upload", "onResponse: " + error);

            }
        });


    }
    private void updateHead() {
        Map<String, Object> params = new HashMap<>();
        params.put("headPortrait", image);
        String token=MyApplication.token;
        Log.e("token",token+"");
        HttpProxy.obtain().post(PlatformContans.User.updateUser, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("resutl", result);
                MyApplication.getUserInfo().setHeadPortrait(image);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void updateNick(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        HttpProxy.obtain().post(PlatformContans.User.updateUser, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("resutl", result);
                MyApplication.getUserInfo().setName(name);

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void updateSex(String sex) {
        Map<String, Object> params = new HashMap<>();
        params.put("sex", sex);
        HttpProxy.obtain().post(PlatformContans.User.updateUser, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("sex", result);
                MyApplication.getUserInfo().setSex(sex);
                tv_sex.setText(sex);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "个人资料");
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait())
                .into(sd_head);
        sd_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog();
                //showNearbyDialog();
                selectFile();
            }
        });
        tv_account.setText(MyApplication.getUserInfo().getUsername());
        tv_nickname.setText(MyApplication.getUserInfo().getName());
        tv_baohao.setText(MyApplication.getUserInfo().getHxAccount());
        tv_sex.setText(MyApplication.getUserInfo().getSex());
        if(MyApplication.getUserInfo().getCarShowState()==1){
            iv_switch.setImageResource(R.mipmap.switch_open);
        }else{
            iv_switch.setImageResource(R.mipmap.switch_close);
        }
    }
    private void selectFile() {
        Matisse
                .from(this)
                //选择视频和图片
                //选择图片
                .choose(MimeType.ofImage(), true)//不能同时选择视频和照片
                //有序选择图片
                .countable(true)
                //最大选择数量为9
                .maxSelectable(1)
                .capture(true)
                //选择方向
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                //界面中缩略图的质量
                .thumbnailScale(0.8f)
                //蓝色主题
                //黑色主题
                .theme(R.style.Matisse_Dracula)
                .captureStrategy(new CaptureStrategy(true, "com.yancy.gallerypickdemo.fileprovider"))
                //Glide加载方式
                //Picasso加载方式
                .imageEngine(new GlideImageEngine())
                //请求码
                .forResult(200);
    }
    private void setState(int state){
        Map<String, Object> params = new HashMap<>();
        params.put("state", state);
        HttpProxy.obtain().get(PlatformContans.User.setCarShowState, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("resutl", result);
                if(state==1){
                    iv_switch.setImageResource(R.mipmap.switch_open);
                    MyApplication.getUserInfo().setCarShowState(1);
                }else{
                    MyApplication.getUserInfo().setCarShowState(2);
                    iv_switch.setImageResource(R.mipmap.switch_close);
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @OnClick({R.id.back, R.id.addressLay, R.id.rl_sex,R.id.rl_phone, R.id.rl_idcard, R.id.rl_mycar, R.id.rl_code,R.id.iv_switch,R.id.rl_nick})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sex:
                showSexDialog();
                break;
            case R.id.rl_nick:
                showNickDialog();
                break;
            case R.id.iv_switch:
                if(MyApplication.getUserInfo().getCarShowState()==1){
                    setState(2);
                }else{
                    setState(1);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.addressLay:
                ActivityAnimationUtils.commonTransition(UserInfoActivity.this, ManaAddressActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rl_idcard:
                ActivityAnimationUtils.commonTransition(UserInfoActivity.this, IDCardCertificationActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rl_phone:
                ActivityAnimationUtils.commonTransition(UserInfoActivity.this, MyPhoneActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rl_mycar:
                ActivityAnimationUtils.commonTransition(UserInfoActivity.this, MyCarListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rl_code:
                startActivity(new Intent(UserInfoActivity.this, MyQrcodeActivity.class));
                break;
        }
    }
}
