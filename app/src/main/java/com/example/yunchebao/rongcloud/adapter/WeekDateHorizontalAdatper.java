package com.example.yunchebao.rongcloud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.WeekDate;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/20 14:01
 * 邮箱：771548229@qq..com
 */
public class WeekDateHorizontalAdatper extends BaseAdapter {
    private Context mContext;
    private List<WeekDate> mWeekDates;
    public WeekDateHorizontalAdatper(Context context,List<WeekDate> mWeekDates) {
        this.mContext=context;
        this.mWeekDates=mWeekDates;
    }

    @Override
    public int getCount() {
        return mWeekDates.size();
    }

    @Override
    public Object getItem(int position) {
        return mWeekDates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WeekDate weekDate=mWeekDates.get(position);
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_grid_date,null);
        TextView tv_date= (TextView) view.findViewById(R.id.tv_date);
        tv_date.setText(weekDate.getDate().substring(5));
        TextView tv_week= (TextView) view.findViewById(R.id.tv_week);
        tv_week.setText(weekDate.getWeek());
        if(weekDate.isSelect()){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            tv_date.setTextColor(mContext.getResources().getColor(R.color.white));
            tv_week.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            tv_date.setTextColor(mContext.getResources().getColor(R.color.black_33));
            tv_week.setTextColor(mContext.getResources().getColor(R.color.black_33));
        }
        return view;
    }
}
