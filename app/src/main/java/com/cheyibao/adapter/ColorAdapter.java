package com.cheyibao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/14 10:05
 * 邮箱：771548229@qq..com
 */
public class ColorAdapter extends BaseAdapter{
    Context mContext;
    List<String> mStringList;
    int pos=0;
    public ColorAdapter(Context context, List<String> stringList) {
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_color,null);
        TextView tv_color= (TextView) convertView.findViewById(R.id.tv_color);
        LinearLayout ll_color= (LinearLayout) convertView.findViewById(R.id.ll_color);
        TextView tv_icon= (TextView) convertView.findViewById(R.id.st_color);
        if(pos==position){
            ll_color.setBackgroundResource(R.drawable.selected_bg_yellow_small);
        }else{
            ll_color.setBackgroundResource(R.drawable.gray_stroke_r4);
        }

        tv_color.setText(mStringList.get(position));
        GradientDrawable st_color = (GradientDrawable) tv_icon.getBackground();
        switch (position){
            case 0:
                st_color.setColor(mContext.getResources().getColor(R.color.black_23));
                break;
            case 1:
                st_color.setColor(mContext.getResources().getColor(R.color.gray_70));
                //st_color.setSolid(R.color.gray_70);
                break;
            case 2:
                tv_icon.setBackgroundResource(R.mipmap.qita_icon);
                //st_color.setSolid(R.color.white);
                break;
            case 3:
                st_color.setColor(mContext.getResources().getColor(R.color.green_69));
                //st_color.setTextColor(mContext.getResources().getColor(R.color.green_69));
                //st_color.setSolid(Color.BLUE);
                break;
            case 4:
                st_color.setColor(mContext.getResources().getColor(R.color.red_4e));
                //st_color.setSolid(R.color.red_4e);
                break;
            case 5:
                st_color.setColor(mContext.getResources().getColor(R.color.blue_f9));
                //st_color.setSolid(R.color.blue_f9);
                break;
            case 6:
                st_color.setColor(mContext.getResources().getColor(R.color.orange_95));
               // st_color.setSolid(R.color.orange_95);
                break;
            case 7:
                st_color.setColor(mContext.getResources().getColor(R.color.orange_3d));
                //st_color.setSolid(R.color.orange_3d);
                break;
            case 8:
                st_color.setColor(mContext.getResources().getColor(R.color.brown_22));
               // st_color.setSolid(R.color.brown_22);
                break;
            case 9:
                tv_icon.setBackgroundResource(R.mipmap.caise_icon);
                //st_color.setSolid(R.color.brown_4f);
                break;
            case 10:
                tv_icon.setBackgroundResource(R.mipmap.qita_icon);
                //st_color.setSolid(R.color.blue_ef8);
                break;
        }
        return convertView;
    }
}
