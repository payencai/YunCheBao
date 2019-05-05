package com.xihubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.entity.ServerType;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/21 16:55
 * 邮箱：771548229@qq..com
 */
public class ServerCatogryAdapter extends BaseAdapter{
    Context mContext;
    List<ServerType> mServerTypes;
    int pos=0;
    @Override
    public int getCount() {
        return mServerTypes.size();
    }

    public ServerCatogryAdapter(Context context, List<ServerType> serverTypes) {
        mContext = context;
        mServerTypes = serverTypes;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_category_list,null);
        TextView name= (TextView) view.findViewById(R.id.goodsCategoryNameTV);
        name.setText(mServerTypes.get(position).getCategoryName());
        if(pos==position){
            view.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else{
            view.setBackgroundColor(mContext.getResources().getColor(R.color.gray_ee));
        }
        return view;
    }
}
