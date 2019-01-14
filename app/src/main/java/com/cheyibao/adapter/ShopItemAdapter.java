package com.cheyibao.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cheyibao.NewCarDetailActivity;
import com.cheyibao.NewCarShopActivity;
import com.cheyibao.model.Shop;
import com.cheyibao.model.ShopComment;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class ShopItemAdapter extends BaseAdapter {
    private List<Shop> mClassItems;
    private Context mContext;

    public ShopItemAdapter(Context context, List<Shop> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<Shop> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<Shop> classItems) {
        mClassItems = classItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        //return mClassItems.size();
        if (mClassItems.size() == 0)
            return 0;
        else {
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return mClassItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address);
        TextView tv_sale = (TextView) convertView.findViewById(R.id.tv_sale);
        TextView tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
        TextView tv_service = (TextView) convertView.findViewById(R.id.tv_service);
        tv_name.setText(mClassItems.get(position).getName());
        tv_address.setText(mClassItems.get(position).getAddress());
        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,NewCarShopActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("data",mClassItems.get(position));
                mContext.startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewCarShopActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("data",mClassItems.get(position));
                mContext.startActivity(intent);
            }
        });
        tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
}
