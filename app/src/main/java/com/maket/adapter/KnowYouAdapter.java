package com.maket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.maket.model.KnowYou;

import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class KnowYouAdapter extends BaseAdapter {
    private Context ctx;
    private List<KnowYou> list;

    public KnowYouAdapter(Context ctx, List<KnowYou> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 5;
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
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_know_you, null);
        return convertView;
    }


}
