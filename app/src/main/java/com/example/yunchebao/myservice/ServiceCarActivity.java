package com.example.yunchebao.myservice;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.order.CarOrderFragment;
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

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.vp_gank)
    ViewPager vp_gank;
    @BindView(R.id.tab_service)
    SlidingTabLayout tabGank;
    String []mTitles={"全部","服务中","待评价"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_car);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我的服务");
        initFragmentList();

    }


    private void initFragmentList() {
        mFragments.add(CarOrderFragment.newInstance(0));
        mFragments.add(CarOrderFragment.newInstance(1));
        mFragments.add(CarOrderFragment.newInstance(2));
        tabGank.setViewPager(vp_gank,mTitles,this,mFragments);
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
