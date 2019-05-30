package com.cheyibao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.indexbar.RecCarAdapter;
import com.xihubao.model.CarBrand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import qdx.indexbarlayout.IndexBar;
import qdx.indexbarlayout.IndexLayout;
import qdx.stickyheaderdecoration.NormalDecoration;

public class CarSecondBrandActivity extends AppCompatActivity {
    @BindView(R.id.recView)
    RecyclerView mRecyclerView;
    RecCarAdapter adapter;
    List<CarBrand> mOldCarBrands = new ArrayList<>();
    List<CarBrand> mCarBrands = new ArrayList<>();
    String id;
    String name;
    String logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_second_brand);
        id = getIntent().getStringExtra("id");
        logo=getIntent().getStringExtra("logo");
        initView();
    }
    private void getFourData(CarBrand carBrand) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", carBrand.getId());
        HttpProxy.obtain().get(PlatformContans.CarCategory.getSubclass, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray jsonArray=jsonObject.getJSONArray("param");
                    if(jsonArray!=null){
                        JSONObject item=jsonArray.getJSONObject(0);
                        JSONArray params=item.getJSONArray("param");
                        if(params!=null&&params.length()>0){
                            JSONObject param=params.getJSONObject(0);
                            detailId=param.getString("carCategoryDetailId");
                        }
                    }
                    String id1=carBrand.getFistId();
                    String id2=carBrand.getSecondId();
                    String id3=carBrand.getThirdId();
                    Intent intent = new Intent();
                    String value=name+" "+carBrand.getName();
                    intent.putExtra("name", value);
                    intent.putExtra("logo", logo);
                    intent.putExtra("id1",id1);
                    intent.putExtra("id2",id2);
                    intent.putExtra("id3",id3);
                    intent.putExtra("id",detailId);
                    setResult(1, intent);
                    finish();
                    Log.e("getSecData", result);
                    //adapter.notifyDataSetChanged();
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
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "选品牌");
        ButterKnife.bind(this);

//        CarBrand carBean = new Utils().readFromAssets(CarBrandSelectActivity.this);
//        final List<CarBean.CarInfo> carList = carBean.getData();
        adapter = new RecCarAdapter(this);
        adapter.addDatas(mCarBrands);

        final NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return mCarBrands.get(pos).getInitial();
            }
        };

        decoration.setOnHeaderClickListener(new NormalDecoration.OnHeaderClickListener() {
            @Override
            public void headerClick(int pos) {
//                Toast.makeText(CarBrandSelectActivity.this, "点击到头部" + carList.get(pos).getInitial(), Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecCarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (mCarBrands.get(position).getLevel() == 2) {
                    name=mCarBrands.get(position).getName();
                    getThirdData(mCarBrands.get(position));
                } else {
                    getFourData(mCarBrands.get(position));
//                    Intent intent = new Intent();
//                    intent.putExtra("name", mCarBrands.get(position).getName());
//                    setResult(1, intent);
//                    finish();
                }
            }
        });


        //侧边导航栏
        IndexLayout indexLayout = (IndexLayout) findViewById(R.id.index_layout);
        List<String> heads = new ArrayList<>();
        for (CarBrand car : mCarBrands) {
            if (!heads.contains(car.getInitial())) {
                heads.add(car.getInitial());
            }
        }
        indexLayout.setIndexBarHeightRatio(0.9f);
        indexLayout.getIndexBar().setIndexsList(heads);
        indexLayout.getIndexBar().setIndexChangeListener(new IndexBar.IndexChangeListener() {
            @Override
            public void indexChanged(String indexName) {
                for (int i = 0; i < mCarBrands.size(); i++) {
                    if (indexName.equals(mCarBrands.get(i).getInitial())) {
                        manager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
        getSecData(id);
    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                if (mCarBrands.size() > 0) {
                    if (mCarBrands.get(0).getLevel() == 3) {
                        mCarBrands.clear();
                        mCarBrands.addAll(mOldCarBrands);
                        mOldCarBrands.clear();
                        adapter.addDatas(mCarBrands);
                    }
                    else{
                        finish();
                    }
                }
                //onBackPressed();
                break;
        }
    }
    String detailId;
    private void getThirdData(CarBrand carBrand) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", carBrand.getId());
        HttpProxy.obtain().get(PlatformContans.CarCategory.getSubclass, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getThirdData",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("carCategory");
                    if (data.length() == 0) {
                       // String id3;
                        String detailId="";
                        JSONArray jsonArray=jsonObject.getJSONArray("param");
                        if(jsonArray!=null){
                            JSONObject item=jsonArray.getJSONObject(0);
                            detailId=item.getString("carCategoryId");
                            JSONArray params=item.getJSONArray("param");
                            if(params!=null&&params.length()>0){
                                JSONObject param=params.getJSONObject(0);
                                detailId=param.getString("carCategoryDetailId");
                            }
                        }
                        String id1=carBrand.getFistId();
                        String id2=carBrand.getSecondId();
                        String id3=carBrand.getThirdId();
                        Intent intent = new Intent();
                        intent.putExtra("name", carBrand.getName());
                        intent.putExtra("id1",id1);
                        intent.putExtra("id2",id2);
                        intent.putExtra("id3",id3);
                        intent.putExtra("id",detailId);
                        intent.putExtra("logo", logo);
                        setResult(1, intent);
                        finish();
                    } else {

                        mOldCarBrands.clear();
                        mOldCarBrands.addAll(mCarBrands);
                        mCarBrands.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            CarBrand baikeItem = new Gson().fromJson(item.toString(), CarBrand.class);
                            mCarBrands.add(baikeItem);
                        }
                        adapter.addDatas(mCarBrands);
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

    private void getSecData(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", id);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getSubclass, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("carCategory");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarBrand baikeItem = new Gson().fromJson(item.toString(), CarBrand.class);
                        mCarBrands.add(baikeItem);
                    }
                    adapter.addDatas(mCarBrands);
                    Log.e("getSecData", result);
                    //adapter.notifyDataSetChanged();
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