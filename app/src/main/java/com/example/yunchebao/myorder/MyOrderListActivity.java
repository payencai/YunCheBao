package com.example.yunchebao.myorder;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entity.PhoneCommBaseType;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.NoScrollViewPager;
import com.tool.UIControlUtils;
import com.tool.viewpager.IndicatorViewPager;
import com.tool.viewpager.OnTransitionTextListener;
import com.tool.viewpager.ScrollIndicatorView;
import com.vipcenter.fragment.OrderListFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class MyOrderListActivity extends NoHttpFragmentBaseActivity {

    @BindView(R.id.tab_order)
    SlidingTabLayout scrollIndicatorView;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    ArrayList<Fragment> mFragments;
    String []mTitles={"全部","待付款","待发货","待收货","待评价","退款/售后"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_vp_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments=new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            OrderListFragment orderListFragment=OrderListFragment.newInstance(i);
            mFragments.add(orderListFragment);
        }
        scrollIndicatorView.setViewPager(viewPager,mTitles,this,mFragments);
    }



    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
