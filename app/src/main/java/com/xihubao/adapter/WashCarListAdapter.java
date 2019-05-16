package com.xihubao.adapter;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.MathUtil;

import java.util.List;

/**
 * Created by sdhcjhss on 2017/12/7.
 */

public class WashCarListAdapter extends BaseAdapter {
    private Context ctx;
    private List<CarShop> list;

    public WashCarListAdapter(Context ctx, List<CarShop> list) {
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
        CarShop carShop = list.get(position);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.img);
        TextView item1 = (TextView) view.findViewById(R.id.item1);
        TextView item2 = (TextView) view.findViewById(R.id.item2);
        TextView item3 = (TextView) view.findViewById(R.id.item3);
        TextView item4 = (TextView) view.findViewById(R.id.item4);
        TextView item5 = (TextView) view.findViewById(R.id.item5);
        SimpleRatingBar sb_score= (SimpleRatingBar) view.findViewById(R.id.sb_score);
        if (!TextUtils.isEmpty(carShop.getLogo())) {
            Uri uri = Uri.parse(carShop.getLogo());
            if (uri != null)
                simpleDraweeView.setImageURI(uri);
        }
        sb_score.setRating((float) carShop.getScore());
        item1.setText(carShop.getShopName());
        item2.setText("评分" + carShop.getGrade() + "|订单" + carShop.getOrderNum());
        item3.setText(carShop.getAddress());
        item4.setText("￥" + carShop.getPrice());
        item5.setText(MathUtil.getDoubleTwo(carShop.getDistance()) + "km");
        return view;
    }


}
