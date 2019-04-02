package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.MyApplication;
import com.baike.adapter.CarListAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.NewCarSellerActivity;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.model.GoodList;
import com.maket.model.LoadMoreListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.adapter.GoodCollectAdapter;
import com.vipcenter.adapter.GoodCollectListAdapter;
import com.vipcenter.adapter.NewCarCollectAdapter;
import com.vipcenter.model.GoodsCollect;
import com.vipcenter.model.NewCarCollect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.error;

/**
 * Created by sdhcjhss on 2018/1/6.
 * 商品收藏
 */

public class GoodsCollectFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    List<GoodsCollect> mWashCollects ;
    GoodCollectAdapter mWashCollectAdapter;
    int page=1;
    boolean isLoadMore=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_collect_commom, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        mWashCollects=new ArrayList<>();
        mWashCollectAdapter=new GoodCollectAdapter(R.layout.item_know_you,mWashCollects);
        mWashCollectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_collect);
        mWashCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                GoodList goodList=new GoodList();
                GoodsCollect goodsCollect= (GoodsCollect) adapter.getItem(position);
                goodList.setId(goodsCollect.getCommodityId());
                goodList.setOriginalPrice(goodsCollect.getOriginalPrice());
                goodList.setDiscountPrice(goodsCollect.getDiscountPrice());
                goodList.setCommodityImage(goodsCollect.getCommodityImage());
                goodList.setName(goodsCollect.getCommodityName());
                bundle.putSerializable("data",goodList);
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getCommodityCollectionList, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoods", page +"--"+result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<GoodsCollect>washCollects=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            GoodsCollect baikeItem = new Gson().fromJson(item.toString(), GoodsCollect.class);
                            washCollects.add(baikeItem);
                            mWashCollects.add(baikeItem);
                        }
                        if(isLoadMore){
                            isLoadMore=false;
                            mWashCollectAdapter.addData(washCollects);
                            if(data.length()>0){
                                mWashCollectAdapter.loadMoreComplete();
                            }else{
                                mWashCollectAdapter.loadMoreEnd(true);
                            }

                        }else{
                            mWashCollectAdapter.setNewData(mWashCollects);

                        }
                    }else{
                        mWashCollectAdapter.loadMoreEnd(true);
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

}
