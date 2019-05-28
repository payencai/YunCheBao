package com.newversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.babycircle.carfriend.DriverFriendsRepublishActivity;
import com.bbcircle.fragment.DriverFragment;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;

public class NewCarFriendActivity extends AppCompatActivity {
    DriverFragment mDriverFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_friend);
        ButterKnife.bind(this);
        mDriverFragment=new DriverFragment();
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mDriverFragment).commit();
        findViewById(R.id.tv_public).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewCarFriendActivity.this, DriverFriendsRepublishActivity.class));
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
