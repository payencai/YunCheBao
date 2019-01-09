package com.vipcenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.cheyibao.fragment.StudyListFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.fragment.ArticleFragment;
import com.vipcenter.fragment.BaobiDescribFragment;
import com.vipcenter.fragment.DeductMoneyFragment;
import com.vipcenter.fragment.GoodsCollectFragment;
import com.vipcenter.fragment.HowToGetFragment;
import com.vipcenter.fragment.NewCarFragment;
import com.vipcenter.fragment.OldCarFragment;
import com.vipcenter.fragment.ShopCollectListFragment;
import com.vipcenter.fragment.WashFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class GiftRuleActivity extends NoHttpFragmentBaseActivity {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gank);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        //findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "宝币使用帮助");
        initFragmentList();
        rl_top.setVisibility(View.GONE);
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
        mTitleList.add("如何获得");
        mTitleList.add("宝币说明");
        mTitleList.add("扣减规则");
        mFragments.add(new HowToGetFragment());
        mFragments.add(new BaobiDescribFragment());
        mFragments.add(new DeductMoneyFragment());
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
