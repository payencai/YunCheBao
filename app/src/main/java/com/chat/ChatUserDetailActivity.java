package com.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/31.
 */

public class ChatUserDetailActivity extends NoHttpBaseActivity {
    private boolean isOpen1, isOpen2;
    @BindView(R.id.switchBtn1)
    ImageView switchBtn1;
    @BindView(R.id.switchBtn2)
    ImageView switchBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_user_detail_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "详细资料");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.switchBtn1, R.id.switchBtn2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.switchBtn1:
                if (isOpen1) {
                    isOpen1 = false;
                    switchBtn1.setImageResource(R.mipmap.switch_close);
                } else {
                    isOpen1 = true;
                    switchBtn1.setImageResource(R.mipmap.switch_open);
                }
                break;
            case R.id.switchBtn2:
                if (isOpen2) {
                    isOpen2 = false;
                    switchBtn2.setImageResource(R.mipmap.switch_close);
                } else {
                    isOpen2 = true;
                    switchBtn2.setImageResource(R.mipmap.switch_open);
                }
                break;
        }
    }
}
