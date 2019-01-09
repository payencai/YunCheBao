package com.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chat.fragment.ConversationListFragment;
import com.chat.fragment.MessageListFragment;
import com.chat.fragment.NewsListFragment;
import com.chat.fragment.NoticeListFragment;
import com.chat.helper.Constant;
import com.example.yunchebao.R;
import com.maket.fragment.GoodCommentFragment;
import com.maket.fragment.GoodDetailFragment;
import com.maket.fragment.GoodDetailInfoFragment;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.OrderConfirmActivity;
import com.vipcenter.ShopMainListActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/6.
 * 消息列表
 */

public class MessageMainActivity extends NoHttpFragmentBaseActivity implements GoodDetailFragment.OnTabChangeListener {
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
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.backBtn).setVisibility(View.VISIBLE);
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
        findViewById(R.id.bottomMenuLay).setVisibility(View.GONE);

    }


    private void initFragmentList() {
        mTitleList.add("私信");
        mTitleList.add("通知");
        mTitleList.add("动态");
        mFragments.add(new MessageListFragment());
        mFragments.add(new NoticeListFragment());
        mFragments.add(new NewsListFragment());
    }

    @OnClick({R.id.backBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void changeTab(int position) {
        vpGank.setCurrentItem(position);
    }
}
