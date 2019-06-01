package com.xihubao;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.system.model.ShopInfo;
import com.vipcenter.RegisterActivity;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class ShopInfoActivity extends AppCompatActivity {
    String id;
    ShopInfo mShopInfo;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.back)
    ImageView back;
    UserMsg mUserMsg;
    boolean isfriend=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id = getIntent().getStringExtra("id");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MyApplication.isLogin){
                    startActivity(new Intent(ShopInfoActivity.this, RegisterActivity.class));
                    return;
                }
                if(isfriend){
                    RongIM.getInstance().startPrivateChat(ShopInfoActivity.this,id,mShopInfo.getName());
                }else{
                    showApplyDialog();
                }

            }
        });
        getUserDetail(id);
        getData();
    }
    private void getUserDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.Chat.isFriendByShopId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    if("1".equals(data)){
                        isfriend=true;
                        tv_add.setText("聊天");
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
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.MerchAdmin.getMerchInformationByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("shopDetail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mShopInfo = new Gson().fromJson(data.toString(), ShopInfo.class);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showApplyDialog() {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        final EditText et_season = (EditText) view.findViewById(R.id.et_season);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_name.setText("好友申请");
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reson = et_season.getEditableText().toString();
                apply(reson, dialog);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }

    private void apply(String reason, final Dialog dialog) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", mShopInfo.getId());

        HttpProxy.obtain().post(PlatformContans.Chat.addFriendByShopId, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("friend", result);
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    String msg = jsonObject.getString("message");
                    if (code == 0) {
                        dialog.dismiss();
                        Toast.makeText(ShopInfoActivity.this, "已添加对方为好友！", Toast.LENGTH_SHORT).show();
                    } else {
                        ToastUtil.showToast(ShopInfoActivity.this, msg);
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

    List<String> mImages = new ArrayList<>();

    private void setData() {
        UserInfo userInfo=new UserInfo(mShopInfo.getId(),mShopInfo.getName(), Uri.parse(mShopInfo.getHeadPortrait()));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
        String images = mShopInfo.getImgs();
        if (!TextUtils.isEmpty(images)) {
            if (images.contains(",")) {
                String[] imgs = images.split(",");
                for (int i = 0; i < imgs.length; i++) {
                    mImages.add(imgs[i]);
                }
            } else {
                mImages.add(images);
            }
            initBanner();
        }

        String address = mShopInfo.getProvince() + mShopInfo.getCity() + mShopInfo.getArea();
        tv_home.setText(address.replace("null", ""));
        tv_nick.setText(mShopInfo.getName());
        tv_content.setText(mShopInfo.getPersonSign());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bithday = null;
        Date driveday = null;
        try {
            bithday = format.parse(mShopInfo.getBirthday() + " 00:00:00");
            driveday = format.parse(mShopInfo.getDrivingLicenseTime() + " 00:00:00");
            int age = getAgeByBirth(bithday)-1;
            int driverage = getAgeByBirth(driveday);
            String value = mShopInfo.getSex() + " " + age + "岁 " + mShopInfo.getConstellation() + " 驾龄" + driverage + "年";
            tv_address.setText(value.replace("null", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void initBanner() {

        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ShopInfoActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(mImages);//设置图片源
        banner.start();
    }

    private static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }

}
