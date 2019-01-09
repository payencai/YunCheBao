package com.cheyibao;

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
 * Created by sdhcjhss on 2017/12/28.
 */

public class OldCarConfigDetailActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_car_config_detail_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "车辆配置");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,R.id.askLowPriceBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.askLowPriceBtn:
                ActivityAnimationUtils.commonTransition(OldCarConfigDetailActivity.this,AskLowPriceActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
