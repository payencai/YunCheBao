package com.system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.adapter.HomeListAdapter;
import com.system.model.HomeImage;
import com.tool.view.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebCarActivity extends AppCompatActivity {
    List<HomeImage> mHomeImages;
    HomeListAdapter mHomeListAdapter;
    @BindView(R.id.lv_web)
    ListView lv_home;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_car);
        ButterKnife.bind(this);
        id=getIntent().getIntExtra("id",-1);
        mHomeImages=new ArrayList<>();
        mHomeListAdapter=new HomeListAdapter(this,mHomeImages);
        lv_home.setAdapter(mHomeListAdapter);
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeImage homeImage=mHomeImages.get(position);
                Intent intent = new Intent(WebCarActivity.this, WebviewActivity.class);
                intent.putExtra("url",homeImage.getUrl());
                startActivity(intent);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getHomeImage();
    }
    private void getHomeImage() {

        Map<String, Object> params = new HashMap<>();
        params.put("superId", id);
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
                    mHomeListAdapter.notifyDataSetChanged();
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
