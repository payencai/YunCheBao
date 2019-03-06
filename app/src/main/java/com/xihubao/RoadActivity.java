package com.xihubao;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.yunchebao.R;
import com.xihubao.fragment.RoadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoadActivity extends AppCompatActivity {
    private FragmentManager fm;
    RoadFragment mRoadFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roat);
        ButterKnife.bind(this);
        mRoadFragment=new RoadFragment();
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fr_content, mRoadFragment).commit();
    }
}
