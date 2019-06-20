package com.system.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.yunchebao.MyApplication;
import com.bbcircle.AllKindActivity;
import com.bumptech.glide.Glide;

import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.MarketSelectListActivity;
import com.example.yunchebao.maket.RentGoodsActivity;
import com.example.yunchebao.maket.ShopCartActivity;
import com.example.yunchebao.maket.adapter.GridMenuAdapter;
import com.example.yunchebao.maket.adapter.RentGoodsAdapter;
import com.example.yunchebao.maket.model.GoodList;
import com.example.yunchebao.maket.model.GoodMenu;
import com.example.yunchebao.maket.model.RentGoods;
import com.nohttp.sample.BaseFragment;
import com.example.yunchebao.rongcloud.activity.ChatActivity;
import com.system.SearchActivity;
import com.system.WebviewActivity;
import com.system.adapter.GoodsListAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;
import com.xihubao.NewGoodsListActivity;
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
 * A simple {@link Fragment} subclass.
 */
public class MallFragment extends BaseFragment {


    private Context ctx;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    com.youth.banner.Banner banner;
    @BindView(R.id.ll_knowyou)
    LinearLayout ll_knowyou;
    @BindView(R.id.middleGrid)
    GridViewForScrollView middleGrid;
    @BindView(R.id.gv_type)
    GridViewForScrollView menuGrid;
    @BindView(R.id.gv_rentest)
    GridViewForScrollView gv_rentest;
    RentGoodsAdapter mRentlyAdapter;
    GoodsListAdapter mHotAdapter;
    GridMenuAdapter mGridMenuAdapter;

    private List<GoodList> hotList;
    List<RentGoods> mRentGoods = new ArrayList<>();
    List<GoodMenu> mGoodMenus = new ArrayList<>();
    List<Banner> mBanners = new ArrayList<>();
    List<String> images = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, rootView);
        init();
        getData();
        getBaner();
        getMenu();
        getHotGoods();
        getRently();
        return rootView;
    }

    private void getHotGoods() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getHotCommodity, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodMenu", result);
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodList baikeItem = new Gson().fromJson(item.toString(), GoodList.class);
                        hotList.add(baikeItem);
                    }
                    mHotAdapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getRently() {
        Map<String, Object> params = new HashMap<>();
        params.put("type", 2);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getGoodMenu, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodMenu", result);
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RentGoods baikeItem = new Gson().fromJson(item.toString(), RentGoods.class);
                        mRentGoods.add(baikeItem);
                    }
                    mRentlyAdapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    int page = 1;


    @Override
    public void onResume() {
        super.onResume();
        page = 1;

        /// getKnowYou();
    }

    private void getMenu() {
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getGoodMenu, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodMenu", result);
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodMenu baikeItem = new Gson().fromJson(item.toString(), GoodMenu.class);
                        mGoodMenus.add(baikeItem);
                    }
                    mGoodMenus.add(new GoodMenu());
                    mGridMenuAdapter.notifyDataSetChanged();
                    //updateData();

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
        ctx = getActivity();

        hotList = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            middleList.add(new PhoneGoodEntity());
//        }
        mHotAdapter = new GoodsListAdapter(ctx, hotList);
        mRentlyAdapter = new RentGoodsAdapter(ctx, mRentGoods);
        mGridMenuAdapter = new GridMenuAdapter(ctx, mGoodMenus);
        middleGrid.setAdapter(mHotAdapter);
        gv_rentest.setAdapter(mRentlyAdapter);
        menuGrid.setAdapter(mGridMenuAdapter);
        middleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("data", hotList.get(position));
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
            }
        });
        gv_rentest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), RentGoodsActivity.class);
                intent.putExtra("id", mRentGoods.get(position).getSecondId());
                startActivity(intent);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("data",mRentGoods.get(position));
//                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mGoodMenus.get(position).getId() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", mGoodMenus.get(position).getSecondId());
                    ActivityAnimationUtils.commonTransition(getActivity(), MarketSelectListActivity.class, ActivityConstans.Animation.FADE, bundle);
                } else {
                    ActivityAnimationUtils.commonTransition(getActivity(), AllKindActivity.class, ActivityConstans.Animation.FADE);
                }
            }
        });


    }

    private void getData() {

    }


    @OnClick({R.id.messenger_icon, R.id.user_center_icon, R.id.shop_cart_icon, R.id.middleMore, R.id.search_lay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.search_lay:
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), SearchActivity.class, ActivityConstans.Animation.FADE);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
            case R.id.user_center_icon:
                if (MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), UserCenterActivity.class, ActivityConstans.Animation.FADE);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
            case R.id.shop_cart_icon:
                if (MyApplication.isIsLogin())
                    ActivityAnimationUtils.commonTransition(getActivity(), ShopCartActivity.class, ActivityConstans.Animation.FADE);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                break;
            case R.id.messenger_icon:
                if (MyApplication.isIsLogin())
                    ActivityAnimationUtils.commonTransition(getActivity(), ChatActivity.class, ActivityConstans.Animation.FADE);
                else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
                //ActivityAnimationUtils.commonTransition(getActivity(), MessageMainActivity.class, ActivityConstans.Animation.FADE);
                break;

            case R.id.middleMore:
                ActivityAnimationUtils.commonTransition(getActivity(), NewGoodsListActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }

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
