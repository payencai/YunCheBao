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
public class MagFragment extends BaseFragment {


    private Context ctx;

    private List<Map<String, String>> imageList = new ArrayList<>();


    private List<String> tabNames=new ArrayList<>();
    private List<Fragment> mFragments=new ArrayList<>();
    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.id_stickynavlayout_viewpager)
    ViewPager vp_content;
    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;
    @BindView(R.id.menuLay1)
    LinearLayout menuLay1;
    @BindView(R.id.menuLay2)
    LinearLayout menuLay2;
    @BindView(R.id.menuLay3)
    LinearLayout menuLay3;
    @BindView(R.id.messenger_icon)
    ImageView messenger_icon;
//    @BindView(R.id.scollview)
//    PersonalScrollView mScrollView;
    int type = 1;

    ClassifyWikiAdapter mClassifyWikiAdapter;
    List<ClassifyWiki> mClassifyWikis1 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mag, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }



    private void init() {
        getBaner();
        messenger_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivityForResult(new Intent(getContext(), ChatActivity.class), 1);
                    //connect(MyApplication.getUserInfo().getHxPassword());
                } else {
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), 2);
                }
            }
        });
        menuLay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                mClassifyWikis1.clear();

                tabNames.clear();
                mFragments.clear();
                getData(type);
            }
        });
        menuLay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                mClassifyWikis1.clear();
                tabNames.clear();
                mFragments.clear();
                getData(type);
            }
        });
        menuLay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                mClassifyWikis1.clear();
                tabNames.clear();
                mFragments.clear();
                getData(type);
            }
        });

        if (mClassifyWikiAdapter == null)
            mClassifyWikiAdapter = new ClassifyWikiAdapter(getActivity().getSupportFragmentManager(), tabNames, mFragments);
        vp_content.setAdapter(mClassifyWikiAdapter);
        vp_content.setOffscreenPageLimit(1);
       // mClassifyWikiAdapter.notifyDataSetChanged();
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //vp_content.resetHeight(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTablayout.setupWithViewPager(vp_content);
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_content.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getData(type);

    }

    private void updateData() {

        for (int i = 0; i < mClassifyWikis1.size(); i++) {
            tabNames.add(mClassifyWikis1.get(i).getName());
            mFragments.add(BaikeItemFragment.newInstance(type, mClassifyWikis1.get(i).getId()));
        }
        mClassifyWikiAdapter.notifyDataSetChanged();

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
