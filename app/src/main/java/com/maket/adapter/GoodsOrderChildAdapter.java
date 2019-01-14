package com.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;

import java.util.List;

import retrofit2.http.POST;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodsOrderChildAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneOrderEntity.ItemListBean> list;

    public GoodsOrderChildAdapter(Context ctx, List<PhoneOrderEntity.ItemListBean> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_order_child, null);
        ImageView iv_goods= (ImageView) convertView.findViewById(R.id.iv_goods);
        TextView tv_goodsname= (TextView) convertView.findViewById(R.id.tv_goodsname);
        TextView tv_num= (TextView) convertView.findViewById(R.id.tv_num);
        TextView tv_price= (TextView) convertView.findViewById(R.id.tv_price);
        Glide.with(ctx).load(list.get(position).getCommodityImage()).into(iv_goods);
        tv_goodsname.setText(list.get(position).getCommodityName());
        tv_num.setText("x"+list.get(position).getNumber());
        tv_price.setText("￥"+list.get(position).getPrice());
        return convertView;
    }


}
