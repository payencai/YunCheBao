package com.bbcircle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.bbcircle.adapter.BBCircleListAdapter;
import com.bbcircle.data.SelfDrive;
import com.entity.PhoneArticleEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/8.
 * 自驾游报名成功
 */

public class DrivingSelfReplaySuccessActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    ListViewForScrollView listView;
    private List<SelfDrive> list;
    private BBCircleListAdapter adapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driving_reply_success);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "发布成功");
        list = new ArrayList<>();

    }


    @OnClick({R.id.back, R.id.toDetail})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.toDetail:
                break;
        }
    }
}
