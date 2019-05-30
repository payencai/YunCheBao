package com.system.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.cheyibao.newcar.NewCarFragment;
import com.example.yunchebao.cheyibao.oldcar.OldCarFragment;
import com.example.yunchebao.cheyibao.rentcar.RentCarFragment;
import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;
import com.system.SearchActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.NoScrollViewPager;
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
    NoScrollViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.messenger_icon)
    TextView messenger_icon;
    @BindView(R.id.user_center_icon)
    ImageView user_center_icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cheyibao_new, container, false);
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
        rootView.findViewById(R.id.search_lay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        initLocation();
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
        vpGank.setScroll(false);
        return rootView;
    }
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.e("locate", aMapLocation.getAddress());
            messenger_icon.setText(aMapLocation.getCity());
        }
    };

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);

        if (null != mLocationOption) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();

        }
        mLocationClient.startLocation();
        // Log.e("locate", mLocationClient.getVersion() + "gfg");
    }


    private void initFragmentList() {
        mTitleList.add("新车汇");
        mTitleList.add("二手车");
        mTitleList.add("车租赁");

        mFragments.add(new NewCarFragment());
        mFragments.add(new OldCarFragment());
        mFragments.add(new RentCarFragment());

    }
}
