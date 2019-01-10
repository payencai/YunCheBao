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
import android.widget.ScrollView;

import com.bbcircle.AllKindActivity;
import com.bbcircle.data.ClassItem;
import com.bumptech.glide.Glide;
import com.chat.MessageMainActivity;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.MarketSelectListActivity;
import com.maket.ShopCartActivity;
import com.maket.adapter.GridMenuAdapter;
import com.maket.adapter.KnowYouAdapter;
import com.maket.model.GoodMenu;
import com.maket.model.KnowYou;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.BaseFragment;
import com.nohttp.sample.HttpListener;
import com.nohttp.tools.HttpJsonClient;
import com.system.WebviewActivity;
import com.system.adapter.HomeMenuListAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.listview.PersonalListView;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.vipcenter.UserCenterActivity;
import com.xihubao.NewGoodsListActivity;
import com.xihubao.RepairListActivity;
import com.xihubao.WashCarListActivity;
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
    @BindView(R.id.middleGrid)
    GridViewForScrollView middleGrid;
    @BindView(R.id.gv_type)
    GridViewForScrollView menuGrid;
    @BindView(R.id.downGrid)
    PersonalListView listView;
    KnowYouAdapter mKnowYouAdapter;
    GridMenuAdapter mGridMenuAdapter;
    private HomeMenuListAdapter menuAdapter;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private List<PhoneGoodEntity> middleList;
    List<GoodMenu> mGoodMenus=new ArrayList<>();
    List<Banner> mBanners = new ArrayList<>();
    List<String> images = new ArrayList<>();
    List<KnowYou> mKnowYous=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, rootView);
        init();
        getData();
        getBaner();
        getMenu();
        return rootView;
    }
    private void getMenu(){
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getGoodMenu, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodMenu",result);
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

        //网络地址获取轮播图
//        imageList.clear();
//        for (int i = 0; i < 3; i++) {
//            Map<String, String> image_uri = new HashMap<String, String>();
//            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//
//            imageList.add(image_uri);
//        }
//        slideShowView.setImageUrls(imageList);
        middleList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            middleList.add(new PhoneGoodEntity());
        }
        menuAdapter = new HomeMenuListAdapter(ctx, middleList);
        mKnowYouAdapter=new KnowYouAdapter(ctx,mKnowYous);
        mGridMenuAdapter=new GridMenuAdapter(ctx,mGoodMenus);
        middleGrid.setAdapter(menuAdapter);
        listView.setAdapter(mKnowYouAdapter);
        menuGrid.setAdapter(mGridMenuAdapter);
        middleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        menuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mGoodMenus.get(position).getId()!=0){
                    Bundle bundle =new Bundle();
                    bundle.putString("id",mGoodMenus.get(position).getSecondId());
                    ActivityAnimationUtils.commonTransition(getActivity(), MarketSelectListActivity.class, ActivityConstans.Animation.FADE,bundle);
                }else{
                    ActivityAnimationUtils.commonTransition(getActivity(), AllKindActivity.class, ActivityConstans.Animation.FADE);
                }
            }
        });
        pullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.my_scrollview);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.e("gkfkgk","gkfkgk");

                refreshView.onRefreshComplete();
            }
        });


    }
    private void getData(){

    }


    @OnClick({R.id.messenger_icon, R.id.user_center_icon, R.id.shop_cart_icon,R.id.middleMore})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.user_center_icon:
                ActivityAnimationUtils.commonTransition(getActivity(), UserCenterActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.shop_cart_icon:
                ActivityAnimationUtils.commonTransition(getActivity(), ShopCartActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.messenger_icon:
                ActivityAnimationUtils.commonTransition(getActivity(), MessageMainActivity.class, ActivityConstans.Animation.FADE);
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
