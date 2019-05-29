package com.example.yunchebao.cheyibao.oldcar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cheyibao.fragment.SellCarHandbookFragment;
import com.cheyibao.fragment.SellPublishFragment;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/25.
 * 我要卖车
 */

public class SellCarActivity extends NoHttpFragmentBaseActivity {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;

    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_car);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"我要卖车");
       // findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        ctx = this;
        ButterKnife.bind(this);
        initFragmentList();
    }

    private void initFragmentList() {
        mTitleList.add("发布车源");
        mTitleList.add("车辆指南");
        mFragments.add(new SellPublishFragment());
        mFragments.add(new SellCarHandbookFragment());

        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        vpGank.setOffscreenPageLimit(2);
        myAdapter.notifyDataSetChanged();
        tabGank.setTabMode(TabLayout.MODE_FIXED);
        tabGank.setupWithViewPager(vpGank);
    }

    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
