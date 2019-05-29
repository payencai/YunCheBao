package com.example.yunchebao.yuedan;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.example.yunchebao.yuedan.fragment.OrderItemFragment;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyYuedanActivity extends AppCompatActivity {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.vp_content)
    ViewPager viewpager;
    @BindView(R.id.back)
    ImageView back;
    private ArrayList<Fragment> fragments;
    private String [] mTitles={"未完成","已付款"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yuedan);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        fragments=new ArrayList<>();
        fragments.add(OrderItemFragment.newInstance(1));
        fragments.add(OrderItemFragment.newInstance(2));
        tab_layout.setViewPager(viewpager,  mTitles,this,fragments);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
