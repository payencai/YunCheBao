package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.tool.listview.PersonalListView;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodsOrderDetailAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneOrderEntity> list;
    GoodsOrderChildAdapter mGoodsOrderChildAdapter;
    public GoodsOrderDetailAdapter(Context ctx, List<PhoneOrderEntity> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_order_info, null);
        TextView shopName= (TextView) convertView.findViewById(R.id.shopName);
        mGoodsOrderChildAdapter=new GoodsOrderChildAdapter(ctx,list.get(position).getItemList());
        PersonalListView lv_child= (PersonalListView) convertView.findViewById(R.id.lv_child);
        lv_child.setAdapter(mGoodsOrderChildAdapter);
        shopName.setText(list.get(position).getShopName());
        return convertView;
    }


}
