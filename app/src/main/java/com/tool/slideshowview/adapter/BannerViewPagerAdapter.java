package com.tool.slideshowview.adapter;

/**
 * Created by pengying on 2017/1/10.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.yunchebao.R;
import com.tool.slideshowview.Banner;

import java.util.ArrayList;
import java.util.List;

public class BannerViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<View> viewLists;
    private List<Banner> banners;

    public BannerViewPagerAdapter(Context context, List<Banner> banners) {
        super();
        this.context = context;
        this.banners = banners;
        initViewList();
    }

    private void initViewList(){
        viewLists = new ArrayList<>();
        for (int i = 0;i < banners.size();i ++){
            View view = LayoutInflater.from(context).inflate(R.layout.item_view_pager, null);
            ImageView imageView = (ImageView)view.findViewById(R.id.view_page_image);
            imageView.setImageResource(banners.get(i).getImageUrl());
            viewLists.add(view);
        }
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}