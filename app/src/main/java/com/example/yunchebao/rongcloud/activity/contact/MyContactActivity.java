package com.example.yunchebao.rongcloud.activity.contact;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.adapter.MyViewPagerAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyContactActivity extends AppCompatActivity {
    ArrayList<String> titleDatas=new ArrayList<>();
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initData();
    }
    private void initData(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleDatas.add("好友列表");
        titleDatas.add("新的朋友");
        titleDatas.add("群聊");
        mTabLayout.addTab(mTabLayout.newTab().setText(titleDatas.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titleDatas.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titleDatas.get(2)));
        fragmentList.add(new FriendListFragment());
        fragmentList.add(new NewFriendFragment());
        fragmentList.add(new GroupListFragment());
        MyViewPagerAdapter myViewPageAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), titleDatas,fragmentList);
        viewpager.setAdapter(myViewPageAdapter);
        mTabLayout.setupWithViewPager(viewpager);
        mTabLayout.setTabsFromPagerAdapter(myViewPageAdapter);

    }

}
