package com.maket.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.maket.model.GoodParam;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class ConfigSizeAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodParam.SecondSpecificationsBean> list;
    int pos=0;
    public ConfigSizeAdapter(Context ctx, List<GoodParam.SecondSpecificationsBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.super_text_item_layout, null);
            vh.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.text.setText(list.get(position).getSpecificationsValue());
        if(position==pos){

            vh.text.setBackgroundResource(R.drawable.shape_param_select);
            vh.text.setTextColor(ctx.getResources().getColor(R.color.red_4e));
        }else{
            vh.text.setBackgroundResource(R.drawable.shape_param_unselect);
            vh.text.setTextColor(ctx.getResources().getColor(R.color.black_33));
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView text;
    }
}