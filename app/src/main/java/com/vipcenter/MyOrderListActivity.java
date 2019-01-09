package com.vipcenter;

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
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
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

public class MyOrderListActivity extends NoHttpFragmentBaseActivity implements IndicatorViewPager.OnIndicatorPageChangeListener {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    private ArrayList<PhoneCommBaseType> menuList = new ArrayList<PhoneCommBaseType>();
    @BindView(R.id.moretab_indicator)
    ScrollIndicatorView scrollIndicatorView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private HashMap<String, Fragment> fragmentList = new HashMap<String, Fragment>();
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_vp_list);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "订单");
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        Resources res = getResources();
        initMenu();
        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.15f;
        int selectColor = res.getColor(R.color.black_33);
        int unSelectColor = res.getColor(R.color.gray_99);
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(unSelectSize, unSelectSize));
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);
        indicatorViewPager.setOnIndicatorPageChangeListener(this);
        inflate = LayoutInflater.from(ctx);
        // 注意这里 的FragmentManager 是 getChildFragmentManager(); 因为是在Fragment里面
        // 而在activity里面用FragmentManager 是 getSupportFragmentManager()
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initMenu() {
        menuList.add(new PhoneCommBaseType(0, "全部"));
        menuList.add(new PhoneCommBaseType(1, "待付款"));
        menuList.add(new PhoneCommBaseType(3, "待收货"));
        menuList.add(new PhoneCommBaseType(2, "待发货"));
        menuList.add(new PhoneCommBaseType(4, "待评价"));
    }

    @Override
    public void onIndicatorPageChange(int preItem, int currentItem) {

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return menuList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflate.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            StringBuilder sb = new StringBuilder();
            sb.append(menuList.get(position).getTypeName());
            if (menuList.get(position).getTypeDes() != null && !menuList.get(position).getTypeDes().equals("")) {
                sb.append("\n").append(menuList.get(position).getTypeDes());
            }
            textView.setText(sb.toString());
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {

            OrderListFragment mainFragment;
            if (fragmentList.get(menuList.get(position).getTypeName()) != null) {
                mainFragment = (OrderListFragment) fragmentList.get(menuList.get(position).getTypeName());
            } else {
                mainFragment = new OrderListFragment();
                mainFragment.setPosition(menuList.get(position).getTypeId());
                fragmentList.put(menuList.get(position).getTypeName(), mainFragment);
            }
            return mainFragment;
        }
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
