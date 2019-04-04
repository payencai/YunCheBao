package com.system;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.tool.NoScrollViewPager;
import com.vipcenter.fragment.OrderListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriveServiceActivity extends AppCompatActivity {


    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout scrollIndicatorView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ArrayList<Fragment> mFragments;
    String []mTitles={"全部","待付款"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_service);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments=new ArrayList<>();
        for (int i = 0; i <2 ; i++) {
            OrderListFragment orderListFragment=OrderListFragment.newInstance(i);
            mFragments.add(orderListFragment);
        }
        scrollIndicatorView.setViewPager(viewPager,mTitles,this,mFragments);
    }


}
