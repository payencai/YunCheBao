package com.example.yunchebao.maket;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.adapter.GoodsTypeAdapter;
import com.example.yunchebao.maket.model.GoodList;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentGoodsActivity extends AppCompatActivity {
   @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
   @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    GoodsTypeAdapter mGoodsTypeAdapter;
    List<GoodList> mGoodLists;
    int page=1;
    String id;
    boolean isLoadMore= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_goods);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGoodLists=new ArrayList<>();
        mGoodsTypeAdapter=new GoodsTypeAdapter(R.layout.item_know_you,mGoodLists);
        mGoodsTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",mGoodLists.get(position));
                ActivityAnimationUtils.commonTransition(RentGoodsActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        mGoodsTypeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getRentestGoods();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mGoodLists.clear();
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getRentestGoods();
            }
        });
        rv_goods.setLayoutManager(new LinearLayoutManager(this));
        rv_goods.setAdapter(mGoodsTypeAdapter);
        getRentestGoods();
    }
    private void getRentestGoods(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("secondId",id);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getCommodityByDistince, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                mRefreshLayout.finishRefresh();
                Log.e("getCommodityByDistince", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodList goodList = new Gson().fromJson(item.toString(), GoodList.class);
                        mGoodLists.add(goodList);
                    }
                    mGoodsTypeAdapter.setNewData(mGoodLists);
                    if(isLoadMore){
                        isLoadMore=false;
                        mGoodsTypeAdapter.loadMoreComplete();
                    }else{
                        mGoodsTypeAdapter.loadMoreEnd(true);
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
