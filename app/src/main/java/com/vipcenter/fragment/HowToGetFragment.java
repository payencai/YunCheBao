package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.vipcenter.adapter.CoinRuleAdapter;
import com.vipcenter.model.CoinRlue;

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
 * Created by sdhcjhss on 2018/1/15.
 * 宝币使用帮助--如何获得
 */

public class HowToGetFragment extends BaseFragment {
    private Context ctx;
    CoinRuleAdapter mCoinRuleAdapter;
    List<CoinRlue> mCoinRlues;
    @BindView(R.id.rv_content)
    RecyclerView rv_content;
    int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gift_how_to_get, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
        mCoinRlues=new ArrayList<>();
        mCoinRuleAdapter=new CoinRuleAdapter(R.layout.item_coin_rule,mCoinRlues);
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(mCoinRuleAdapter);
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Gift.getCoinRuleList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    jsonObject =jsonObject.getJSONObject("data");
                    JSONArray data=jsonObject.getJSONArray("beanList");
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject item=data.getJSONObject(i);
                        CoinRlue coinRlue=new Gson().fromJson(item.toString(),CoinRlue.class);
                        mCoinRlues.add(coinRlue);
                    }
                    mCoinRuleAdapter.setNewData(mCoinRlues);
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
