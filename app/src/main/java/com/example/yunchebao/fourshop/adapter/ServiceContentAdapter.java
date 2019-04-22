package com.example.yunchebao.fourshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.ServiceContent;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/18 15:10
 * 邮箱：771548229@qq..com
 */
public class ServiceContentAdapter extends BaseAdapter {
    Context mContext;
    List<ServiceContent.ServeListBean> mServiceContents;
    int pos=0;
    public ServiceContentAdapter(Context context, List<ServiceContent.ServeListBean> serviceContents) {
        mContext = context;
        mServiceContents = serviceContents;
    }

    @Override
    public int getCount() {
        return mServiceContents.size();
    }

    @Override
    public Object getItem(int position) {
        return mServiceContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServiceContent.ServeListBean serveListBean=mServiceContents.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list,null);
        TextView goodsCategoryName= (TextView) view.findViewById(R.id.goodsCategoryName);
        TextView price= (SuperTextView) view.findViewById(R.id.price);
        TextView tvGoodsDescription= (TextView) view.findViewById(R.id.tvGoodsDescription);
        goodsCategoryName.setText(serveListBean.getSecondName());
        price.setText("￥"+serveListBean.getPrice());
        tvGoodsDescription.setText(serveListBean.getSecondContent());
        return  view;
    }
}
