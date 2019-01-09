package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbcircle.data.History;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 浏览历史列表适配器
 */
public class HistoryListAdapter extends BaseAdapter {
    private List<History> list;
    private Context ctx;

    public HistoryListAdapter(Context ctx, List<History> list) {
        this.list = list;
        this.ctx = ctx;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.history_list_item, null);
            vh.tv_title= (TextView) convertView.findViewById(R.id.item1);
            vh.tv_name= (TextView) convertView.findViewById(R.id.item2);
            vh.tv_titme= (TextView) convertView.findViewById(R.id.item3);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_title.setText(list.get(position).getTitle());
        vh.tv_name.setText(list.get(position).getName());
        vh.tv_titme.setText(list.get(position).getCreateTime());
        return convertView;
    }


    public class ViewHolder {
        TextView tv_title;
        TextView tv_name;
        TextView tv_titme;
    }

}
