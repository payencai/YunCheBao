package com.example.yunchebao.babycircle.selfdrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bbcircle.fragment.SelfDrivingFragment;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;

public class NewSelfDrvingActivity extends AppCompatActivity {
    SelfDrivingFragment mDriverFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_self_drving);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mDriverFragment=new SelfDrivingFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mDriverFragment).commit();
        findViewById(R.id.tv_public).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewSelfDrvingActivity.this, SelfDrivingRepublishActivity.class));
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
