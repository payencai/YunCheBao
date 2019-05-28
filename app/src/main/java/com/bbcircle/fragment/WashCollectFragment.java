package com.bbcircle.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.bbcircle.adapter.WashCollectAdapter;
import com.bbcircle.data.WashCollect;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.WashCarDetailActivity;

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

public class WashCollectFragment extends BaseFragment {
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;
    List<WashCollect> mWashCollects ;
    WashCollectAdapter mWashCollectAdapter;
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
        mWashCollectAdapter=new WashCollectAdapter(R.layout.wash_list_item,mWashCollects);
        mWashCollectAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        });
        mWashCollectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WashCollect washCollect= (WashCollect) adapter.getItem(position);
                Bundle bundle = new Bundle();
                if(washCollect.getType()==1){
                    bundle.putString("id",washCollect.getShopId());
                    bundle.putInt("flag",1);
                    ActivityAnimationUtils.commonTransition(getActivity(), WashCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                }else{
                    bundle.putString("id",washCollect.getShopId());
                    bundle.putInt("flag", 2);
                    ActivityAnimationUtils.commonTransition(getActivity(), WashCarDetailActivity.class, ActivityConstans.Animation.FADE, bundle);

                }


            }
        });
        rv_collect.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_collect.setAdapter(mWashCollectAdapter);
        getData();

    }
    private void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Collect.getWashCollectionList, params,MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("washcollect", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<WashCollect>washCollects=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        WashCollect baikeItem = new Gson().fromJson(item.toString(), WashCollect.class);
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
