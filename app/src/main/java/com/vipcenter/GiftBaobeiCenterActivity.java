package com.vipcenter;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.FitStateUI;
import com.tool.UIControlUtils;
import com.vipcenter.fragment.GiftBaobiCenterAllFragment;
import com.vipcenter.fragment.GiftBaobiCenterGetFragment;
import com.vipcenter.fragment.GiftBaobiCenterPayFragment;
import com.xihubao.adapter.TabFragmentAdapter;
import com.xihubao.fragment.EvaluateFragment;
import com.xihubao.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GiftBaobeiCenterActivity extends NoHttpFragmentBaseActivity {

    private TabLayout slidingTabLayout;
    //fragment列表
    private List<Fragment> mFragments = new ArrayList<>();
    //tab名的列表
    private List<String> mTitles = new ArrayList<>();
    private ViewPager viewPager;
    private TabFragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_baobi_center);
        FitStateUI.setImmersionStateMode(this);
        ButterKnife.bind(this);
        initView();

    }


    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "宝币中心");
        slidingTabLayout = (TabLayout) findViewById(R.id.slidinglayout);
        viewPager = (ViewPager) findViewById(R.id.vp);
        setViewPager();
    }

    private void setViewPager() {
        GiftBaobiCenterAllFragment allFragment = new GiftBaobiCenterAllFragment();
        GiftBaobiCenterGetFragment getFragment = new GiftBaobiCenterGetFragment();
        GiftBaobiCenterPayFragment payFragment = new GiftBaobiCenterPayFragment();
        mFragments.add(allFragment);
        mFragments.add(getFragment);
        mFragments.add(payFragment);

        mTitles.add("兑换记录");
        mTitles.add("收入");
        mTitles.add("支出");

        adapter = new TabFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
