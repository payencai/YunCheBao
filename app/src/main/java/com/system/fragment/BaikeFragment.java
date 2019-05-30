package com.system.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.baike.BaikeTagActivity;
import com.baike.fragment.BaikeItemFragment;
import com.baike.model.ClassifyWiki;
import com.bumptech.glide.Glide;

import com.costans.PlatformContans;

import com.entity.Banner;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.system.SearchActivity;
import com.system.WebviewActivity;
import com.system.adapter.FragmentDreamAdapter;

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


/**
 * A simple {@link Fragment} subclass.
 * 宝贝百科
 */
public class BaikeFragment extends BaseFragment {
    @BindView(R.id.menuLay1)
    LinearLayout menuLay1;
    @BindView(R.id.menuLay2)
    LinearLayout menuLay2;
    @BindView(R.id.menuLay3)
    LinearLayout menuLay3;
    @BindView(R.id.menuLay4)
    LinearLayout menuLay4;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.user_center_icon)
    ImageView iv_center;
    @BindView(R.id.search_lay)
    RelativeLayout search_lay;
    private List<String> mTitles;
    private List<Fragment> mFragments;
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout stl_baike;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.iv_add)
    ImageView iv_add;
    int type = 1;
    int pos=0;
    List<ClassifyWiki> mClassifyWikis1;
    FragmentDreamAdapter mFragmentDreamAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_baike, container, false);
        ButterKnife.bind(this, rootView);
        getBaner();
        init();
        return rootView;
    }

    private void init() {
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
        mClassifyWikis1 = new ArrayList<>();
        menuLay1.setSelected(true);
        menuLay2.setSelected(false);
        menuLay3.setSelected(false);
        menuLay4.setSelected(false);
        iv_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), UserCenterActivity.class));
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BaikeTagActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("pos",pos);
                startActivityForResult(intent,1);
            }
        });
        search_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    startActivity(new Intent(getContext(), SearchActivity.class));
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        menuLay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(true);
                menuLay2.setSelected(false);
                menuLay3.setSelected(false);
                menuLay4.setSelected(false);
                tv_type.setText("装饰百科");
                type = 1;
                mClassifyWikis1.clear();
                mTitles.clear();
                mFragments.clear();
                getUpdateData();
            }
        });
        menuLay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(true);
                menuLay3.setSelected(false);
                menuLay4.setSelected(false);
                tv_type.setText("养护百科");
                type = 2;
                mFragments.clear();
                mTitles.clear();
                mClassifyWikis1.clear();
                getUpdateData();
            }
        });
        menuLay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(false);
                menuLay3.setSelected(true);
                menuLay4.setSelected(false);
                tv_type.setText("购车指南");
                type = 3;
                mFragments.clear();
                mTitles.clear();
                mClassifyWikis1.clear();
                getUpdateData();
            }
        });
        menuLay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLay1.setSelected(false);
                menuLay2.setSelected(false);
                menuLay3.setSelected(false);
                menuLay4.setSelected(true);
                tv_type.setText("交通百科");
                type = 4;
                mFragments.clear();
                mTitles.clear();
                mClassifyWikis1.clear();
                getUpdateData();

            }
        });
        getFirstData();

    }

    private void initViewPager() {
        mFragments.clear();
        for (int i = 0; i < mClassifyWikis1.size(); i++) {
            mFragments.add(BaikeItemFragment.newInstance(type, mClassifyWikis1.get(i).getId()));
        }
        mFragmentDreamAdapter = new FragmentDreamAdapter(getContext(), getChildFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mFragmentDreamAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                  pos=i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        stl_baike.setViewPager(viewPager);
    }

    private void getFirstData() {

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
                        mTitles.add(classifyWiki.getName());
                    }
                    initViewPager();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1&&data!=null){
            pos=data.getExtras().getInt("pos",0);
            viewPager.setCurrentItem(pos);

        }
    }
    private void getUpdateData() {

        // UserInfo userInfo = MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.WiKi.getWikiClassifyByType, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getWikiClassifyByType", type + "-------" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<String> tittles = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ClassifyWiki classifyWiki = new Gson().fromJson(item.toString(), ClassifyWiki.class);
                        mClassifyWikis1.add(classifyWiki);
                        tittles.add(classifyWiki.getName());
                        mFragments.add(BaikeItemFragment.newInstance(type, mClassifyWikis1.get(i).getId()));
                    }
                    mFragmentDreamAdapter.setNewTitleFragment(mFragments, tittles);
                    stl_baike.notifyDataSetChanged();
                    if (mFragments.size() > 0) {
                        viewPager.setCurrentItem(0);
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
