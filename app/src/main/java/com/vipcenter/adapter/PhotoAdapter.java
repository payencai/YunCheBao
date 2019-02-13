package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/13 12:21
 * 邮箱：771548229@qq..com
 */
public class PhotoAdapter extends BaseAdapter {
    Context mContext;
    List<String> path;

    public PhotoAdapter(Context context, List<String> path) {
        mContext = context;
        this.path = path;
    }

    @Override
    public int getCount() {
        return path.size();
    }

    @Override
    public Object getItem(int position) {
        return path.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_gv_photo,null);
        ImageView iv_photo= (ImageView) convertView.findViewById(R.id.iv_photo);
        Glide.with(mContext).load(path.get(position)).into(iv_photo);
        return convertView;
    }
}
