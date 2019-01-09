package com.cheyibao;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.HashMap;

/**
 * 作者：凌涛 on 2018/12/26 17:48
 * 邮箱：771548229@qq..com
 */
public class PageAdapter extends FragmentPagerAdapter {

    private int num;
    private HashMap<Integer, Fragment> mFragmentHashMap = new HashMap<>();

    public PageAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int position) {

        return createFragment(position);
    }

    @Override
    public int getCount() {
        return num;
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = mFragmentHashMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = new NewCarNearbyFragment();
                    Log.i("fragment", "fragment1");
                    break;
                case 1:
                    fragment = new NewCarCommentFragment();
                    Log.i("fragment", "fragment2");
                    break;

            }
            mFragmentHashMap.put(pos, fragment);
        }
        return fragment;
    }
}

