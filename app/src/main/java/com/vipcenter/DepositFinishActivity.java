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
 * Created by sdhcjhss on 2018/1/3.
 */

public class DepositFinishActivity extends NoHttpBaseActivity {
    Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_finish_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
    }

    @OnClick({R.id.nextBtn})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.nextBtn:
                ActivityAnimationUtils.commonTransition(DepositFinishActivity.this,MyWalletActivity.class, ActivityConstans.Animation.FADE);
                if (AccountDepositActivity.activity != null ){
                    AccountDepositActivity.activity.finish();
                }
                DepositFinishActivity.this.finish();
                break;
        }
    }
}
