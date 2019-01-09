package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.view.PayWayDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tool.MyProgressDialog.dialog;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class AccountDepositActivity extends NoHttpBaseActivity {
    @BindView(R.id.textBtn)
    TextView rightText;
    @BindView(R.id.depositEdit)
    EditText depositEdit;
    @BindView(R.id.nextBtn)
    SuperTextView nextBtn;

    Context ctx;
    public static AccountDepositActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_deposit_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
        activity = this;
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "账户充值");
        rightText.setVisibility(View.VISIBLE);
        rightText.setTextColor(ContextCompat.getColor(ctx,R.color.yellow_65));
        rightText.setText("限额说明");
        depositEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.toString() != null && !s.toString().equals("")){
                    nextBtn.setSolid(ContextCompat.getColor(ctx,R.color.yellow_65));
                    nextBtn.setTextColor(ContextCompat.getColor(ctx,R.color.white));
                }
            }
        });
    }


    /**
     * 初始化支付方式Dialog
     */
    private void initDialog() {
        // 隐藏输入法
        dialog = new PayWayDialog(this, R.style.recharge_pay_dialog, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认充值
            }
        });
        dialog.show();
    }

    @OnClick({R.id.back,R.id.nextBtn,R.id.textBtn,R.id.accountLay})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.nextBtn:
                if (depositEdit.getText() != null && !depositEdit.getText().toString().equals("")){

                }
                break;
            case R.id.textBtn:
                ActivityAnimationUtils.commonTransition(AccountDepositActivity.this,LimitExplanationActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.accountLay:
                initDialog();
                break;
        }
    }
}
