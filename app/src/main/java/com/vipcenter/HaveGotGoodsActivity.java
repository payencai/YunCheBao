package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.entity.PhoneGoodEntity;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.vipcenter.adapter.OrderConfirmGridListAdapter;
import com.vipcenter.adapter.OrderConfirmHoriListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class HaveGotGoodsActivity extends NoHttpBaseActivity {
    @BindView(R.id.horiListview)
    HorizontalListView horizontalListView;
    @BindView(R.id.gridView)
    GridViewForScrollView gridView;
    OrderConfirmHoriListAdapter horiListAdapter;
    private List<PhoneOrderEntity> horiList;
    OrderConfirmGridListAdapter gridListAdapter;
    private List<PhoneGoodEntity> gridList;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.have_got_goods_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "确认收货成功");
        ButterKnife.bind(this);
        ctx = this;
        horiList = new ArrayList<>();
        gridList = new ArrayList<>();
        horiListAdapter = new OrderConfirmHoriListAdapter(ctx, horiList, this);
        horizontalListView.setAdapter(horiListAdapter);
        gridListAdapter = new OrderConfirmGridListAdapter(ctx, gridList, this);
        gridView.setAdapter(gridListAdapter);
    }

    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int location = loc.intValue();
        switch (t) {
            case 0://去评价
                ActivityAnimationUtils.commonTransition(HaveGotGoodsActivity.this, OrderCommentSubmitActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 1://取消
                break;
            case 2://商品详情
                break;
        }
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
