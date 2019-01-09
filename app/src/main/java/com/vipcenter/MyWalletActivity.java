package com.vipcenter;

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
 * Created by sdhcjhss on 2018/1/2.
 */

public class MyWalletActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.toMoreBtn,R.id.consumptionLay,R.id.depositLay,R.id.toDeposit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.toMoreBtn:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, GiftMarketHomeActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.consumptionLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, ConsumptionListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.depositLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, DepositListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.toDeposit:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this,AccountDepositActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
