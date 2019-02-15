package com.vipcenter.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.PhoneTraceEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 列表适配器
 */
public class LogisticsTraceListAdapter extends BaseAdapter {
    private List<PhoneTraceEntity.ListBean> list;
    private Context ctx;

    public LogisticsTraceListAdapter(Context ctx, List<PhoneTraceEntity.ListBean> list) {
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
        PhoneTraceEntity.ListBean listBean=list.get(position);
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.logistics_trace_list_item, null);
            vh.icon = (ImageView) convertView.findViewById(R.id.icon);
            vh.item1 = (TextView) convertView.findViewById(R.id.item1);
            vh.item2 = (TextView) convertView.findViewById(R.id.item2);
            vh.topLine = convertView.findViewById(R.id.topLine);
            vh.bottomLine = convertView.findViewById(R.id.bottomLine);
            vh.grayIcon = (ImageView) convertView.findViewById(R.id.grayIcon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (position == 0) {
            vh.icon.setVisibility(View.VISIBLE);
            vh.topLine.setVisibility(View.INVISIBLE);
            vh.bottomLine.setVisibility(View.VISIBLE);
            vh.grayIcon.setVisibility(View.GONE);
            vh.item1.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
        } else {
            if (position >= list.size() - 1 && position == list.size() - 1) {
                vh.bottomLine.setVisibility(View.INVISIBLE);
                vh.grayIcon.setVisibility(View.VISIBLE);
                vh.topLine.setVisibility(View.VISIBLE);
            } else {
                vh.topLine.setVisibility(View.VISIBLE);
                vh.bottomLine.setVisibility(View.VISIBLE);
                vh.grayIcon.setVisibility(View.GONE);
            }
            vh.icon.setVisibility(View.GONE);
        }
        vh.item1.setText(listBean.getRemark());
        vh.item2.setText(listBean.getDatetime());
        return convertView;
    }


    public class ViewHolder {
        public TextView item1;
        public TextView item2;
        public TextView item3;
        public TextView item4;
        public ImageView icon;
        public View topLine;
        public View bottomLine;
        public ImageView grayIcon;
    }

}
