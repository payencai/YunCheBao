package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.GiftOrderListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 * 礼品汇我的订单
 */

public class GiftMyOrderListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    private GiftOrderListAdapter adapter;
    private List<PhoneGoodEntity> list;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_newonly);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我的订单");
        ButterKnife.bind(this);
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        ctx = this;
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_ee));
        listView.setDividerHeight(12);
        list = new ArrayList<>();
        adapter = new GiftOrderListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(GiftMyOrderListActivity.this, OrderDetailActivity.class, ActivityConstans.Animation.FADE);
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
