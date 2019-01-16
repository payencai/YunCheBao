package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.RentCarType;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class RentTypeAdapter extends BaseAdapter {
    private List<RentCarType> mClassItems;
    private Context mContext;

    public RentTypeAdapter(Context context, List<RentCarType> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<RentCarType> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<RentCarType> classItems) {
        mClassItems = classItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        //return mClassItems.size();
        return mClassItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mClassItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RentCarType mRentCarType=mClassItems.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rent_type, null);
        ImageView iv_car= (ImageView) convertView.findViewById(R.id.iv_car);
        TextView tv_name1= (TextView) convertView.findViewById(R.id.tv_name1);
        TextView tv_name2= (TextView) convertView.findViewById(R.id.tv_name2);
        TextView tv_price= (TextView) convertView.findViewById(R.id.tv_price);
        TextView tv_param= (TextView) convertView.findViewById(R.id.tv_param);
        Glide.with(mContext).load(mRentCarType.getPhoto()).into(iv_car);
        tv_name1.setText(mRentCarType.getBrand());
        tv_name2.setText(mRentCarType.getModel());
        tv_price.setText("￥"+mRentCarType.getDayPrice());
        tv_param.setText(mRentCarType.getManualAutomatic()+"/"+mRentCarType.getSeat()+"座");
        return convertView;
    }
}
