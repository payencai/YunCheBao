package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheyibao.model.Area;
import com.cheyibao.model.RentCar;
import com.example.yunchebao.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class RentCarItemAdapter extends BaseAdapter {
    private List<RentCar> mClassItems;
    private Context mContext;
    private int selectedPosition = 0;// 选中的位置

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public RentCarItemAdapter(Context context, List<RentCar> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    @Override
    public int getCount() {
        //return mClassItems.size();
        return  mClassItems.size();
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rentcar, null);
        TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_dis = (TextView) convertView.findViewById(R.id.tv_dis);
        if(mClassItems.size()>0){
        tv_name.setText(mClassItems.get(position).getName());
        tv_address.setText(mClassItems.get(position).getAddress());
        DecimalFormat decimalFormat=new DecimalFormat("0.000");
        tv_dis.setText(decimalFormat.format(mClassItems.get(position).getDistance()) + "km");}
        return convertView;
    }
}
