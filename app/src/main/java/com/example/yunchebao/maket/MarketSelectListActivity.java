package com.example.yunchebao.maket;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.adapter.GoodsTypeAdapter;
import com.example.yunchebao.maket.model.GoodList;
import com.nohttp.sample.NoHttpBaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.CarBrandSelectActivity;

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
 * Created by sdhcjhss on 2017/11/16.
 * 商城 商品列表
 */

public class MarketSelectListActivity extends NoHttpBaseActivity {
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
    @BindView(R.id.rankPrice)
    TextView rankPriceBtn;
    @BindView(R.id.rankSale)
    TextView rankSaleBtn;
    @BindView(R.id.rankDefault)
    TextView rankDefaultBtn;
    @BindView(R.id.et_search)
    EditText et_search;
    Resources res;
    int page=1;
    String id;
    boolean isPriceUp = false, isSaleUp = false;
    private int type = 0;//选中的是哪个排序方式
    String orderByClause;
    String sort;
    String minPrice;
    String maxPrice;
    String keyword;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    GoodsTypeAdapter mGoodsTypeAdapter;
    List<GoodList> mGoodLists;
    boolean isLoadMore= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getExtras().getString("id");
        setContentView(R.layout.market_list_layout);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        res = getResources();
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
                ActivityAnimationUtils.commonTransition(MarketSelectListActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        mGoodsTypeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData(id);
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mGoodLists.clear();
                maxPrice="";
                minPrice="";
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getData(id);
            }
        });
        rv_goods.setLayoutManager(new LinearLayoutManager(this));
        rv_goods.setAdapter(mGoodsTypeAdapter);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    keyword = et_search.getText().toString().trim();

                    if (TextUtils.isEmpty(keyword)) {
                        Toast.makeText(MarketSelectListActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                    page=1;
                    mGoodLists.clear();
                    mGoodsTypeAdapter.setNewData(mGoodLists);
                    getData(id);
                    // 搜索功能主体

                    return true;
                }
                return false;
            }
        });

        getData(id);

    }

    private void getData(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("secondId",id);
        if(!TextUtils.isEmpty(orderByClause)){
            params.put("orderByClause",orderByClause);
        }
        if(!TextUtils.isEmpty(sort)){
            params.put("sort",sort);
        }
        if(!TextUtils.isEmpty(minPrice)){
            params.put("minPrice",minPrice);
        }
        if(!TextUtils.isEmpty(maxPrice)){
            params.put("maxPrice",maxPrice);
        }
        if(!TextUtils.isEmpty(keyword)){
            params.put("keyword",keyword);
        }
        HttpProxy.obtain().get(PlatformContans.GoodMenu.getGoodList, params, new ICallBack() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1&data!=null){
            minPrice=data.getStringExtra("min");
            maxPrice=data.getStringExtra("max");
            page=1;
            mGoodLists.clear();
            getData(id);
        }
    }

    @OnClick({R.id.back, R.id.selectBtn, R.id.rankDefault, R.id.rankPrice, R.id.rankSale, R.id.pleaseLay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.selectBtn:
                startActivityForResult(new Intent(MarketSelectListActivity.this,MarketSelectActivity.class),1);
                //ActivityAnimationUtils.commonTransition(MarketSelectListActivity.this, MarketSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rankPrice:
                orderByClause="price";
                if (type == 2) {
                    isPriceUp = isPriceUp ? false : true;
                }
                type = 2;
                if (isPriceUp) {
                    sort="asc";
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_up_small);
                    rankPriceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                } else {
                    sort="desc";
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_small);
                    rankPriceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                }
                rankDefaultBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                rankSaleBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                rankPriceBtn.setTextColor(ContextCompat.getColor(this, R.color.yellow_65));
                page=1;
                mGoodLists.clear();
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getData(id);
                break;
            case R.id.rankSale:
                orderByClause="orderNum";
                if (type == 1) {
                    isPriceUp = isPriceUp ? false : true;
                }
                type = 1;
                rankSaleBtn.setTextColor(ContextCompat.getColor(this, R.color.yellow_65));
                isSaleUp = isSaleUp ? false : true;
                if (isSaleUp) {
                    sort="asc";
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_up_small);
                    rankSaleBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                } else {
                    sort="desc";
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_small);
                    rankSaleBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                }
                rankPriceBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                rankDefaultBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                page=1;
                mGoodLists.clear();
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getData(id);
                break;
            case R.id.rankDefault:
                type = 0;
                sort="";
                maxPrice="";
                minPrice="";
                orderByClause="";
                rankSaleBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                rankPriceBtn.setTextColor(ContextCompat.getColor(this, R.color.black_33));
                rankDefaultBtn.setTextColor(ContextCompat.getColor(this, R.color.yellow_65));
                page=1;
                mGoodLists.clear();
                mGoodsTypeAdapter.setNewData(mGoodLists);
                getData(id);
                break;
            case R.id.pleaseLay:
                startActivityForResult(new Intent(MarketSelectListActivity.this, CarBrandSelectActivity.class), 1);
                break;
        }
    }
}
