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
import com.example.yunchebao.drive.activity.ReplaceDriveDetailActivity;
import com.example.yunchebao.drive.adapter.ReplaceDriveAdapter;
import com.example.yunchebao.drive.model.ReplaceDrive;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class CollectDriveFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    List<ReplaceDrive> mWashCollects ;
    ReplaceDriveAdapter mWashCollectAdapter;
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
        mWashCollectAdapter=new ReplaceDriveAdapter(R.layout.item_replace_drive,mWashCollects);
        mWashCollectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_collect);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mWashCollects.clear();
                mWashCollectAdapter.setNewData(mWashCollects);
                getData();
            }
        });
        mWashCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent=new Intent(getContext(), ReplaceDriveDetailActivity.class);
                ReplaceDrive replaceDrive= (ReplaceDrive) adapter.getItem(position);
                intent.putExtra("id",replaceDrive.getId());
                startActivityForResult(intent,1);
            }
        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        page=1;
        mWashCollects.clear();
        mWashCollectAdapter.setNewData(mWashCollects);
        getData();
    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getDriverCollectionList, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refreshLayout.finishRefresh();
                Log.e("getshop", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<ReplaceDrive>washCollects=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            ReplaceDrive gasStation = new Gson().fromJson(item.toString(), ReplaceDrive.class);
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
