package com.system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.system.model.HomeImage;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/25 10:36
 * 邮箱：771548229@qq..com
 */
public class HomeListAdapter extends BaseAdapter{
    Context mContext;
    List<HomeImage> mList;
    @Override
    public int getCount() {
        return mList.size();
    }

    public HomeListAdapter(Context context, List<HomeImage> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_home,null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        Glide.with(mContext).load(mList.get(position).getImage()).into(iv_logo);
        return convertView;
    }
}
