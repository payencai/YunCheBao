package com.system.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yunchebao.MyApplication;
import com.bbcircle.PublishAreaSelectActivity;
import com.bbcircle.fragment.CarShowFragment;
import com.bbcircle.fragment.DriverFragment;
import com.bbcircle.fragment.RacePublishFragment;
import com.bbcircle.fragment.SelfDrivingFragment;
import com.bumptech.glide.Glide;


import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.example.yunchebao.rongcloud.activity.ChatActivity;
import com.system.WebviewActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.example.yunchebao.collect.AllCollectionActivity;
import com.vipcenter.HistoryListActivity;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
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
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/20.
 * 宝贝圈
 */

public class BBCircleFragment extends BaseFragment {
    private ArrayList<String> mTitleList;
    private ArrayList<Fragment> mFragments;
    //@BindView(R.id.id_stickynavlayout_viewpager)
    ViewPager vpGank;
    //@BindView(R.id.id_stickynavlayout_indicator)
    TabLayout tabGank;
    MyFragmentPagerAdapter myAdapter;
    //轮播图片
    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;

    List<String> images = new ArrayList<>();

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getContext()).load((String) path).into(imageView);
            }
        });
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.e("url", mBanners.get(position).getPicture() + "-" + mBanners.get(position).getSkipUrl());
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String url = mBanners.get(position).getSkipUrl();
                if (!url.contains("http") && !url.contains("https")) {
                    url = "http://" + url;
                }
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

    List<Banner> mBanners = new ArrayList<>();

    private void getBaner() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 6);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_circle, container, false);
        ButterKnife.bind(this, rootView);
        vpGank = (ViewPager) rootView.findViewById(R.id.id_stickynavlayout_viewpager);
        tabGank = (TabLayout) rootView.findViewById(R.id.id_stickynavlayout_indicator);
        getBaner();
        initFragmentList();
        return rootView;
    }

    private void initFragmentList() {
        mTitleList = new ArrayList<>();
        mFragments = new ArrayList<>();
        myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        vpGank.setOffscreenPageLimit(1);
        vpGank.setBackgroundColor(getResources().getColor(R.color.white));
        tabGank.setupWithViewPager(vpGank);
        mTitleList.add("自驾游");
        mTitleList.add("车友会");
        mTitleList.add("汽车秀");
        mTitleList.add("赛事发布");
        // mFragments.add(new HotArticleFragment());
        mFragments.add(new SelfDrivingFragment());
        mFragments.add(new DriverFragment());
        mFragments.add(new CarShowFragment());
        mFragments.add(new RacePublishFragment());
        myAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.messenger_icon, R.id.menuLay1, R.id.menuLay2, R.id.menuLay3, R.id.user_center_icon})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.messenger_icon:
                if (MyApplication.isLogin) {
                    startActivityForResult(new Intent(getContext(), ChatActivity.class), 1);
                    //connect(MyApplication.getUserInfo().getHxPassword());
                } else {
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), 5);
                }
                //ActivityAnimationUtils.commonTransition(getActivity(), MessageMainActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay1:
                ActivityAnimationUtils.commonTransition(getActivity(), PublishAreaSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay2:
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), AllCollectionActivity.class, ActivityConstans.Animation.FADE);
                else
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), 5);
                break;
            case R.id.menuLay3:
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), HistoryListActivity.class, ActivityConstans.Animation.FADE);
                else
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), 5);
                break;
            case R.id.user_center_icon:
                if (MyApplication.isLogin)
                    startActivityForResult(new Intent(getContext(), UserCenterActivity.class), 5);
                else {
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), 5);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==5){

        }
    }
}
