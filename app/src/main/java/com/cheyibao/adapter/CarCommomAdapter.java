package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/14 10:05
 * 邮箱：771548229@qq..com
 */
public class CarCommomAdapter extends BaseAdapter{
    Context mContext;
    List<String> mStringList;
    int pos=0;
    public CarCommomAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_commom_type,null);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        LinearLayout ll_car= (LinearLayout) convertView.findViewById(R.id.ll_type);
        if(pos==position){
            ll_car.setBackgroundResource(R.drawable.selected_bg_yellow_small);
        }else{
            ll_car.setBackgroundResource(R.drawable.gray_stroke_r4);
        }
        tv_name.setText(mStringList.get(position));


        return convertView;
    }
}
