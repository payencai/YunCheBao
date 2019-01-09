package com.xihubao.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xihubao.model.RoadItem;

import java.util.List;

/**
 * Created by sdhcjhss on 2017/12/7.
 * 道路救援列表页适配器
 */

public class AssistanceListAdapter extends BaseAdapter {
    private Context ctx;
    private List<RoadItem> list;

    public AssistanceListAdapter(Context ctx, List<RoadItem> list) {
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.assistant_list_item, null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.item1 = (TextView) convertView.findViewById(R.id.item1);
            vh.item2 = (TextView) convertView.findViewById(R.id.item2);
            vh.item3 = (TextView) convertView.findViewById(R.id.item3);
            vh.item4 = (TextView) convertView.findViewById(R.id.item4);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (list.size() > 0) {
            vh.img.setImageURI(Uri.parse(list.get(position).getLogo()));
            vh.item1.setText(list.get(position).getShopName());
            vh.item2.setText(list.get(position).getDistance() + "km");
            vh.item3.setText(list.get(position).getRemark());
            String content = "";
            for (int i = 0; i < list.get(position).getRoadRescueServeList().size(); i++) {
                content = list.get(position).getRoadRescueServeList().get(i).getTitle() + " " + content;
            }
            vh.item4.setText(content);
        }
        return convertView;
    }

    public class ViewHolder {
        SimpleDraweeView img;
        public TextView item1;
        public TextView item2;
        public TextView item3;
        public TextView item4;
    }
}
