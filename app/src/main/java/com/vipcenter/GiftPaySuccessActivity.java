package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.gift.GiftMarketHomeActivity;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.GridViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/15.
 * 礼品汇 积分兑换成功
 */

public class GiftPaySuccessActivity extends NoHttpBaseActivity {
    @BindView(R.id.gridView)
    GridViewForScrollView gridView;
    List<PhoneGoodEntity> list;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_pay_success_layout);
        initView();
    }

    private void initView() {
        ctx = this;
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "积分兑换成功");
        findViewById(R.id.title).setFocusable(true);
        findViewById(R.id.title).setFocusableInTouchMode(true);
        findViewById(R.id.title).requestFocus();
        list = new ArrayList<>();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(GiftPaySuccessActivity.this, GiftGoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
    }

    @OnClick({R.id.back, R.id.menuLay1, R.id.menuLay2})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menuLay1:
                ActivityAnimationUtils.commonTransition(GiftPaySuccessActivity.this, GiftMyOrderListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.menuLay2:
                ActivityAnimationUtils.commonTransition(GiftPaySuccessActivity.this, GiftMarketHomeActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
