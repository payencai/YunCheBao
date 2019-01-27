package com.bbcircle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bbcircle.fragment.DriverFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.example.yunchebao.R;
import com.newversion.NewCarFriendActivity;

import butterknife.BindView;

public class NewDrvingActivity extends AppCompatActivity {
    StudyCarFragment mStudyCarFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_drving);
        mStudyCarFragment=new StudyCarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fr_content,mStudyCarFragment).commit();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
