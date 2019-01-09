package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cheyibao.model.CoachItem;
import com.cheyibao.model.CoashComment;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class CoashCommentAdapter extends BaseAdapter{
    private List<CoashComment> mClassItems;
    private Context mContext;

    public CoashCommentAdapter(Context context, List<CoashComment> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<CoashComment> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<CoashComment> classItems) {
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
       // return mClassItems.size();
        return 3;
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
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_coash_comment,null);
        return view;
    }
}
