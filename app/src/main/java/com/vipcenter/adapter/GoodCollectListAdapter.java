package com.vipcenter.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodList;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 全部收藏商品列表适配器
 */
public class GoodCollectListAdapter extends BaseAdapter {
    private List<GoodList> list;
    private Context ctx;

    public GoodCollectListAdapter(Context ctx, List<GoodList> list) {
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
        GoodList goodList=list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_know_you, null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.iv_logo);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_new = (TextView) convertView.findViewById(R.id.tv_newprice);
        TextView tv_old = (TextView) convertView.findViewById(R.id.tv_oldprice);
        TextView tv_sale = (TextView) convertView.findViewById(R.id.tv_sale);
        String urls=goodList.getCommodityImage();
        if(urls.contains(",")){
            String []images=goodList.getCommodityImage().split(",");
            Glide.with(ctx).load(images[0]).into(iv_logo);
        }else{
            Glide.with(ctx).load(urls).into(iv_logo);
        }

        tv_sale.setText("销量："+goodList.getNumber());
        tv_name.setText(goodList.getName());
        tv_new.setText("￥"+goodList.getDiscountPrice());
        tv_old.setText("￥"+goodList.getOriginalPrice());
        tv_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        return convertView;
    }


}
