package com.system.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.application.MyApplication;
import com.baike.BuyCarListActivity;
import com.baike.DecorationListActivity;
import com.baike.MagzineCoverActivity;
import com.baike.MagzineListActivity;
import com.baike.MaintainListActivity;
import com.baike.adapter.ClassifyWikiAdapter;
import com.baike.fragment.BaikeItemFragment;
import com.baike.model.ClassifyWiki;
import com.bbcircle.adapter.VPAndTLAdapter;
import com.bbcircle.fragment.CarShowFragment;
import com.bbcircle.fragment.DriverFragment;
import com.bbcircle.fragment.RacePublishFragment;
import com.bbcircle.fragment.SelfDrivingFragment;
import com.bumptech.glide.Glide;
import com.chat.MessageMainActivity;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.BaseFragment;
import com.nohttp.sample.HttpListener;
import com.nohttp.tools.HttpJsonClient;
import com.rongcloud.activity.ChatActivity;
import com.system.WebviewActivity;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalScrollView;
import com.tool.listview.PersonalViewPager;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.baike.adapter.MagzineListAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.model.UserInfo;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.yuedan.WashCarType;

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
 * A simple {@link Fragment} subclass.
 * 宝贝百科
 */
public class BaikeFragment extends BaseFragment {


    private ArrayList<String> tabNames = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    TabLayout mTablayout;
    ViewPager vp_content;

    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;
    @BindView(R.id.menuLay1)
    LinearLayout menuLay1;
    @BindView(R.id.menuLay2)
    LinearLayout menuLay2;
    @BindView(R.id.menuLay3)
    LinearLayout menuLay3;

    int type = 1;

    MyFragmentPagerAdapter mClassifyWikiAdapter;
    List<ClassifyWiki> mClassifyWikis1 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mag, container, false);
        ButterKnife.bind(this, rootView);
        vp_content = (ViewPager) rootView.findViewById(R.id.id_stickynavlayout_viewpager);
        mTablayout = (TabLayout) rootView.findViewById(R.id.id_stickynavlayout_indicator);
        getBaner();
        init();
        return rootView;
    }

    private void init() {


//        menuLay1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                type = 1;
//                mClassifyWikis1.clear();
//
//                tabNames.clear();
//                mFragments.clear();
//                getData(type);
//            }
//        });
//        menuLay2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                type = 2;
//                mClassifyWikis1.clear();
//                tabNames.clear();
//                mFragments.clear();
//                getData(type);
//            }
//        });
//        menuLay3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                type = 3;
//                mClassifyWikis1.clear();
//                tabNames.clear();
//                mFragments.clear();
//                getData(type);
//            }
//        });
//        tabNames=new ArrayList<>();
//        mFragments=new ArrayList<>();
        tabNames.add("自驾游");
        tabNames.add("车友会");
        tabNames.add("汽车秀");
        tabNames.add("赛事发布");
        // mFragments.add(new HotArticleFragment());
        mFragments.add(new SelfDrivingFragment());
        mFragments.add(new DriverFragment());
        mFragments.add(new CarShowFragment());
        mFragments.add(new RacePublishFragment());
        mClassifyWikis1=new ArrayList<>();

        mClassifyWikiAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragments,tabNames);
        vp_content.setAdapter(mClassifyWikiAdapter);
        vp_content.setOffscreenPageLimit(1);
        //vpGank.setCurrentItem(0);
        vp_content.setBackgroundColor(getResources().getColor(R.color.white));
        mTablayout.setupWithViewPager(vp_content);
       // getData(type);

    }

    private void updateData() {

        for (int i = 0; i < mClassifyWikis1.size(); i++) {
            tabNames.add(mClassifyWikis1.get(i).getName());
            mFragments.add(BaikeItemFragment.newInstance(type, mClassifyWikis1.get(i).getId()));
        }
        Log.e("size",mFragments.size()+"");

        mClassifyWikiAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), mFragments,tabNames);
        vp_content.setAdapter(mClassifyWikiAdapter);
        mTablayout.setupWithViewPager(vp_content);

    }

    private void getData(final int type) {

        // UserInfo userInfo = MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.WiKi.getWikiClassifyByType, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getWikiClassifyByType", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ClassifyWiki classifyWiki = new Gson().fromJson(item.toString(), ClassifyWiki.class);
                        mClassifyWikis1.add(classifyWiki);
                    }
                    updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

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

}
