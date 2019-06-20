package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodsOrderImageAdapter extends BaseAdapter {
    private Context ctx;
    private List<String> list;

    public GoodsOrderImageAdapter(Context ctx, List<String> list) {
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
        String goodInfoParams=list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_order_goodsimage, null);
        ImageView iv_img= (ImageView) convertView.findViewById(R.id.iv_comment);
        Glide.with(ctx).load(goodInfoParams).into(iv_img);
        return convertView;
    }


}
