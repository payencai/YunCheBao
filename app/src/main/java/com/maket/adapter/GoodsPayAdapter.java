package com.maket.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.maket.model.GoodsType;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodsPayAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneGoodEntity> list;

    public GoodsPayAdapter(Context ctx, List<PhoneGoodEntity> list) {
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
        PhoneGoodEntity phoneGoodEntity=list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_shopcar_select, null);
        RelativeLayout rl_top = (RelativeLayout) convertView.findViewById(R.id.rl_top);
        LinearLayout ll_bottom = (LinearLayout) convertView.findViewById(R.id.ll_bottom);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        View line=(View) convertView.findViewById(R.id.line);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_shop= (TextView) convertView.findViewById(R.id.tv_shop);

        TextView tv_param= (TextView) convertView.findViewById(R.id.tv_param);
        TextView tv_count= (TextView) convertView.findViewById(R.id.tv_count);
        TextView tv_newprice= (TextView) convertView.findViewById(R.id.tv_newprice);
        TextView tv_oldprice= (TextView) convertView.findViewById(R.id.tv_oldprice);
        TextView tv_total= (TextView) convertView.findViewById(R.id.tv_total);
        Glide.with(ctx).load(phoneGoodEntity.getDefaultPic()).into(iv_logo);
        tv_name.setText(phoneGoodEntity.getProductName());
        tv_param.setText(phoneGoodEntity.getFirstSpecificationValue()+"*"+phoneGoodEntity.getSecondSpecificationValue());
        tv_count.setText("x"+phoneGoodEntity.getCount());
        tv_newprice.setText("￥"+phoneGoodEntity.getPrice());
        tv_oldprice.setText("￥"+phoneGoodEntity.getOriginalPrice());
        tv_shop.setText(phoneGoodEntity.getShopName());
        switch (list.get(position).getFlag()){
            case 1:
                rl_top.setVisibility(View.VISIBLE);
                break;
            case 2:
                ll_bottom.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                tv_total.setText("￥"+phoneGoodEntity.getTotalPrice());
               // line.setVisibility(View.VISIBLE);
                break;
            case 3:
                rl_top.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.VISIBLE);
                line.setVisibility(View.VISIBLE);
                tv_total.setText("￥"+phoneGoodEntity.getTotalPrice());
                break;
        }

        return convertView;
    }


}
