package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bbcircle.fragment.CarShowFragment;
import com.bbcircle.fragment.DriverFragment;
import com.bbcircle.fragment.RacePublishFragment;
import com.bbcircle.fragment.SelfDrivingFragment;
import com.bumptech.glide.Glide;
import com.cheyibao.fragment.ClassFragment;
import com.cheyibao.fragment.CoashCommentFragment;
import com.cheyibao.fragment.CoashFragment;
import com.cheyibao.fragment.SchoolCommentFragment;
import com.cheyibao.fragment.ShopCommentFragment;
import com.cheyibao.list.AutofitViewPager;
import com.cheyibao.model.DrvingSchool;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.nohttp.sample.NoHttpBaseActivity;
import com.system.WebviewActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.CommentPagerAdapter;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalScrollView;
import com.tool.listview.PersonalViewPager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
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
    DrvingSchool mDrvingSchool;
    private ArrayList<String> mTopTitles = new ArrayList<>(2);
    private ArrayList<Fragment> mTopFragments = new ArrayList<>(2);

    private ArrayList<String> mBottomTitles = new ArrayList<>(2);
    private ArrayList<Fragment> mBottomFragments = new ArrayList<>(2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrvingSchool= (DrvingSchool) getIntent().getSerializableExtra("data");
        setContentView(R.layout.driving_school_detail);
        ButterKnife.bind(this);
        initView();
        initTab();
        getBanner();
    }
    List<String> images=new ArrayList<>();
    private void initBanner(){
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
    private void getBanner(){
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", mDrvingSchool.getId());
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getDrivingSchoolPhoto, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getBannerList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        String url = data.getString(i);
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
    public DrvingSchool getDrvingSchool() {
        return mDrvingSchool;
    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    private void initTab(){
        // mTitleList.add("热帖");
        mTopTitles.add("班级");
        mTopTitles.add("教练");
        mTopFragments.add(new ClassFragment());
        mTopFragments.add(new CoashFragment());
        mBottomTitles.add("店铺评论");
        mBottomTitles.add("教练评论");
        mBottomFragments.add(new SchoolCommentFragment());
        mBottomFragments.add(new CoashCommentFragment());
        tab_comment.setTabMode(TabLayout.MODE_FIXED);
        tab_comment.setupWithViewPager(vp_comment);
        tab_school.setTabMode(TabLayout.MODE_FIXED);
        tab_school.setupWithViewPager(vp_school);

        MyFragmentPagerAdapter schoolAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mTopFragments, mTopTitles);
        vp_school.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0) {
//                    vp_school.resetHeight(0);
//                } else if (position == 1) {
//                    vp_school.resetHeight(1);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_school.setAdapter(schoolAdapter);
        vp_school.setOffscreenPageLimit(1);
        schoolAdapter.notifyDataSetChanged();

        CommentPagerAdapter commentAdapter = new CommentPagerAdapter(getSupportFragmentManager(), mBottomFragments, mBottomTitles);
        vp_comment.setAdapter(commentAdapter);
        vp_comment.setOffscreenPageLimit(1);
        vp_comment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0) {
//                    vp_comment.resetHeight(0);
//                } else if (position == 1) {
//                    vp_comment.resetHeight(1);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        commentAdapter.notifyDataSetChanged();
        tab_school.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab_school, 40, 40);
            }
        });
        tab_comment.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tab_comment, 40, 40);
            }
        });


    }
    private void initView() {

        ctx = this;
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"驾校详情");
        findViewById(R.id.shareBtn).setVisibility(View.VISIBLE);
        tv_name.setText(mDrvingSchool.getName());
        tv_address.setText(mDrvingSchool.getAddress());
        tv_score.setText(mDrvingSchool.getScore()+"分");
        sr_score.setRating((float) mDrvingSchool.getScore());

    }

    @OnClick({R.id.back,R.id.collectBtn})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.collectBtn:
                if (isCollected){
                    isCollected = false;
                    collectIcon.setImageResource(R.mipmap.collect_yellow);
                }else{
                    isCollected = true;
                    collectIcon.setImageResource(R.mipmap.collect_gray_hole);
                }
                break;
        }
    }
}
