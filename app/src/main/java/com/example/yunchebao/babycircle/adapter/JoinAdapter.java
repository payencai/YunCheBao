package com.example.yunchebao.babycircle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bbcircle.data.SeldDrvingDetail;
import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;

import java.util.List;

public class JoinAdapter extends BaseAdapter {
    Context context;
    List<SeldDrvingDetail.ListBean> mListBeans;

    public JoinAdapter(Context context, List<SeldDrvingDetail.ListBean> mListBeans) {
        this.context = context;
        this.mListBeans = mListBeans;
    }

    @Override
    public int getCount() {
        return mListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return mListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SeldDrvingDetail.ListBean listBean=mListBeans.get(position);
        convertView= LayoutInflater.from(context).inflate(R.layout.item_join,null);
        CircleImageView iv_head=convertView.findViewById(R.id.iv_head);
        TextView tv_name=convertView.findViewById(R.id.tv_name);
        TextView tv_time=convertView.findViewById(R.id.tv_time);
        TextView tv_nick=convertView.findViewById(R.id.tv_nick);
        TextView tv_phone=convertView.findViewById(R.id.tv_phone);
        tv_name.setText(listBean.getName());
        tv_nick.setText(listBean.getNickname());
        tv_phone.setText(listBean.getTelephone());
        tv_time.setText(listBean.getCreateTime());
        Glide.with(context).load(listBean.getHeadPortrait()).into(iv_head);
        return convertView;
    }
}
