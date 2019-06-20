package com.xihubao;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.RentGoodsActivity;
import com.example.yunchebao.maket.adapter.GoodsTypeAdapter;
import com.example.yunchebao.maket.model.GoodList;
import com.nohttp.sample.NoHttpBaseActivity;
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
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class NewGoodsListActivity extends NoHttpBaseActivity {
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    GoodsTypeAdapter mGoodsTypeAdapter;
    List<GoodList> mGoodLists;
    int page=1;

    boolean isLoadMore= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_good);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {

        mGoodLists=new ArrayList<>();
        mGoodsTypeAdapter=new GoodsTypeAdapter(R.layout.item_know_you,mGoodLists);
        mGoodsTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodList goodList= (GoodList) adapter.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",goodList);
                ActivityAnimationUtils.commonTransition(NewGoodsListActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        mGoodsTypeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getHotGoods();
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mGoodLists.clear();
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getHotGoods();
            }
        });
        rv_goods.setLayoutManager(new LinearLayoutManager(this));
        rv_goods.setAdapter(mGoodsTypeAdapter);
        getHotGoods();
    }

    private void getHotGoods(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getHotCommodity, params,new ICallBack() {
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
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
