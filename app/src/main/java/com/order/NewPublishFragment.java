package com.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import com.payencai.library.util.ToastUtil;
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
public class NewPublishFragment extends Fragment {

    int page = 1;
    boolean isLoadMore = false;
    int state=1;
    public NewPublishFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    List<NewPublish> mCarOrders;
    NewPublishAdapter mCarOrderAdapter;

    public static NewPublishFragment newInstance(int state) {
        NewPublishFragment orderFragment = new NewPublishFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_publish, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }
   private void cancelsale(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("id",id) ;
        String json=new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.OldCar.delOldCarMerchantCar, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        page=1;
                        mCarOrders.clear();
                        mCarOrderAdapter.setNewData(mCarOrders);
                        getOldCar();
                        ToastUtil.showToast(getContext(),"取消成功");
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
    View empty;
    private void initView() {
        state=getArguments().getInt("state");
        mCarOrders = new ArrayList<>();
        mCarOrderAdapter = new NewPublishAdapter(R.layout.item_mypublish, mCarOrders);
        mCarOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getOldCar();
            }
        }, rv_order);
        empty=LayoutInflater.from(getContext()).inflate(R.layout.empty_web,null);
        mCarOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NewPublish newPublish= (NewPublish) adapter.getItem(position);
                if(view.getId()==R.id.tv_cancel){
                    cancelsale(newPublish.getId());
                }
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mCarOrderAdapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mCarOrders.clear();
                mCarOrderAdapter.setNewData(mCarOrders);
                getOldCar();
            }
        });
        getOldCar();

    }

    private void getOldCar() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if(state==1){
            params.put("audit",2);
            params.put("state", 1);
        }else if(state==2){
            params.put("audit", 2);
            params.put("state", 2);
        }else{
            params.put("audit",3);
        }
        Log.e("params",params.toString());
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarByUser, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getold",result);
                refresh.finishRefresh();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        jsonObject=jsonObject.getJSONObject("data");
                        JSONArray data = jsonObject.getJSONArray("beanList");
                        List<NewPublish> mCarOrder = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            NewPublish carOrder = new Gson().fromJson(item.toString(), NewPublish.class);
                            mCarOrder.add(carOrder);
                        }
                        if(page==1&&data.length()==0){
                            mCarOrderAdapter.setEmptyView(empty);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mCarOrderAdapter.addData(mCarOrder);
                            if (data.length() > 0)
                                mCarOrderAdapter.loadMoreComplete();
                            else {
                                mCarOrderAdapter.loadMoreEnd(true);
                            }
                        } else {
                            mCarOrderAdapter.setNewData(mCarOrder);
                        }
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
