package com.vipcenter.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.vipcenter.adapter.RecordListAdapter;
import com.vipcenter.model.GiftRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GiftBaobiCenterAllFragment extends BaseFragment {

    private String keywords;

    private List<GiftRecord> list = new ArrayList<>();
    RecordListAdapter adapter;
    private View view;
    RecyclerView recyclerView;
    int page=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_evaluate_list, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        init();
        list.clear();
        getData();
        return view;
    }


    public static GiftBaobiCenterAllFragment getInstance(String mTitle) {
        GiftBaobiCenterAllFragment tabFragment = null;
        if (tabFragment == null) {
            tabFragment = new GiftBaobiCenterAllFragment();
        }
        tabFragment.keywords = mTitle;
        return tabFragment;
    }

    public void getData(){

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().post(PlatformContans.Gift.getGiftOrderListByUserId,MyApplication.token,params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getRecordtByUserId", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GiftRecord gift = new Gson().fromJson(item.toString(), GiftRecord.class);
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

    private void init() {
        adapter = new RecordListAdapter(R.layout.gift_record_list_item_layout,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


}

