package com.baike.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/28 15:29
 * 邮箱：771548229@qq..com
 */
public class ClassifyWikiAdapter extends FragmentStatePagerAdapter {
    List<String> mTitles;
    List<Fragment>mFragments;
    public ClassifyWikiAdapter(FragmentManager fm,List<String> mTitles,List<Fragment>mFragments) {
        super(fm);
        this.mFragments=mFragments;
        this.mTitles=mTitles;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
