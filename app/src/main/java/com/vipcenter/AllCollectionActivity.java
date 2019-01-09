package com.vipcenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bbcircle.fragment.WashCollectFragment;
import com.cheyibao.fragment.RentCarFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.cheyibao.fragment.StudyListFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.vipcenter.fragment.ArticleFragment;
import com.vipcenter.fragment.GoodsCollectFragment;
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

public class AllCollectionActivity extends NoHttpFragmentBaseActivity  {
    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_collect);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

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
        mTitleList.add("洗护宝");
       // mTitleList.add("二手车");
        mTitleList.add("帖子");
//        mTitleList.add("商品");
//        mTitleList.add("驾校汇");
//        mTitleList.add("新车整车");
//        mTitleList.add("店铺");
        mFragments.add(new WashCollectFragment());
       // mFragments.add(new OldCarFragment());
        mFragments.add(new ArticleFragment());
//        mFragments.add(new GoodsCollectFragment());
//        mFragments.add(new StudyListFragment());
//        mFragments.add(new NewCarFragment());
//        mFragments.add(new ShopCollectListFragment());
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
