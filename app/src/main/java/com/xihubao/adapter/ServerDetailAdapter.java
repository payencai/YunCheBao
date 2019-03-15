package com.xihubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.entity.ServerType;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/21 16:57
 * 邮箱：771548229@qq..com
 */
public class ServerDetailAdapter  extends BaseAdapter {
    Context mContext;
    List<ServerType.ServeListBean> mServerTypes;
    @Override
    public int getCount() {
        return mServerTypes.size();
    }

    public ServerDetailAdapter(Context context, List<ServerType.ServeListBean> serverTypes) {
        mContext = context;
        mServerTypes = serverTypes;
    }

    @Override
    public Object getItem(int position) {
        return mServerTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ServerType.ServeListBean serveListBean=mServerTypes.get(position);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list,null);
        TextView goodsCategoryName= (TextView) view.findViewById(R.id.goodsCategoryName);
        TextView price= (SuperTextView) view.findViewById(R.id.price);
        TextView tvGoodsDescription= (TextView) view.findViewById(R.id.tvGoodsDescription);
        goodsCategoryName.setText(serveListBean.getSecondName());
        price.setText("￥"+serveListBean.getPrice());
        tvGoodsDescription.setText(serveListBean.getSecondContent());
        return view;
    }
}