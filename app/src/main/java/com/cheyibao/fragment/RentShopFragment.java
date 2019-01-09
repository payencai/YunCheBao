package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.application.MyApplication;
import com.cheyibao.adapter.RentTypeAdapter;
import com.cheyibao.adapter.ShopCommentAdapter;
import com.cheyibao.list.SpreadListView;
import com.cheyibao.model.RentCarType;
import com.cheyibao.model.ShopComment;
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
public class RentShopFragment extends Fragment {

    private List<RentCarType> list;
    private RentTypeAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    SpreadListView listView;
    int page = 1;
    String id;
    public RentShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_rent_shop, container, false);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_shop, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        listView.setFocusable(false);
        list = new ArrayList<>();
        adapter = new RentTypeAdapter(getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        //getData();

    }

    public void getData() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state", 1);
        params.put("merchantId","");
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarCarList, params,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getRentCarCarList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RentCarType baikeItem = new Gson().fromJson(item.toString(), RentCarType.class);
                        list.add(baikeItem);
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