package com.example.yunchebao.fourshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.ServiceContent;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/18 15:10
 * 邮箱：771548229@qq..com
 */
public class ServiceTypeAdapter extends BaseAdapter {
    Context mContext;
    List<ServiceContent> mServiceContents;
    int pos=0;
    public ServiceTypeAdapter(Context context, List<ServiceContent> serviceContents) {
        mContext = context;
        mServiceContents = serviceContents;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return mServiceContents.size();
    }

    @Override
    public Object getItem(int position) {
        return mServiceContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_category_list,null);
        TextView name= (TextView) convertView.findViewById(R.id.goodsCategoryNameTV);
        name.setText(mServiceContents.get(position).getCategoryName());
        if(pos==position){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ee));
        }
        return convertView;
    }
}
