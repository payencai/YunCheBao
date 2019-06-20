package com.bbcircle.adapter;

/**
 * Created by sdhcjhss on 2018/1/8.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bbcircle.AllKindActivity;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodsType;

import java.util.List;

@SuppressLint("ViewHolder")
public class KindAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsType> strings;
    public static int mPosition;

    public KindAdapter(Context context, List<GoodsType> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
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
        tv.setText(strings.get(position).getName());
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