package com.example.yunchebao.maket.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.adapter.GoodsTypeAdapter;
import com.example.yunchebao.maket.model.GoodList;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.example.yunchebao.maket.ShopMainListActivity;

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
 * A simple {@link Fragment} subclass.
 */
public class GoodsTypeFragment extends Fragment {

    int page=1;
    boolean isLoadMore=false;
    GoodsTypeAdapter mRoadAdapter;
    List<GoodList> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    int  type=0;
    String id;
    public static GoodsTypeFragment newInstance(int value) {
        GoodsTypeFragment fragment=new GoodsTypeFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",value);
        fragment.setArguments(bundle);
        return fragment;
    }
    public  void getNewData(String value){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_search_new, container, false);
        ButterKnife.bind(this,view);
        type=getArguments().getInt("type")+1;
        initView();
        return view;
    }

    private void initView() {
        mRoads=new ArrayList<>();
        mRoadAdapter=new GoodsTypeAdapter(R.layout.item_know_you,mRoads);
        mRoadAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_road);
        mRoadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodList goodList= (GoodList) adapter.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",goodList);
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        rv_road.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv_road.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_road.setAdapter(mRoadAdapter);

        getData();
    }

    private void getData(){

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("type", type);
        params.put("merchantId", ShopMainListActivity.id);
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getMerchantCommodityList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodList road = new Gson().fromJson(item.toString(), GoodList.class);
                        mRoads.add(road);
                    }
                    mRoadAdapter.setNewData(mRoads);
                    if(isLoadMore){
                        isLoadMore=false;
                        mRoadAdapter.loadMoreComplete();
                    }else{
                        mRoadAdapter.loadMoreEnd(true);
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
