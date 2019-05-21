package com.example.yunchebao.cheyibao.newcar;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cheyibao.NewCarCommentFragment;
import com.cheyibao.NewCarNearbyFragment;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCarShopActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_shop)
    SlidingTabLayout tabGank;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_grade)
    TextView  tv_grade;
    Shop mShop;
    int flag = 0;

    String []mTitles={"在售车型","查看评论"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_shop);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mShop = (Shop) getIntent().getSerializableExtra("data");
        flag = getIntent().getIntExtra("flag", 0);
        tv_shopname.setText(mShop.getName());
        tv_address.setText(mShop.getProvince()+mShop.getCity()+mShop.getDistrict()+mShop.getAddress());
        tv_grade.setText(mShop.getGrade()+"");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initFragmentList();
    }

    public Shop getShop() {
        return mShop;
    }

    public void setShop(Shop shop) {
        mShop = shop;
    }

    private void initFragmentList() {

        mFragments.add(new NewCarNearbyFragment());
        mFragments.add(new NewCarCommentFragment());
        tabGank.setViewPager(vpGank,mTitles,this,mFragments);

        if (flag == 2)
            vpGank.setCurrentItem(1);
    }
}
