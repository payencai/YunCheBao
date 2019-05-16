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
import com.example.yunchebao.rongcloud.model.Group;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/17 14:17
 * 邮箱：771548229@qq..com
 */
public class GroupAdapter extends BaseAdapter {
    private Context mContext;
    List<Group> mGroups;

    public GroupAdapter(Context context, List<Group> groups) {
        mContext = context;
        mGroups = groups;
    }

    @Override
    public int getCount() {
        return mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
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
        TextView mana= (TextView) view.findViewById(R.id.mana);
        if(mGroups.get(position).isMyGroup()){
            mana.setVisibility(View.VISIBLE);
        }
        tv_name.setText(mGroups.get(position).getCrowdName());
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(mContext).load(mGroups.get(position).getImage()).apply(mRequestOptions).into(imageView);
        return view;
    }
}
