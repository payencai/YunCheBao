package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodsType;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GridTypeAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodsType> list;

    public GridTypeAdapter(Context ctx, List<GoodsType> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_goodstype, null);
        ImageView iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_title);
        GoodsType goodMenu = list.get(position);
        if (!TextUtils.isEmpty(goodMenu.getImage())) {
            Glide.with(ctx).load(goodMenu.getImage()).into(iv_logo);
            tv_name.setText(goodMenu.getName());
        }
        return convertView;
    }


}
