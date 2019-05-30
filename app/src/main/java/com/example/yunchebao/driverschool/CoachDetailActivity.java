package com.example.yunchebao.driverschool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.example.yunchebao.R;
import com.example.yunchebao.driverschool.model.CoachDetail;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
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
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class CoachDetailActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.tv_man)
    TextView tv_man;
    @BindView(R.id.tv_address)
    TextView tv_address;
    CoachDetail mCoachDetail;
    String id;
    List<String> mImages = new ArrayList<>();

    boolean isfriend=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        getData();
    }

    @OnClick({R.id.back,R.id.tv_add})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                if(!MyApplication.isLogin){
                    startActivity(new Intent(CoachDetailActivity.this, RegisterActivity.class));
                    return;
                }
                if(isfriend){
                    RongIM.getInstance().startPrivateChat(CoachDetailActivity.this,mCoachDetail.getMerchantId(),mCoachDetail.getCoachName());
                }else{
                    showApplyDialog();
                }
                break;
            case R.id.back:
                finish();
                break;
        }
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
        params.put("coachId", id);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getDrivingSchoolCoachByCoachId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("shopDetail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mCoachDetail = new Gson().fromJson(data.toString(), CoachDetail.class);
                    getUserDetail(mCoachDetail.getMerchantId());
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

    private void setData() {
        tv_nick.setText(mCoachDetail.getCoachName());
        String address=mCoachDetail.getNativePlace() + " " + mCoachDetail.getTelephone();
        tv_address.setText(address.replace("null",""));
        String images = mCoachDetail.getImgs();
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date bithday = format.parse(mCoachDetail.getBirthday() + " 00:00:00");
            int age = getAgeByBirth(bithday) - 1;
            String value = mCoachDetail.getSex() + " " + age + "岁 " + mCoachDetail.getConstellation() + " 驾龄" + mCoachDetail.getDrivingAge() + "年";
            tv_man.setText(value.replace("null", ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        params.put("shopId", mCoachDetail.getMerchantId());

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
                        isfriend=true;
                        tv_add.setText("开始聊天");
                        dialog.dismiss();
                        Toast.makeText(CoachDetailActivity.this, "已添加对方为好友！开始进行聊天吧~", Toast.LENGTH_SHORT).show();
                    } else {
                        ToastUtil.showToast(CoachDetailActivity.this, msg);
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
    private void initBanner() {

        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(CoachDetailActivity.this).load((String) path).into(imageView);
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
