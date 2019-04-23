package com.yuedan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/25 16:31
 * 邮箱：771548229@qq..com
 */
public class ImageAdapter extends BaseAdapter {
    Context mContext;
    List<String> images;

    public ImageAdapter(Context context, List<String> images) {
        mContext = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_grid_images,null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        ImageView iv_del=convertView.findViewById(R.id.iv_del);
        if(position==0){
            iv_del.setVisibility(View.GONE);
            iv_logo.setImageResource(R.mipmap.camera_default);
        }else{
            iv_del.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(images.get(position)).into(iv_logo);
        }

        return convertView;
    }
}
