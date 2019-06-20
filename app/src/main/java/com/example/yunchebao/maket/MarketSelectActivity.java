package com.example.yunchebao.maket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class MarketSelectActivity extends NoHttpBaseActivity {

    private Context ctx;

    @BindView(R.id.et_min)
    EditText et_min;
    @BindView(R.id.et_max)
    EditText et_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_select_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "筛选");
        ButterKnife.bind(this);
        ctx = this;

    }




    @OnClick({R.id.back, R.id.submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                String min=et_min.getEditableText().toString();
                String max=et_max.getEditableText().toString();
                Intent intent =new Intent();
                intent.putExtra("min",min);
                intent.putExtra("max",max);
                setResult(1,intent);
                finish();
                //onBackPressed();
                break;
        }
    }
}
