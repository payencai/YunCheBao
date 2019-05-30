package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cheyibao.adapter.RvCommentAdapter;
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
public class RentComentFragment extends Fragment {

    private List<ShopComment> list;
    private RvCommentAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    int page = 1;
    String id;
    boolean isLoadMore=false;
    public RentComentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rent_coment, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {

        list = new ArrayList<>();
        adapter = new RvCommentAdapter(R.layout.item_shop_comment, list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        getData();

    }

    public void getData() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("type", 3);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getUserComment, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getUserComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ShopComment baikeItem = new Gson().fromJson(item.toString(), ShopComment.class);
                        list.add(baikeItem);
                    }

                    adapter.notifyDataSetChanged();
                    if(isLoadMore){
                        isLoadMore=false;

                    }
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
