package com.example.yunchebao.maket;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.example.yunchebao.R;
import com.example.yunchebao.maket.fragment.GoodCommentFragment;
import com.example.yunchebao.maket.fragment.GoodDetailFragment;
import com.example.yunchebao.maket.fragment.GoodDetailInfoFragment;
import com.example.yunchebao.maket.model.GoodList;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/6.
 * 宝贝商城 商品详情页
 */

public class GoodDetailActivity extends NoHttpFragmentBaseActivity implements GoodDetailFragment.OnTabChangeListener {
    private ArrayList<String> mTitleList = new ArrayList<>(4);
    private ArrayList<Fragment> mFragments = new ArrayList<>(4);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;

    GoodList mGoodList;

    public GoodList getGoodList() {
        return mGoodList;
    }

    public void setGoodList(GoodList goodList) {
        mGoodList = goodList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoodList= (GoodList) getIntent().getSerializableExtra("data");
        setContentView(R.layout.market_good_detail_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
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

    }


    private void initFragmentList() {
        mTitleList.add("商品");
        mTitleList.add("详情");
        mTitleList.add("评价");
        GoodDetailFragment goodDetailFragment = new GoodDetailFragment();
        goodDetailFragment.setListener(this);
        mFragments.add(goodDetailFragment);
        mFragments.add(new GoodDetailInfoFragment());
        mFragments.add(new GoodCommentFragment());
    }

    @OnClick({R.id.backBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.backBtn:
                onBackPressed();
                break;
//            case R.id.toCustomServiceBtn:
//                Intent intent = new Intent(GoodDetailActivity.this, OrderChatDetailActivity.class);
//                intent.putExtra(Constant.EXTRA_USER_ID, "哈哈");
//                startActivity(intent);
//                break;
//            case R.id.toShopDetailBtn:
//                ActivityAnimationUtils.commonTransition(GoodDetailActivity.this, ShopMainListActivity.class, ActivityConstans.Animation.FADE);
//                break;
//            case R.id.submitBtn:
//                ActivityAnimationUtils.commonTransition(GoodDetailActivity.this, OrderConfirmActivity.class, ActivityConstans.Animation.FADE);
//                break;
        }
    }

    @Override
    public void changeTab(int position) {
        vpGank.setCurrentItem(position);
    }
}
