package com.example.yunchebao.rongcloud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.Friend;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/17 11:55
 * 邮箱：771548229@qq..com
 */
public class FriendAdapter extends BaseAdapter{
    private Context  mContext;
    List<Friend> mFriends;

    public FriendAdapter(Context context, List<Friend> friends) {
        mContext = context;
        mFriends = friends;
    }

    @Override
    public int getCount() {
        return mFriends.size();

    }

    public List<Friend> getFriends() {
        return mFriends;
    }

    public void setFriends(List<Friend> friends) {
        mFriends = friends;
    }

    @Override
    public Object getItem(int position) {
        return mFriends.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_friends,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.iv_icon);
        TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
        tv_name.setText(mFriends.get(position).getName()+" ("+mFriends.get(position).getHxAccount() + ")");
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(mContext).load(mFriends.get(position).getHeadPortrait()).apply(mRequestOptions).into(imageView);
        return view;
    }
}
