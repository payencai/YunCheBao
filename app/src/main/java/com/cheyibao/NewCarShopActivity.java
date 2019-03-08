package com.cheyibao;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cheyibao.fragment.NewCarFragment;
import com.cheyibao.fragment.OldCarFragment;
import com.cheyibao.fragment.RentCarFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCarShopActivity extends AppCompatActivity {
    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_grade)
    TextView  tv_grade;
    Shop mShop;
    int flag = 0;
    MyFragmentPagerAdapter mMyFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_shop);
        ButterKnife.bind(this);
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
        mTitleList.add("在售车型");
        mTitleList.add("查看评论");
        mFragments.add(new NewCarNearbyFragment());
        mFragments.add(new NewCarCommentFragment());
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(mMyFragmentPagerAdapter);
        vpGank.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabGank.setupWithViewPager(vpGank);
        if (flag == 2)
            vpGank.setCurrentItem(1);
    }
}
