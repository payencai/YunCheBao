package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.model.OldCar;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class SellerDetailActivity extends NoHttpBaseActivity {
    private List<OldCar> list;
    private CarRecommendListAdapter adapter;
    private Context ctx;

    @BindView(R.id.listview)
    ListViewForScrollView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_detail_list_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"云南隆创尚通二手车");
        ButterKnife.bind(this);

        list = new ArrayList<>();
        ctx = this;
        adapter = new CarRecommendListAdapter(ctx,list);
        listView.setAdapter(adapter);
    }

    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
