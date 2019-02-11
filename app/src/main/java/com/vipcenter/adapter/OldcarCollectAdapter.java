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
import com.vipcenter.model.GoodsCollect;
import com.vipcenter.model.OldCarCollect;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 全部收藏商品列表适配器
 */
public class OldcarCollectAdapter extends BaseAdapter {
    private List<OldCarCollect> list;
    private Context ctx;

    public OldcarCollectAdapter(Context ctx, List<OldCarCollect> list) {
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
        OldCarCollect goodList=list.get(position);
        convertView = LayoutInflater.from(ctx).inflate(R.layout.item_oldcar_collect, null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.img);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);

        String urls=goodList.getCarImage();
        if(urls.contains(",")){
            String []images=goodList.getCarImage().split(",");
            Glide.with(ctx).load(images[0]).into(iv_logo);
        }else{
            Glide.with(ctx).load(urls).into(iv_logo);
        }
        tv_name.setText(goodList.getFirstName()+goodList.getSecondName()+goodList.getFirstName());
        tv_price.setText("低至"+goodList.getCarPrice()+"万");

        return convertView;
    }


}
