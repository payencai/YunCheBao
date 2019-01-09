package com.xihubao.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/30 15:56
 * 邮箱：771548229@qq..com
 */
public class MapPagerAdapter  extends PagerAdapter {
    Context context;
    List<View> mViews;
    @Override
    public int getCount() {
        return mViews.size();
    }

    public MapPagerAdapter(Context context, List<View> views) {
        this.context = context;
        mViews = views;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view=mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view=(View)object;
        container.removeView(view);
    }



}
