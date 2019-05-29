package com.example.yunchebao.drive.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.fragment.BrandDriveFragment;
import com.example.yunchebao.drive.fragment.ReplaceDriveFragment;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;

import com.system.WebviewActivity;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriveServiceActivity extends AppCompatActivity {

    //轮播图片
     List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout scrollIndicatorView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ArrayList<Fragment> mFragments;
    String []mTitles={"距离最近","评分最好","品牌代驾"};
    List<Banner> mBanners = new ArrayList<>();
    List<String> images = new ArrayList<>();
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.iv_play)
    ImageView iv_play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_service);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFragments=new ArrayList<>();
        for (int i = 1; i <3 ; i++) {
            ReplaceDriveFragment orderListFragment=ReplaceDriveFragment.newInstance(i);
            mFragments.add(orderListFragment);
        }
        mFragments.add(new BrandDriveFragment());
        scrollIndicatorView.setViewPager(viewPager,mTitles,this,mFragments);
        getBaner();
    }

    private void getBaner() {
        imageList.clear();
        Map<String, Object> params = new HashMap<>();
        params.put("type", 5);
        HttpProxy.obtain().get(PlatformContans.Commom.getBannerList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Banner banner = new Gson().fromJson(item.toString(), Banner.class);
                        mBanners.add(banner);
                        String url = item.getString("picture");
                        images.add(url);
                    }
                    initBanner();
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
                Glide.with(DriveServiceActivity.this).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("url", mBanners.get(position).getPicture() + "-" + mBanners.get(position).getSkipUrl());
                Intent intent = new Intent(DriveServiceActivity.this, WebviewActivity.class);
                String url = mBanners.get(position).getSkipUrl();
                if (!url.contains("http") && !url.contains("https")) {
                    url = "http://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

}
