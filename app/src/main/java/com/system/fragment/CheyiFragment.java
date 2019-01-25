package com.system.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.application.MyApplication;
import com.cheyibao.fragment.NewCarFragment;
import com.cheyibao.fragment.OldCarFragment;
import com.cheyibao.fragment.RentCarFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2017/12/9.
 */

public class CheyiFragment extends BaseFragment {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.user_center_icon)
    ImageView user_center_icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, rootView);
//        requestMethod(0);
        user_center_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin)
                    ActivityAnimationUtils.commonTransition(getActivity(), UserCenterActivity.class, ActivityConstans.Animation.FADE);
                else{
                    startActivity(new Intent(getContext(),RegisterActivity.class));
                }

            }
        });
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(myAdapter);
        // 左右预加载页面的个数
        vpGank.setOffscreenPageLimit(1);
        myAdapter.notifyDataSetChanged();
        tabGank.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabGank.setupWithViewPager(vpGank);
        vpGank.setCurrentItem(0);
        return rootView;
    }


    private void initFragmentList() {
        mTitleList.add("新车汇");
        mTitleList.add("二手车");
        mTitleList.add("车租赁");
        mTitleList.add("驾校汇");
        mFragments.add(new NewCarFragment());
        mFragments.add(new OldCarFragment());
        mFragments.add(new RentCarFragment());
        mFragments.add(new StudyCarFragment());
    }
}
