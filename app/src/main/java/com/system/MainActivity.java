package com.system;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.application.MyApplication;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.system.fragment.BBCircleFragment;
import com.system.fragment.CheyiFragment;
import com.system.fragment.HomeFragment;
import com.system.fragment.BaikeFragment;
import com.system.fragment.MallFragment;
import com.system.fragment.NewBabyFragment;
import com.system.fragment.NewBaikeFragment;
import com.tool.FitStateUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NoHttpFragmentBaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    HomeFragment fragment1;
    CheyiFragment fragment2;
    NewBabyFragment fragment3;
    NewBaikeFragment fragment4;
    MallFragment fragment5;


    private FragmentManager fm;
    private List<Fragment> fragments;

    View tab1, tab2, tab3, tab4, tab5;
    private ImageView iv_tab1;
    private ImageView iv_tab2;
    private ImageView iv_tab3;
    private ImageView iv_tab4;
    private ImageView iv_tab5;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_tab3;
    private TextView tv_tab4;
    private TextView tv_tab5;


    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.e("locate", aMapLocation.getAddress());
            MyApplication.setaMapLocation(aMapLocation);
        }
    };

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);

        if (null != mLocationOption) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.stopLocation();

        }
        mLocationClient.startLocation();
        Log.e("locate", mLocationClient.getVersion() + "gfg");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_main);
        initLocation();
        initview();
        initListener();
    }


    //关闭时解除监听器
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    private void initview() {
        tab1 = findViewById(R.id.main_fl_1);
        tab2 = findViewById(R.id.main_fl_2);
        tab3 = findViewById(R.id.main_fl_3);
        tab4 = findViewById(R.id.main_fl_4);
        tab5 = findViewById(R.id.main_fl_5);

        iv_tab1 = (ImageView) findViewById(R.id.main_iv_1);
        iv_tab2 = (ImageView) findViewById(R.id.main_iv_2);
        iv_tab3 = (ImageView) findViewById(R.id.main_iv_3);
        iv_tab4 = (ImageView) findViewById(R.id.main_iv_4);
        iv_tab5 = (ImageView) findViewById(R.id.main_iv_5);
        tv_tab1 = (TextView) findViewById(R.id.main_tv_1);
        tv_tab2 = (TextView) findViewById(R.id.main_tv_2);
        tv_tab3 = (TextView) findViewById(R.id.main_tv_3);
        tv_tab4 = (TextView) findViewById(R.id.main_tv_4);
        tv_tab5 = (TextView) findViewById(R.id.main_tv_5);

        fm = getSupportFragmentManager();
        fragment1 = new HomeFragment();
        fragment2 = new CheyiFragment();
        fragment3 = new NewBabyFragment();
        fragment4 = new NewBaikeFragment();
        fragment5 = new MallFragment();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        for (Fragment fragment : fragments) {
            fm.beginTransaction().add(R.id.main_frame, fragment).commit();
        }
        //显示主页
        resetStateForTagbar(R.id.main_fl_1);
        hideAllFragment();
        showFragment(0);
    }

    private void initListener() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
    }


    private void resetStateForTagbar(int viewId) {
        clearTagbarState();
        if (viewId == R.id.main_fl_1) {
            tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab1.setImageResource(R.mipmap.home_icon_1y);
            return;
        }
        if (viewId == R.id.main_fl_2) {
            tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab2.setImageResource(R.mipmap.home_icon_2y);
            return;
        }
        if (viewId == R.id.main_fl_3) {
            tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab3.setImageResource(R.mipmap.home_icon_3y);
            return;
        }
        if (viewId == R.id.main_fl_4) {
            tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab4.setImageResource(R.mipmap.home_icon_4y);
            return;
        }
        if (viewId == R.id.main_fl_5) {
            tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab5.setImageResource(R.mipmap.home_icon_5y);
            return;
        }
    }

    private void clearTagbarState() {
        tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab1.setImageResource(R.mipmap.home_icon_1);
        tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab2.setImageResource(R.mipmap.home_icon_2);
        tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab3.setImageResource(R.mipmap.home_icon_3);
        tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab4.setImageResource(R.mipmap.home_icon_4);
        tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab5.setImageResource(R.mipmap.home_icon_5);
    }

    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commit();
        }
    }

    private void showFragment(int position) {
        fm.beginTransaction().show(fragments.get(position)).commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_fl_1:
                //状态重置
                resetStateForTagbar(R.id.main_fl_1);
                hideAllFragment();
                showFragment(0);
                break;

            case R.id.main_fl_2:
                //状态重置
                resetStateForTagbar(R.id.main_fl_2);
                hideAllFragment();
                showFragment(1);
                break;
            case R.id.main_fl_3:
                //状态重置
                resetStateForTagbar(R.id.main_fl_3);
                hideAllFragment();
                showFragment(2);
                if(fragment3!=null)
                   fragment3.setUri();

                break;
            case R.id.main_fl_4:
                //状态重置
                resetStateForTagbar(R.id.main_fl_4);
                hideAllFragment();
                showFragment(3);
                break;
            case R.id.main_fl_5:
                //状态重置
                resetStateForTagbar(R.id.main_fl_5);
                hideAllFragment();
                showFragment(4);
                //fragment5.onResume();
                break;

            default:
                break;
        }
    }


}
