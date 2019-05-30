package com.example.yunchebao.collect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bbcircle.fragment.WashCollectFragment;
import com.cheyibao.fragment.StudyListFragment;
import com.example.yunchebao.R;
import com.example.yunchebao.collect.fragment.CollecImageFragment;
import com.example.yunchebao.collect.fragment.CollectDriveFragment;
import com.example.yunchebao.collect.fragment.CollectGasstationFragment;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.vipcenter.fragment.ArticleFragment;
import com.vipcenter.fragment.GoodsCollectFragment;
import com.vipcenter.fragment.NewCarFragment;
import com.vipcenter.fragment.OldCarFragment;
import com.example.yunchebao.collect.fragment.ShopCollectListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class AllCollectionActivity extends NoHttpFragmentBaseActivity  {

    private ArrayList<Fragment> mFragments ;
    @BindView(R.id.vp_collect)
    ViewPager vpGank;
    @BindView(R.id.tab_collect)
    SlidingTabLayout tabGank;
    String []mTitles={"洗车/修理","新车汇","二手车","帖子","商品","驾校汇","店铺","加油站","代驾","图片"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_collect);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }
    private void initView() {
        initFragmentList();
    }

    private void initFragmentList() {
        mFragments=new ArrayList<>();
        mFragments.add(new WashCollectFragment());
        mFragments.add(new NewCarFragment());
        mFragments.add(new OldCarFragment());
        mFragments.add(new ArticleFragment());
        mFragments.add(new GoodsCollectFragment());
        mFragments.add(new StudyListFragment());
        mFragments.add(new ShopCollectListFragment());
        mFragments.add(new CollectGasstationFragment());
        mFragments.add(new CollectDriveFragment());
        mFragments.add(new CollecImageFragment());
        tabGank.setViewPager(vpGank,mTitles,this,mFragments);
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
