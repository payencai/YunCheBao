package com.rongcloud.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.rongcloud.activity.contact.GroupDetailActivity;
import com.rongcloud.model.GroupUser;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/24 11:06
 * 邮箱：771548229@qq..com
 */
public class GridAdapter extends BaseAdapter {
    Context mContext;
    List<GroupUser> mGroupUsers;
    @Override
    public int getCount() {
        return mGroupUsers.size();
    }

    public GridAdapter(Context context, List<GroupUser> groupUsers) {
        mContext = context;
        mGroupUsers = groupUsers;
    }

    @Override
    public Object getItem(int position) {
        return mGroupUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_user, null);
        TextView tv = (TextView) convertView.findViewById(R.id.nickname);
        LinearLayout ll_commom= (LinearLayout) convertView.findViewById(R.id.ll_commom);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.head);
        ImageView iv_change = (ImageView) convertView.findViewById(R.id.iv_change);
        if(mGroupUsers.size()>0){
         Glide.with(mContext).load(mGroupUsers.get(position).getHeadPortrait()).into(imageView);
         tv.setText(mGroupUsers.get(position).getNickName());}
         if(mGroupUsers.get(position).getFlag()!=0){
            ll_commom.setVisibility(View.GONE);
            if(mGroupUsers.get(position).getFlag()==1)
                iv_change.setImageResource(R.mipmap.chat_add);
            else{
                iv_change.setImageResource(R.mipmap.chat_delete);
            }
             iv_change.setVisibility(View.VISIBLE);
         }

        return convertView;
    }
}
