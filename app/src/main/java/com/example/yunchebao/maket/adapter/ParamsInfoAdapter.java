package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodInfoParams;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class ParamsInfoAdapter extends BaseAdapter {
    private Context ctx;
    private List<GoodInfoParams> list;

    public ParamsInfoAdapter(Context ctx, List<GoodInfoParams> list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GoodInfoParams goodInfoParams=list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_good_param, null);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_value= (TextView) convertView.findViewById(R.id.tv_value);
        tv_name.setText(goodInfoParams.getParamName());
        tv_value.setText(goodInfoParams.getParamValue());
        return convertView;
    }


}
