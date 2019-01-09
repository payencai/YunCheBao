package com.bbcircle.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbcircle.data.WashCollect;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rongcloud.model.CarShop;

import java.util.List;

/**
 * Created by sdhcjhss on 2017/12/7.
 */

public class WashCollectAdapter extends BaseAdapter {
    private Context ctx;
    private List<WashCollect> list;

    public WashCollectAdapter(Context ctx, List<WashCollect> list) {
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

        View view = LayoutInflater.from(ctx).inflate(R.layout.wash_list_item, null);
        WashCollect carShop = list.get(position);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.img);
        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);
        TextView item5 = (TextView) view.findViewById(R.id.item5);
        if (carShop.getLogo() != null) {
            Uri uri = Uri.parse(carShop.getLogo());
            simpleDraweeView.setImageURI(uri);
        }
        item1.setText(carShop.getShopName());
        item2.setText("评分" + carShop.getScore() + "|订单" + carShop.getOrderNum());
        item3.setText(carShop.getAddress());
        item4.setVisibility(View.GONE);
        item5.setVisibility(View.GONE);
        return view;
    }


}
