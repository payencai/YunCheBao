package com.cheyibao.fragment;

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
import com.example.yunchebao.driverschool.DrivingSchoolActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.system.adapter.SchoolCollectAdapter;
import com.vipcenter.model.SchoolCollect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StudyListFragment extends BaseFragment {
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    List<SchoolCollect> mWashCollects ;
    SchoolCollectAdapter mWashCollectAdapter;
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
        mWashCollectAdapter=new SchoolCollectAdapter(R.layout.driving_school_list_item_layout,mWashCollects);
        mWashCollectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
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
        mWashCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SchoolCollect schoolCollect= (SchoolCollect) adapter.getItem(position);
                String id=schoolCollect.getMerchantId();
                Intent intent=new Intent(getContext(),DrivingSchoolActivity.class);
                intent.putExtra("id",id);
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
        HttpProxy.obtain().get(PlatformContans.Collect.getDrivingSchoolCollectionList, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refreshLayout.finishRefresh();
                Log.e("getNewCarCollectionList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<SchoolCollect>washCollects=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        SchoolCollect baikeItem = new Gson().fromJson(item.toString(), SchoolCollect.class);
                        washCollects.add(baikeItem);
                        mWashCollects.add(baikeItem);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        if(data.length()>0){
                            mWashCollectAdapter.addData(washCollects);
                            mWashCollectAdapter.loadMoreComplete();
                        }else{
                            mWashCollectAdapter.loadMoreEnd(true);
                        }

                    }else{
                        mWashCollectAdapter.setNewData(mWashCollects);
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
