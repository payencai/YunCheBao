package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderDetailActivity extends NoHttpBaseActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "订单详情");
        ButterKnife.bind(this);
        ctx = this;
    }

    @OnClick({R.id.back, R.id.complaintLay, R.id.lianxi})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.complaintLay:
                ActivityAnimationUtils.commonTransition(OrderDetailActivity.this, OrderComplaintActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lianxi:
                //ActivityAnimationUtils.commonTransition(OrderDetailActivity.this, OrderChatDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
