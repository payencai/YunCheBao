package com.maket;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.adapter.GoodCollectListAdapter;
import com.xihubao.CarBrandSelectActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/11/16.
 * 商城 商品列表
 */

public class MarketSelectListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    private Context ctx;
    private List<PhoneGoodEntity> list;
    private GoodCollectListAdapter adapter;
    @BindView(R.id.rankPrice)
    TextView rankPriceBtn;
    @BindView(R.id.rankSale)
    TextView rankSaleBtn;
    @BindView(R.id.rankDefault)
    TextView rankDefaultBtn;
    Resources res;
    boolean isPriceUp = false, isSaleUp = false;
    private int type = 0;//选中的是哪个排序方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_list_layout);
        initView();
//        requestMethod(0,"");
    }


    private void initView() {
        findViewById(R.id.back).setVisibility(View.VISIBLE);
        findViewById(R.id.user_center_icon).setVisibility(View.GONE);
        ctx = this;
        ButterKnife.bind(this);
        res = getResources();
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new GoodCollectListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(MarketSelectListActivity.this, GoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });

    }


    @OnClick({R.id.back, R.id.selectBtn, R.id.rankDefault, R.id.rankPrice, R.id.rankSale, R.id.pleaseLay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.selectBtn:
                ActivityAnimationUtils.commonTransition(MarketSelectListActivity.this, MarketSelectActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.rankPrice:
                if (type == 2) {
                    isPriceUp = isPriceUp ? false : true;
                }
                type = 2;
                if (isPriceUp) {
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_up_small);
                    rankPriceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                } else {
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_small);
                    rankPriceBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                }
                rankDefaultBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                rankSaleBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                rankPriceBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
                break;
            case R.id.rankSale:
                if (type == 1) {
                    isPriceUp = isPriceUp ? false : true;
                }
                type = 1;
                rankSaleBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
                isSaleUp = isSaleUp ? false : true;
                if (isSaleUp) {
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_up_small);
                    rankSaleBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                } else {
                    Drawable myImage = res.getDrawable(R.mipmap.yellow_arrow_small);
                    rankSaleBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, myImage, null);
                }
                rankPriceBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                rankDefaultBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                break;
            case R.id.rankDefault:
                type = 0;
                rankSaleBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                rankPriceBtn.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                rankDefaultBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
                break;
            case R.id.pleaseLay:
                startActivityForResult(new Intent(MarketSelectListActivity.this, CarBrandSelectActivity.class), 1);
                break;
        }
    }
}
