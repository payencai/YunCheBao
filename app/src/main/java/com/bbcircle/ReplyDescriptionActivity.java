package com.bbcircle;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.SimpleCommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by sdhcjhss on 2018/1/12.
 */

public class ReplyDescriptionActivity extends NoHttpBaseActivity {

    private Context ctx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_description_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;

    }






    @OnClick({R.id.back, R.id.faceBtn, R.id.text1})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.faceBtn:

                break;
            case R.id.text1:
                onBackPressed();
                break;
        }
    }
}
