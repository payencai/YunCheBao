package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.NewCarMenu;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.xihubao.model.CarBrand;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class NewCarMenuAdapter extends BaseAdapter {
    private List<CarBrand> mClassItems;
    private Context mContext;

    public NewCarMenuAdapter(Context context, List<CarBrand> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<CarBrand> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<CarBrand> classItems) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_newcar_menu, null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        Glide.with(mContext).load(mClassItems.get(position).getImage()).into(iv_logo);
        tv_name.setText(mClassItems.get(position).getName());
        return convertView;
    }
}
