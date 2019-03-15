package com.cheyibao.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
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
public class CarTypeAdapter extends BaseAdapter{
    Context mContext;
    List<String> mStringList;
    int pos=0;
    public CarTypeAdapter(Context context, List<String> stringList) {
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_cartype,null);
        TextView tv_car= (TextView) convertView.findViewById(R.id.tv_car);
        LinearLayout ll_car= (LinearLayout) convertView.findViewById(R.id.ll_car);
        ImageView iv_car= (ImageView) convertView.findViewById(R.id.iv_car);
        if(pos==position){
            ll_car.setBackgroundResource(R.drawable.selected_bg_yellow_small);
        }else{
            ll_car.setBackgroundResource(R.drawable.gray_stroke_r4);
        }

        tv_car.setText(mStringList.get(position));

        switch (position){
            case 0:
                iv_car.setImageResource(R.mipmap.select_2);
                break;
            case 1:
                iv_car.setImageResource(R.mipmap.select_3);
                //st_color.setSolid(R.color.gray_70);
                break;
            case 2:
                iv_car.setImageResource(R.mipmap.select_pika);
                //st_color.setSolid(R.color.white);
                break;
            case 3:
                iv_car.setImageResource(R.mipmap.select_paoche);
                //st_color.setTextColor(mContext.getResources().getColor(R.color.green_69));
                //st_color.setSolid(Color.BLUE);
                break;
            case 4:
                iv_car.setImageResource(R.mipmap.select_mianbao);
                //st_color.setSolid(R.color.red_4e);
                break;
            case 5:
                iv_car.setImageResource(R.mipmap.select_whatever);
                //st_color.setSolid(R.color.blue_f9);
                break;

        }
        return convertView;
    }
}
