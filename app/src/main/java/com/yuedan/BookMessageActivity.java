package com.yuedan;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.yuedan.fragment.BookMessageFragment;
import com.yuedan.fragment.BookNeedListFragment;
import com.yuedan.fragment.BookNewCarFragment;
import com.yuedan.fragment.BookOldCarFragment;
import com.yuedan.fragment.BookWashCarFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class BookMessageActivity extends NoHttpFragmentBaseActivity {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_good_detail_main);
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
        tabGank.setTabMode(TabLayout.MODE_FIXED);
        tabGank.setupWithViewPager(vpGank);
    }

    private void initFragmentList() {
        mTitleList.add("消息");
        mTitleList.add("需求");
        mFragments.add(new BookMessageFragment());
        mFragments.add(new BookNeedListFragment());
    }

    @OnClick({R.id.backBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }
}
