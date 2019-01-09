package com.system.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.bbcircle.AllKindActivity;
import com.chat.MessageMainActivity;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.maket.GoodDetailActivity;
import com.maket.MarketSelectListActivity;
import com.maket.ShopCartActivity;
import com.nohttp.rest.Request;
import com.nohttp.rest.Response;
import com.nohttp.sample.BaseFragment;
import com.nohttp.sample.HttpListener;
import com.nohttp.tools.HttpJsonClient;
import com.system.adapter.HomeMenuListAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.vipcenter.UserCenterActivity;
import com.xihubao.NewGoodsListActivity;
import com.xihubao.RepairListActivity;
import com.xihubao.WashCarListActivity;

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
    SlideShowView slideShowView;
    @BindView(R.id.middleGrid)
    GridViewForScrollView middleGrid;
    @BindView(R.id.downGrid)
    GridViewForScrollView listView;

    private HomeMenuListAdapter menuAdapter;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private List<PhoneGoodEntity> middleList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mall, container, false);
        ButterKnife.bind(this, rootView);
        init();


        return rootView;
    }

    private void init() {
        ctx = getActivity();

        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0; i < 3; i++) {
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");

            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);
        middleList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            middleList.add(new PhoneGoodEntity());
        }
        menuAdapter = new HomeMenuListAdapter(ctx, middleList);
        middleGrid.setAdapter(menuAdapter);
        middleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });

        pullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.my_scrollview);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                Log.e("gkfkgk","gkfkgk");
            }
        });

    }



    @OnClick({R.id.messenger_icon, R.id.user_center_icon, R.id.shop_cart_icon, R.id.menuLay1, R.id.menuLay2, R.id.menuLay3, R.id.menuLay4, R.id.menuLay5, R.id.menuLay6, R.id.menuLay7, R.id.menuLay8})
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
            case R.id.menuLay1:
            case R.id.menuLay2:
            case R.id.menuLay3:
            case R.id.menuLay4:
            case R.id.menuLay5:
            case R.id.menuLay6:
            case R.id.menuLay7:
                ActivityAnimationUtils.commonTransition(getActivity(), MarketSelectListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay8:
                ActivityAnimationUtils.commonTransition(getActivity(), AllKindActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.downGridImg:
            case R.id.middleMore:
                ActivityAnimationUtils.commonTransition(getActivity(), NewGoodsListActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }

}
