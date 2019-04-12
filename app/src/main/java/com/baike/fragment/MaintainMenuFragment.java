package com.baike.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baike.BaikeDetailActivity;
import com.baike.adapter.BaikePersonAdapter;
import com.costans.PlatformContans;
import com.entity.GoodsListBean;
import com.entity.PhoneShopEntity;

import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;
import com.nohttp.tools.HttpJsonClient;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.adapter.RecycleGoodsCategoryListAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 商品
 */
public class MaintainMenuFragment extends BaseFragment   {


    private RecyclerView mGoodsCateGoryList;
    private RecycleGoodsCategoryListAdapter mGoodsCategoryListAdapter;
    //商品类别列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodscatrgoryEntities=new ArrayList<>();
    //商品列表
    private List<GoodsListBean.DataEntity.GoodscatrgoryEntity> goodsitemEntities=new ArrayList<>();

    //存储含有标题的第一个含有商品类别名称的条目的下表
    private List<Integer> titlePois = new ArrayList<>();
    //上一个标题的小标
    private String lastTitlePoi;
    private RecyclerView recyclerView;
    private BaikePersonAdapter personAdapter;

    private LinearLayoutManager mLinearLayoutManager;
    private Context ctx;
    PhoneShopEntity entity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.shop_detail_goods, container, false);
        initView(view);

        return view;
    }

    private void initData(List<GoodsListBean.DataEntity.GoodscatrgoryEntity> dataList) {

    }




    private void initView(View view) {

    }



    /**
     * 处理滑动 是两个ListView联动
     */
    private class MyOnGoodsScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (!(lastTitlePoi.equals(goodsitemEntities.get(firstVisibleItem).getId()))) {
                lastTitlePoi = goodsitemEntities.get(firstVisibleItem).getId();
                mGoodsCategoryListAdapter.setCheckPosition(goodsitemEntities.get(firstVisibleItem).getPosition());
                mGoodsCategoryListAdapter.notifyDataSetChanged();
            }
        }
    }

}
