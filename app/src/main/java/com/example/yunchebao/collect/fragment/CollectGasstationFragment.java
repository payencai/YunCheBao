package com.example.yunchebao.collect.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.yunchebao.gasstation.GasStationDetailActivity;
import com.example.yunchebao.gasstation.adapter.GasStationAdapter;
import com.example.yunchebao.gasstation.model.GasStation;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;

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

public class CollectGasstationFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    List<GasStation> mWashCollects ;
    GasStationAdapter mWashCollectAdapter;
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
        mWashCollectAdapter=new GasStationAdapter(R.layout.item_search_wash,mWashCollects);
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
                GasStation fourShopData= (GasStation) adapter.getItem(position);
                Intent intent=new Intent(getContext(), GasStationDetailActivity.class);
                intent.putExtra("id",fourShopData.getId());
                startActivity(intent);
            }
        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getShopCollectionList, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getshop", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<GasStation>washCollects=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            GasStation gasStation = new Gson().fromJson(item.toString(), GasStation.class);
                            washCollects.add(gasStation);
                            mWashCollects.add(gasStation);
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
