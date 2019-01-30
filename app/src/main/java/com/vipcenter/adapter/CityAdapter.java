package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cheyibao.model.Area;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/30 10:26
 * 邮箱：771548229@qq..com
 */
public class CityAdapter extends BaseAdapter{
    Context mContext;
    List<Area> mAreas;
    int pos=0;

    public CityAdapter(Context context, List<Area> areas) {
        mContext = context;
        mAreas = areas;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return mAreas.size();
    }

    @Override
    public Object getItem(int position) {
        return mAreas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_city,null);
        TextView tv_city= (TextView) convertView.findViewById(R.id.tv_city);
        tv_city.setText(mAreas.get(position).getName());
        if(pos==position){
            tv_city.setTextColor(mContext.getResources().getColor(R.color.yellow_02));
        }else{
            tv_city.setTextColor(mContext.getResources().getColor(R.color.black_33));
        }
        return convertView;
    }
}
