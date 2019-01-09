package com.yuedan;

import android.os.Bundle;
import android.view.View;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/24.
 */

public class BookNeedCancelDetailActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_need_cancel_detail_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "");
        int type = (int) PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.BOOK_TYPE_ID);
        switch (type) {
            case 1:
                findViewById(R.id.viewOldCar).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.viewWashCar).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.viewNewCar).setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick({R.id.back, R.id.submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                onBackPressed();
                break;
        }
    }
}
