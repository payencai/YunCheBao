package com.xihubao;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.xihubao.fragment.RoadFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoadActivity extends AppCompatActivity {
    private FragmentManager fm;
    RoadFragment mRoadFragment;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roat);
        ButterKnife.bind(this);
        mRoadFragment=new RoadFragment();
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fr_content, mRoadFragment).commit();
        tv_city.setText(MyApplication.getaMapLocation().getCity());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
