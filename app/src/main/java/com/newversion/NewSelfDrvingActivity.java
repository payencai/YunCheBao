package com.newversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bbcircle.SelfDrivingRepublishActivity;
import com.bbcircle.fragment.DriverFragment;
import com.bbcircle.fragment.SelfDrivingFragment;
import com.example.yunchebao.R;

import butterknife.ButterKnife;

public class NewSelfDrvingActivity extends AppCompatActivity {
    SelfDrivingFragment mDriverFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_self_drving);
        ButterKnife.bind(this);
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
