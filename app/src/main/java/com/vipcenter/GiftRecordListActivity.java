package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baike.MagzineCoverActivity;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.RecordListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 * 兑换记录
 */

public class GiftRecordListActivity extends NoHttpBaseActivity {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView refreshListView;

    private RecordListAdapter adapter;
    private List<PhoneGoodEntity> list;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_all);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

//        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
//        listView.setDividerHeight(1);
//        list = new ArrayList<>();
//        adapter = new RecordListAdapter(R.layout.gift_record_list_item_layout, list);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ActivityAnimationUtils.commonTransition(GiftRecordListActivity.this, GiftGoodDetailActivity.class, ActivityConstans.Animation.FADE);
//            }
//        });
    }


}
