package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.ShopComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class RentCarImageAdapter extends BaseAdapter {
    private List<String> mClassItems;
    private Context mContext;

    public RentCarImageAdapter(Context context, List<String> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<String> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<String> classItems) {
        mClassItems = classItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        //return mClassItems.size();
        return mClassItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rentcar_image, null);
        ImageView userhead= (ImageView) convertView.findViewById(R.id.iv_logo);
        Glide.with(mContext).load(mClassItems.get(position)).into(userhead);
        return convertView;
    }
}
