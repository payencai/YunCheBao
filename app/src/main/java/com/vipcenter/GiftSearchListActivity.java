package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.baike.MagzineCoverActivity;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.GiftHomeListAdapter;
import com.vipcenter.adapter.RecordListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 * 商品列表
 */

public class GiftSearchListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshGridView refreshListView;
    GridView listView;
    private GiftHomeListAdapter adapter;
    private List<PhoneGoodEntity> list;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_only);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.topSearchPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.back).setVisibility(View.VISIBLE);
        findViewById(R.id.user_center_icon).setVisibility(View.GONE);

        ctx = this;
        listView = refreshListView.getRefreshableView();
        list = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(GiftSearchListActivity.this, MagzineCoverActivity.class, ActivityConstans.Animation.FADE);
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
