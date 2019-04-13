package com.drive.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drive.model.DriveMan;
import com.example.yunchebao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddSubstitubeActivity extends AppCompatActivity {
    DriveMan mDriveMan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_substitube);
        ButterKnife.bind(this);
        mDriveMan= (DriveMan) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {

    }
}
