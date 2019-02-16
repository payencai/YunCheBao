package com.cheyibao;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldCarShopActivity extends AppCompatActivity {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    String id;
    int flag = 0;
    MyFragmentPagerAdapter mMyFragmentPagerAdapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_car_shop);
        ButterKnife.bind(this);
        flag = getIntent().getIntExtra("flag", 0);
        id = getIntent().getStringExtra("id");
        initFragmentList();
    }

    private void initFragmentList() {
        mTitleList.add("在售车型");
        mFragments.add(new OldCarSaleFragment());
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(mMyFragmentPagerAdapter);
        tabGank.setupWithViewPager(vpGank);

    }
}
