package com.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

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

    private void initView() {
        state=getArguments().getInt("state");
        mCarOrders = new ArrayList<>();
        mCarOrderAdapter = new NewPublishAdapter(R.layout.item_mypublish, mCarOrders);
        mCarOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getWashOrder();
            }
        }, rv_order);
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mCarOrderAdapter);
        getWashOrder();

    }

    private void getWashOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("audit", 2);
        params.put("state", state);
        params.put("page", page);
        Log.e("params",params.toString());
        HttpProxy.obtain().get(PlatformContans.OldCar.getOldCarMerchantCarByUser, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
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
