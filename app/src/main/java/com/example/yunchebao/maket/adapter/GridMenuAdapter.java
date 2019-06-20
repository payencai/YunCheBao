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
import com.example.yunchebao.maket.model.GoodMenu;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GridMenuAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodMenu> list;

    public GridMenuAdapter(Context ctx, List<GoodMenu> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_grid_menu, null);
        ImageView iv_logo = (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        GoodMenu goodMenu = list.get(position);
        if (!TextUtils.isEmpty(goodMenu.getLogo())) {
            Glide.with(ctx).load(goodMenu.getLogo()).into(iv_logo);
            tv_name.setText(goodMenu.getName());
        }
        return convertView;
    }


}
