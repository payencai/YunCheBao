package com.example.yunchebao.yuedan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.model.CarType;

import java.util.List;

public class CarTypeAdapter extends BaseAdapter {
    Context context;
    List<CarType>  mCarTypes;
    @Override
    public int getCount() {
        return mCarTypes.size();
    }

    public CarTypeAdapter(Context context, List<CarType> mCarTypes) {
        this.context = context;
        this.mCarTypes = mCarTypes;
    }

    @Override
    public Object getItem(int position) {
        return mCarTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.item_select_car,null);
        TextView tv_type=convertView.findViewById(R.id.tv_type);
        tv_type.setText(mCarTypes.get(position).getName());
        return convertView;
    }
}
