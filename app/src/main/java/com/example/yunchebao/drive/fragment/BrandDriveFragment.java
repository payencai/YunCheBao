package com.example.yunchebao.drive.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.drive.adapter.BrandAdapter;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.WebCarActivity;
import com.system.WebviewActivity;
import com.system.adapter.HomeListAdapter;
import com.system.model.HomeImage;

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
public class BrandDriveFragment extends Fragment {
    List<HomeImage> mHomeImages;
    BrandAdapter mHomeListAdapter;
    @BindView(R.id.rv_drive)
    RecyclerView rv_drive;
    public BrandDriveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_brand_drive, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        mHomeImages=new ArrayList<>();
        mHomeListAdapter=new BrandAdapter(R.layout.item_home,mHomeImages);
        mHomeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeImage homeImage=mHomeImages.get(position);
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                intent.putExtra("url",homeImage.getUrl());
                startActivity(intent);
            }
        });
        rv_drive.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_drive.setAdapter(mHomeListAdapter);

        getHomeImage();
    }
    private void getHomeImage() {

        Map<String, Object> params = new HashMap<>();
        params.put("superId", 6);
        HttpProxy.obtain().get(PlatformContans.Commom.getSkipUrlResult, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        HomeImage urlBean = new Gson().fromJson(item.toString(), HomeImage.class);
                        mHomeImages.add(urlBean);
                    }
                    mHomeListAdapter.setNewData(mHomeImages);
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
