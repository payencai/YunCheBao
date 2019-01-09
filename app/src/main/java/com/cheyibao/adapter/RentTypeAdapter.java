package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
        return 6;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rent_type, null);
        return view;
    }
}
