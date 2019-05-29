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
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.indexbar.RecCarAdapter;
import com.xihubao.CarBrandSelectActivity;
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

public class SelectCarBrandActivity extends AppCompatActivity {


    @BindView(R.id.recView)
    RecyclerView mRecyclerView;
    RecCarAdapter adapter;
    List<CarBrand> mCarBrands=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_brand);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();


    }


    String name;
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
                name=mCarBrands.get(position).getName();
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("id", mCarBrands.get(position).getId());
                setResult(1, intent);
                finish();
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
        getData(1);
    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    private void getData(int level){
        Map<String,Object> params=new HashMap<>();
        params.put("level",level);
        HttpProxy.obtain().get(PlatformContans.CarCategory.getFirstCategory, params, "",new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarBrand baikeItem = new Gson().fromJson(item.toString(), CarBrand.class);
                        mCarBrands.add(baikeItem);
                    }
                    adapter.addDatas(mCarBrands);
                    Log.e("getdata", result);
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
