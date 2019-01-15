package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.CoashComment;
import com.cheyibao.model.ShopComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class ShopCommentAdapter extends BaseAdapter {
    private List<ShopComment> mClassItems;
    private Context mContext;

    public ShopCommentAdapter(Context context, List<ShopComment> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<ShopComment> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<ShopComment> classItems) {
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
        return mClassItems.size();
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_comment, null);
        TextView iv_content= (TextView) convertView.findViewById(R.id.iv_content);
        ImageView userhead= (ImageView) convertView.findViewById(R.id.userhead);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_time= (TextView) convertView.findViewById(R.id.tv_time);
        SimpleRatingBar starbar= (SimpleRatingBar) convertView.findViewById(R.id.starbar);
        iv_content.setText(mClassItems.get(position).getContent());
        tv_name.setText(mClassItems.get(position).getName());
        tv_time.setText(mClassItems.get(position).getReplyTime().substring(0,10));
        starbar.setRating(mClassItems.get(position).getScore());
        Glide.with(mContext).load(mClassItems.get(position).getHeadPortrait()).into(userhead);
        return convertView;
    }
}
