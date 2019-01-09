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
 * Created by sdhcjhss on 2018/1/3.
 */

public class UserInfoActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "个人资料");
        ButterKnife.bind(this);
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
