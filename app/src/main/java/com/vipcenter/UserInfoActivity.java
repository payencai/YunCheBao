package com.vipcenter;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class UserInfoActivity extends NoHttpBaseActivity {
    @BindView(R.id.sd_head)
    SimpleDraweeView sd_head;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_sex)
    TextView tv_sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "个人资料");
        ButterKnife.bind(this);
        sd_head.setImageURI(Uri.parse(MyApplication.getUserInfo().getHeadPortrait()));
        tv_account.setText(MyApplication.getUserInfo().getHxAccount());
        tv_nickname.setText(MyApplication.getUserInfo().getUsername());
        tv_sex.setText(MyApplication.getUserInfo().getSex());
    }

    @OnClick({R.id.back, R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.addressLay:
                ActivityAnimationUtils.commonTransition(UserInfoActivity.this, AddressListActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
