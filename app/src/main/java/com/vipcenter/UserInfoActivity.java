package com.vipcenter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.application.MyApplication;
import com.bbcircle.DriverFriendsRepublishActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.WheelView;

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
    /**
     * 裁剪图片
     */
    Uri photoOutputUri;
    Uri photoUri;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        initView();
    }

    File tempFile;

    public void openCamera() {
        //獲取系統版本
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                photoUri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                //检查是否有存储权限，以免崩溃
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    Toast.makeText(this, "请开启存储权限", Toast.LENGTH_SHORT).show();
                    return;
                }
                photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, 2);
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    int position=0;
    String sex="男";
    private List<String> cartypes = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            cropPhoto(data.getData());
        }
        if (requestCode == 2 && data != null) {
            cropPhoto(data.getData());
        }
        if (requestCode == 3 && data != null) {

            File file = new File(photoOutputUri.getPath());
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
    private void showNearbyDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nearby_man, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();
    }
    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_select_photo, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.tv_select_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_select_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openCamera();
            }
        });
        dialog.findViewById(R.id.tv_select_gallery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
                mIntent.addCategory(Intent.CATEGORY_OPENABLE);
                mIntent.setType("image/*");
                startActivityForResult(mIntent, 1);
            }
        });
        dialog.show();
    }

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
        startActivityForResult(cropPhotoIntent, 3);
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
        Glide.with(this).load(MyApplication.getUserInfo().getHeadPortrait())
                .into(sd_head);
        sd_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                //showNearbyDialog();
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
                onBackPressed();
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
