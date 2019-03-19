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
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class MyCustomerServiceActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_customer_service_home);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我的客服");
        ButterKnife.bind(this);
    }

    public void callToPhoneSweetAlert(final String phone) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定要电话联系商家吗？")
                .setConfirmText("拨打")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        callToPhone(phone);
                    }
                })
                .setCancelText("算了")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick({R.id.back, R.id.lay1, R.id.lay2})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
               // ActivityAnimationUtils.commonTransition(MyCustomerServiceActivity.this, MyCustomServiceChatActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay2:
                callToPhoneSweetAlert("0871-68106401");
                break;
        }
    }
}
