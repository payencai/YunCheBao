package com.system.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.example.yunchebao.maket.model.GoodList;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 首页菜单列表适配器
 */
public class GoodsListAdapter extends BaseAdapter {
    private List<GoodList> list;
    private Context ctx;

    public GoodsListAdapter(Context ctx, List<GoodList> list) {
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
            convertView = LayoutInflater.from(ctx).inflate(R.layout.txt1_on_pic1_item, null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if(!list.get(position).getCommodityImage().contains(","))
            vh.img.setImageURI(Uri.parse(list.get(position).getCommodityImage()));
        else{
            vh.img.setImageURI(Uri.parse(list.get(position).getCommodityImage().split(",")[0]));

        }

        return convertView;
    }


    public class ViewHolder {
        public SimpleDraweeView img;
        public TextView name;
    }

}
