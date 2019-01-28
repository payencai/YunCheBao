package com.order;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.fragment.WashHasCommentListFragment;
import com.vipcenter.fragment.WashWaitCommentListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceCarActivity extends AppCompatActivity {
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_car);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "服务/车辆");
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        vpGank.setOffscreenPageLimit(2);
        //myAdapter.notifyDataSetChanged();
        tabGank.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabGank.setupWithViewPager(vpGank);

    }


    private void initFragmentList() {
        mTitleList.add("全部");
        mTitleList.add("服务中");
        mTitleList.add("待评价");
        mFragments.add(CarOrderFragment.newInstance(0));
        mFragments.add(CarOrderFragment.newInstance(2));
        mFragments.add(CarOrderFragment.newInstance(3));
    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
