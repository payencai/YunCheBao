package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.RentGoods;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class RentGoodsAdapter extends BaseAdapter {
    private Context ctx;
    private List<RentGoods> list;

    public RentGoodsAdapter(Context ctx, List<RentGoods> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_grid_rent, null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        Glide.with(ctx).load(list.get(position).getLogo()).into(iv_logo);
        return convertView;
    }


}
