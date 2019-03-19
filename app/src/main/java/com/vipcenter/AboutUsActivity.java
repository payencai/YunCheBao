package com.vipcenter;

import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class AboutUsActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"关于我们");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,R.id.lay1,R.id.lay2})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                break;
            case R.id.lay2:
                break;
        }
    }
}
