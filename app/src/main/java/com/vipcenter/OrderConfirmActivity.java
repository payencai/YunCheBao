package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.view.PayCashierDialog;
import com.vipcenter.view.PayWayDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tool.MyProgressDialog.dialog;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderConfirmActivity extends NoHttpBaseActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.order_confim_layout,null);
        setContentView(view);
        commHiddenKeyboard(view);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "确认订单");
        ButterKnife.bind(this);
        ctx = this;

    }

    /**
     * 初始化支付方式Dialog
     */
    private void initDialog() {
        // 隐藏输入法
        dialog = new PayCashierDialog(this, R.style.recharge_pay_dialog, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认充值
            }
        });
        dialog.show();
    }

    @OnClick({R.id.back, R.id.submit,R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                initDialog();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(OrderConfirmActivity.this,AddressListActivity.class),1);
                break;
        }
    }
}
