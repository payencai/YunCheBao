package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodList;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class KnowYouAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodList> list;

    public KnowYouAdapter(Context ctx, List<GoodList> list) {
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_know_you, null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_newprice= (TextView) convertView.findViewById(R.id.tv_newprice);
        TextView tv_oldprice= (TextView) convertView.findViewById(R.id.tv_oldprice);
        if(!list.get(position).getCommodityImage().contains(","))
        Glide.with(ctx).load(list.get(position).getCommodityImage()).into(iv_logo);
        else{
            Glide.with(ctx).load(list.get(position).getCommodityImage().split(",")[0]).into(iv_logo);
        }

        tv_name.setText(list.get(position).getName());
        tv_newprice.setText("￥"+list.get(position).getDiscountPrice());
        tv_oldprice.setText("￥"+list.get(position).getOriginalPrice());
        tv_oldprice.setPaintFlags(tv_oldprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        return convertView;
    }


}
