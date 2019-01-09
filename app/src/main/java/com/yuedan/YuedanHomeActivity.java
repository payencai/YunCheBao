package com.yuedan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.cheyibao.fragment.NewCarFragment;
import com.cheyibao.fragment.OldCarFragment;
import com.cheyibao.fragment.RentCarFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.yuedan.fragment.BookNewCarFragment;
import com.yuedan.fragment.BookOldCarFragment;
import com.yuedan.fragment.BookRoadFragment;
import com.yuedan.fragment.BookWashCarFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class YuedanHomeActivity extends NoHttpFragmentBaseActivity {
    private ArrayList<String> mTitleList = new ArrayList<>(5);
    private ArrayList<Fragment> mFragments = new ArrayList<>(5);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuedan_home_layout);
        ButterKnife.bind(this);
//        requestMethod(0);
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        vpGank.setOffscreenPageLimit(1);
        myAdapter.notifyDataSetChanged();
        tabGank.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabGank.setupWithViewPager(vpGank);
    }

    private void initFragmentList() {
        mTitleList.add("洗车店");
        mTitleList.add("修理店");
        mTitleList.add("新车整车");
        mTitleList.add("二手车");
        mTitleList.add("道路救援");
        mFragments.add(BookWashCarFragment.newInstance(1));
        mFragments.add(BookWashCarFragment.newInstance(2));
        mFragments.add(new BookNewCarFragment());
        mFragments.add(new BookOldCarFragment());
        mFragments.add(new BookRoadFragment());
    }

    @OnClick({R.id.back, R.id.rightBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.rightBtn:
                ActivityAnimationUtils.commonTransition(YuedanHomeActivity.this, BookMessageActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
