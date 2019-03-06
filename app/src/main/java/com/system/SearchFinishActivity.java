package com.system;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.system.fragment.SearchNewFragment;
import com.system.fragment.SearchOldFragment;
import com.system.fragment.SearchRentFragment;
import com.system.fragment.SearchWashFragment;
import com.xihubao.fragment.RoadFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFinishActivity extends AppCompatActivity {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.vp_search)
    ViewPager vp_search;
    private ArrayList<Fragment> fragments;
    String [] titles={"洗修店","紧急救援","新车汇","二手车","车租赁","驾校汇","宝贝百科","宝贝圈","宝贝商城",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_finish);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        vp_search.clearDisappearingChildren();
        fragments=new ArrayList<>();
        for (int i = 0; i <9 ; i++) {
            SearchRentFragment roadFragment=new SearchRentFragment();
            fragments.add(roadFragment);
        }
        tab_layout.setViewPager(vp_search, titles,this,fragments);
    }
}
