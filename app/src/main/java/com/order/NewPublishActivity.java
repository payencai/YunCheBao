package com.order;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPublishActivity extends AppCompatActivity {



    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[]titles={"售卖中","已完成","被拒绝"};
    @BindView(R.id.vp_pub)
    ViewPager vp_pub;
    @BindView(R.id.tab_pub)
    SlidingTabLayout tab_pub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_publish);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我发布的");
        initFragmentList();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initFragmentList() {

        mFragments.add(NewPublishFragment.newInstance(1));
        mFragments.add(NewPublishFragment.newInstance(2));
        mFragments.add(NewPublishFragment.newInstance(3));
        tab_pub.setViewPager(vp_pub,titles,this,mFragments);
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
