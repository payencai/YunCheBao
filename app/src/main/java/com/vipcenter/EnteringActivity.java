package com.vipcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yunchebao.R;
import com.nohttp.rest.Request;

import butterknife.ButterKnife;

/**
 * 入驻申请
 */
public class EnteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entering);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }
}
