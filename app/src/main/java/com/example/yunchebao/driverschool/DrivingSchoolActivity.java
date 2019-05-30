package com.example.yunchebao.driverschool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.fragment.ClassFragment;
import com.cheyibao.fragment.CoashCommentFragment;
import com.cheyibao.fragment.CoashFragment;
import com.cheyibao.fragment.SchoolCommentFragment;
import com.cheyibao.list.AutofitViewPager;
import com.cheyibao.model.DrvingSchool;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.ActivityConstans;
import com.tool.TabUtils;
import com.tool.UIControlUtils;
import com.tool.adapter.CommentPagerAdapter;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.RegisterActivity;
import com.xihubao.ShopInfoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/24.
 */

public class DrivingSchoolActivity extends AppCompatActivity {
    private Context ctx;
    private boolean isCollected = false;
    @BindView(R.id.tab_school)
    TabLayout tab_school;
    @BindView(R.id.tab_comment)
    TabLayout tab_comment;
    @BindView(R.id.vp_comment)
    AutofitViewPager vp_comment;
    @BindView(R.id.vp_school)
    AutofitViewPager vp_school;
    @BindView(R.id.collectIcon)
    ImageView collectIcon;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.sr_score)
    SimpleRatingBar sr_score;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    String id;
    DrvingSchool mDrvingSchool;
    private ArrayList<String> mTopTitles = new ArrayList<>(2);
    private ArrayList<Fragment> mTopFragments = new ArrayList<>(2);

    private ArrayList<String> mBottomTitles = new ArrayList<>(2);
    private ArrayList<Fragment> mBottomFragments = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrvingSchool = (DrvingSchool) getIntent().getSerializableExtra("data");
        setContentView(R.layout.driving_school_detail);
        ButterKnife.bind(this);
        ctx = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "驾校详情");
        findViewById(R.id.shareBtn).setVisibility(View.GONE);
        if (mDrvingSchool == null) {
            id = getIntent().getStringExtra("id");
            getDetail(id);
        } else {
            init();
        }

    }

    private void getDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", id);
        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mDrvingSchool = new Gson().fromJson(data.toString(), DrvingSchool.class);
                    init();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void init() {
        initView();
        isCollect();
        initTab();
        getBanner();
    }

    private void google(double mLatitude, double mLongitude) {
        if (isAvilible(this, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="
                    + mLatitude + "," + mLongitude
                    + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "您尚未安装谷歌地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    private void showDialog(final LatLng latLng) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_map, null);
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
        dialog.findViewById(R.id.tv_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                baidu(latLng.latitude, latLng.longitude);
            }
        });
        dialog.findViewById(R.id.tv_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                google(latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_gaode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                gaode(latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void baidu(double mLatitude, double mLongitude) {
        LatLng poit = new LatLng(mLatitude, mLongitude);
        if (isAvilible(this, "com.baidu.BaiduMap")) {// 传入指定应用包名

            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + poit.latitude + ","
                        + poit.longitude + "|name:" + // 终点
                        "&mode=driving&" + // 导航路线方式
                        "region=" + //
                        "&src=广州番禺#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                startActivity(intent); // 启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {// 未安装
            Toast.makeText(this, "您尚未安装百度地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    private void gaode(double mLatitude, double mLongitude) {
        if (isAvilible(this, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=新疆和田&poiname=" + "广州" + "&lat="
                        + mLatitude
                        + "&lon="
                        + mLongitude + "&dev=0");
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "您尚未安装高德地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    List<String> images = new ArrayList<>();

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(DrivingSchoolActivity.this).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

    private void getBanner() {
        String banner = mDrvingSchool.getBanner();
        if (!TextUtils.isEmpty(banner)) {
            String imgs[] = banner.split(",");
            for (int i = 0; i < imgs.length; i++) {
                images.add(imgs[i]);
            }
            initBanner();
        }

    }

    public DrvingSchool getDrvingSchool() {
        return mDrvingSchool;
    }


    private void initTab() {
        // mTitleList.add("热帖");
        mTopTitles.add("班级");
        mTopTitles.add("教练");
        mTopFragments.add(new ClassFragment());
        mTopFragments.add(new CoashFragment());
        mBottomTitles.add("店铺评论");
        mBottomTitles.add("教练评论");
        mBottomFragments.add(new SchoolCommentFragment());
        mBottomFragments.add(new CoashCommentFragment());
        MyFragmentPagerAdapter schoolAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mTopFragments, mTopTitles);
        CommentPagerAdapter commentAdapter = new CommentPagerAdapter(getSupportFragmentManager(), mBottomFragments, mBottomTitles);
        vp_comment.setAdapter(commentAdapter);
        vp_school.setAdapter(schoolAdapter);
        tab_comment.setupWithViewPager(vp_comment);
        tab_school.setupWithViewPager(vp_school);
        TabUtils.setWidth(tab_comment);
        TabUtils.setWidth(tab_school);
    }

    private void initView() {

        tv_grade.setText(mDrvingSchool.getGrade()+"");
        tv_name.setText(mDrvingSchool.getName());
        tv_address.setText(mDrvingSchool.getAddress());
        int score= (int) mDrvingSchool.getScore();
        tv_score.setText(score + "分");
        sr_score.setRating((float) mDrvingSchool.getScore());
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(Double.parseDouble(mDrvingSchool.getLatitude()), Double.parseDouble(mDrvingSchool.getLongitude()));
                showDialog(latLng);
            }
        });

    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @OnClick({R.id.back, R.id.collectBtn, R.id.ll_head,R.id.callBtn})
    public void Onclick(View v) {
        switch (v.getId()) {
            case R.id.callBtn:
                callPhone(mDrvingSchool.getServiceTelephone());
                break;
            case R.id.ll_head:
                Intent intent = new Intent(DrivingSchoolActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", mDrvingSchool.getId());
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.collectBtn:
                if (!MyApplication.isLogin) {
                    startActivity(new Intent(DrivingSchoolActivity.this, RegisterActivity.class));
                    return;
                }
                if (isCollect == 0) {
                    isCollect = 1;
                    collectIcon.setImageResource(R.mipmap.collect_yellow);
                } else if (isCollect == 1) {
                    isCollect = 0;
                    collectIcon.setImageResource(R.mipmap.collect_gray_hole);
                }
                collect();
                break;
        }
    }

    int isCollect = -1;

    public void isCollect() {
        Map<String, Object> params = new HashMap<>();
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getId();
        }
        params.put("userId", token);
        params.put("merchantId", mDrvingSchool.getId());
        HttpProxy.obtain().get(PlatformContans.Collect.getDrivingSchoolCollection, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int data = jsonObject.getInt("data");
                    if (data == 0) {
                        isCollect = 0;
                        collectIcon.setImageResource(R.mipmap.collect_gray_hole);
                    } else if (data == 1) {
                        isCollect = 1;
                        collectIcon.setImageResource(R.mipmap.collect_yellow);
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

    public void collect() {
        Map<String, Object> params = new HashMap<>();
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        }
        params.put("merchantId", mDrvingSchool.getId());
        HttpProxy.obtain().post(PlatformContans.Collect.addDrivingSchoolCollection, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
