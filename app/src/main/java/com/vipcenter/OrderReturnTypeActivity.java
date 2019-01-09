package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
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

public class OrderReturnTypeActivity extends NoHttpBaseActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_return_type_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "选择服务类型");
        ButterKnife.bind(this);
        ctx = this;
    }

    @OnClick({R.id.back,R.id.lay1})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                ActivityAnimationUtils.commonTransition(OrderReturnTypeActivity.this,OrderReturnApplyActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
