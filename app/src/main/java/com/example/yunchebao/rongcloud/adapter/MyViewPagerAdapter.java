package com.example.yunchebao.rongcloud.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 作者：凌涛 on 2018/12/18 12:02
 * 邮箱：771548229@qq..com
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> titleList;
    private ArrayList<Fragment> fragmentList;
    public MyViewPagerAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.titleList = titleList;
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }


}
