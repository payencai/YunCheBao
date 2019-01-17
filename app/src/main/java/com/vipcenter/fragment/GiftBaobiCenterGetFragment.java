package com.vipcenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneCommentEntity;
import com.entity.PhoneShopEntity;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.vipcenter.adapter.GiftMoneyAdapter;
import com.vipcenter.model.GiftCoin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GiftBaobiCenterGetFragment extends BaseFragment {

    private String keywords;


    private List<GiftCoin> list = new ArrayList<>();
    GiftMoneyAdapter adapter;
    private View view;
    RecyclerView recyclerView;
    int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_evaluate_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        view.findViewById(R.id.noResult).setVisibility(View.GONE);
        init();
        list.clear();
        getData();
        return view;
    }


    public static GiftBaobiCenterGetFragment getInstance(String mTitle) {
        GiftBaobiCenterGetFragment tabFragment = null;
        if (tabFragment == null) {
            tabFragment = new GiftBaobiCenterGetFragment();
        }
        tabFragment.keywords = mTitle;
        return tabFragment;
    }


    private void init() {
        adapter = new GiftMoneyAdapter(R.layout.baobi_center_list_item, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    public void getData() {
        Log.e("token", MyApplication.getUserInfo().getToken());
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state",1);
        HttpProxy.obtain().post(PlatformContans.Gift.getCoinRecordByUserId, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getRecordtByUserId", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GiftCoin gift = new Gson().fromJson(item.toString(), GiftCoin.class);
                        list.add(gift);
                    }

                    adapter.notifyDataSetChanged();
                    //updateData();

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

