package com.example.yunchebao.collect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.example.yunchebao.maket.ShopMainListActivity;
import com.vipcenter.adapter.ShopCollectListAdapter;
import com.vipcenter.model.ShopCollect;

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
 * Created by sdhcjhss on 2018/1/6.
 */

public class ShopCollectListFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    List<ShopCollect> mWashCollects ;
    ShopCollectListAdapter mWashCollectAdapter;
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        page=1;
        mWashCollects.clear();
        mWashCollectAdapter.setNewData(mWashCollects);
        getData();
    }
    private void init() {
        mWashCollects=new ArrayList<>();
        mWashCollectAdapter=new ShopCollectListAdapter(R.layout.shop_collect_list_item,mWashCollects);
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
                ShopCollect shopCollect= (ShopCollect) adapter.getItem(position);
                Intent intent=new Intent(getContext(),ShopMainListActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",shopCollect.getShopId());
                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mWashCollects.clear();
                mWashCollectAdapter.setNewData(mWashCollects);
                getData();
            }
        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getBabyMerchantCollectionList, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refreshLayout.finishRefresh();
                Log.e("getshop", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        jsonObject=jsonObject.getJSONObject("data");
                        JSONArray data = jsonObject.getJSONArray("beanList");
                        List<ShopCollect>washCollects=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ShopCollect baikeItem = new Gson().fromJson(item.toString(), ShopCollect.class);
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
