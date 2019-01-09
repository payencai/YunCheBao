package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheyibao.model.Area;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class AreaItemAdapter extends BaseAdapter {
    private List<Area> mClassItems;
    private Context mContext;
    private int selectedPosition = 0;// 选中的位置
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public AreaItemAdapter(Context context, List<Area> classItems) {
        mClassItems = classItems;
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
         convertView = LayoutInflater.from(mContext).inflate(R.layout.item_area, null);
        if (position == selectedPosition) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color));
        }
        TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
        tv_area.setText(mClassItems.get(position).getName());
        return convertView;
    }
}
