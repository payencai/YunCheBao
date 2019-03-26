package com.vipcenter.adapter;


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

/**
 * 作者：凌涛 on 2019/3/26 10:27
 * 邮箱：771548229@qq..com
 */
public class RebackMoneyAdapter extends BaseAdapter {
    Context mContext;
    List<PhoneOrderEntity.ItemListBean> mItemListBeans;

    public RebackMoneyAdapter(Context context, List<PhoneOrderEntity.ItemListBean> itemListBeans) {
        mContext = context;
        mItemListBeans = itemListBeans;
    }

    @Override
    public int getCount() {
        return mItemListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mItemListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_reback_money,null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_money= (TextView) convertView.findViewById(R.id.tv_money);
        TextView tv_cause= (TextView) convertView.findViewById(R.id.tv_cause);
        TextView tv_refusemoney= (TextView) convertView.findViewById(R.id.tv_refusemoney);
        TextView tv_refusetime= (TextView) convertView.findViewById(R.id.tv_refusetime);
        Glide.with(mContext).load(mItemListBeans.get(position).getCommodityImage()).into(iv_logo);
        tv_name.setText(mItemListBeans.get(position).getCommodityName());
        tv_money.setText("￥"+mItemListBeans.get(position).getRefusePrice());
        tv_cause.setText("退款原因："+mItemListBeans.get(position).getRefuseCause());
        tv_refusemoney.setText("退款金额：￥"+mItemListBeans.get(position).getRefusePrice());
        tv_refusetime.setText("申请时间：￥"+mItemListBeans.get(position).getHandleTime());
        return convertView;
    }
}
