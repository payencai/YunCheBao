package com.example.yunchebao.gasstation.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.adapter.FourShopCommentAdapter;
import com.example.yunchebao.fourshop.bean.FourShopComment;
import com.example.yunchebao.gasstation.GasStationDetailActivity;
import com.example.yunchebao.gasstation.adapter.StationCommentAdapter;
import com.example.yunchebao.gasstation.model.StationComment;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
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
 * A simple {@link Fragment} subclass.
 */
public class StationCommentFragment extends Fragment {


    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    StationCommentAdapter mFourShopCommentAdapter;
    List<StationComment> mFourShopComments;
    int page=1;
    boolean isLoadMore=false;
    String id;
    GasStationDetailActivity mActivity;
    public StationCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_station_comment, container, false);
        ButterKnife.bind(this,view);
        mActivity= (GasStationDetailActivity) getActivity();
        id=mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mFourShopComments=new ArrayList<>();

        mFourShopCommentAdapter=new StationCommentAdapter(R.layout.item_shop_comment,mFourShopComments);
        mFourShopCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_order);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mFourShopComments.clear();
                mFourShopCommentAdapter.setNewData(mFourShopComments);
                getData();
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mFourShopCommentAdapter);
        getData();
    }

    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.Station.getGasStationCommentListByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh.finishRefresh();
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<StationComment> replaceDrives=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        StationComment replaceDrive = new Gson().fromJson(item.toString(), StationComment.class);
                        mFourShopComments.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mFourShopCommentAdapter.loadMoreEnd(true);
                        } else {
                            mFourShopCommentAdapter.addData(replaceDrives);
                            mFourShopCommentAdapter.loadMoreComplete();
                        }
                    } else {
                        mFourShopCommentAdapter.setNewData(mFourShopComments);

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
