package com.rongcloud.activity.stranger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yunchebao.R;

public class StrangerMsgActivity extends AppCompatActivity {
    StrangerFragment mStrangerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stranger_msg);
        mStrangerFragment=StrangerFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fr_content, mStrangerFragment).commit();
    }
}
