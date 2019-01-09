package com.bbcircle.adapter;

/**
 * Created by sdhcjhss on 2018/1/8.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbcircle.AllKindActivity;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;

@SuppressLint("ViewHolder")
public class KindAdapter extends BaseAdapter {

    private Context context;
    private String[] strings;
    public static int mPosition;

    public KindAdapter(Context context, String[] strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.kind_list_item, null);
        SuperTextView tv = (SuperTextView) convertView.findViewById(R.id.tv);
        mPosition = position;
        tv.setText(strings[position]);
        if (position == AllKindActivity.mPosition) {
            tv.setSolid(ContextCompat.getColor(context, R.color.yellow_65));
            tv.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            tv.setSolid(ContextCompat.getColor(context, R.color.white));
            tv.setTextColor(ContextCompat.getColor(context, R.color.black_33));
        }
        return convertView;
    }
}