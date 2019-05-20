package com.example.yunchebao.driverschool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;

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
