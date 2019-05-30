package com.xihubao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cheyibao.CarSecondBrandActivity;
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


public class CarBrandSelectActivity extends AppCompatActivity {
    @BindView(R.id.recView)
    RecyclerView mRecyclerView;
    RecCarAdapter adapter;
    List<CarBrand> mCarBrands=new ArrayList<>();

    private boolean oneLevelSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_brand_list_layout);
        initView();
        oneLevelSelect = getIntent().getBooleanExtra("oneLevelSelect",false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(data!=null){
                Intent intent = new Intent();
               // Log.e("data",data.getStringExtra("id"));
                String value=name+" "+data.getStringExtra("name");
                intent.putExtra("name", value);
                intent.putExtra("logo", data.getStringExtra("logo"));
                intent.putExtra("id", data.getStringExtra("id"));
                intent.putExtra("id1", data.getStringExtra("id1"));
                intent.putExtra("id2", data.getStringExtra("id2"));
                intent.putExtra("id3", data.getStringExtra("id3"));
                setResult(1, intent);
                finish();
            }
        }
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
                if(oneLevelSelect){
                    Intent intent = new Intent();
                    intent.putExtra("logo", mCarBrands.get(position).getImage());
                    intent.putExtra("name", mCarBrands.get(position).getName());
                    setResult(1, intent);
                    finish();
                }else {
                    Intent intent = new Intent(CarBrandSelectActivity.this, CarSecondBrandActivity.class);
                    intent.putExtra("id", mCarBrands.get(position).getId());
                    intent.putExtra("logo", mCarBrands.get(position).getImage());
                    startActivityForResult(intent, 1);
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
