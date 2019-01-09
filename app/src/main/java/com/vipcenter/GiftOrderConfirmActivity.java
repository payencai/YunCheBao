package com.vipcenter;

import android.content.Intent;
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
 * Created by sdhcjhss on 2018/1/16.
 */

public class GiftOrderConfirmActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_order_confirm);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "商品填写");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.submitBtn,R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(GiftOrderConfirmActivity.this,AddressListActivity.class),1);
                break;
            case R.id.submitBtn:
                ActivityAnimationUtils.commonTransition(GiftOrderConfirmActivity.this, GiftPaySuccessActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
