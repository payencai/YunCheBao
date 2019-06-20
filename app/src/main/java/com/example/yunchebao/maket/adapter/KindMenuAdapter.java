package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class KindMenuAdapter extends BaseAdapter {
    private Context ctx;
    private List<PhoneGoodEntity> list;

    public KindMenuAdapter(Context ctx, List<PhoneGoodEntity> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null ){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.pic1_txt1_item,null);
            vh.img = (SimpleDraweeView) convertView.findViewById(R.id.img);
            vh.text = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public class ViewHolder{
        public SimpleDraweeView img;
        public TextView text;
    }
}
