package com.xihubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.xihubao.model.RoadService;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/22 17:38
 * 邮箱：771548229@qq..com
 */
public class RoadItemAdapter extends BaseAdapter {
    private Context ctx;
    private List<RoadService> list;

    public RoadItemAdapter(Context ctx, List<RoadService> list) {
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

        View view= LayoutInflater.from(ctx).inflate(R.layout.item_road_detail,null);
        RoadService carShop=list.get(position);
        TextView title= (TextView) view.findViewById(R.id.title);
        TextView content= (TextView) view.findViewById(R.id.content);
        title.setText(carShop.getTitle());
        content.setText(carShop.getContent());
        return view;
    }

}
