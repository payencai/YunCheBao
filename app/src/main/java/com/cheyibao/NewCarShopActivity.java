package com.cheyibao;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cheyibao.fragment.NewCarFragment;
import com.cheyibao.fragment.OldCarFragment;
import com.cheyibao.fragment.RentCarFragment;
import com.cheyibao.fragment.StudyCarFragment;
import com.example.yunchebao.R;
import com.tool.adapter.MyFragmentPagerAdapter;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_shop);
        ButterKnife.bind(this);
        tabGank.addTab(tabGank.newTab().setText("在售车型"));
        tabGank.addTab(tabGank.newTab().setText("查看评论"));

        vpGank.setAdapter(new PageAdapter(getSupportFragmentManager(), tabGank.getTabCount()));
        // 左右预加载页面的个数

       // myAdapter.notifyDataSetChanged();

       // tabGank.setupWithViewPager(vpGank);
        tabGank.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpGank.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void initFragmentList() {
        mTitleList.add("在售车型");
        mTitleList.add("查看评论");
        mFragments.add(new NewCarNearbyFragment());
        mFragments.add(new NewCarCommentFragment());

    }
}
