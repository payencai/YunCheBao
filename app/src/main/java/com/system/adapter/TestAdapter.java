package com.system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baike.model.ClassifyWiki;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/22 15:02
 * 邮箱：771548229@qq..com
 */
public class TestAdapter extends BaseAdapter {
    Context mContext;
    List<String> mHomeCategories;

    public TestAdapter(Context context, List<String> homeCategories) {
        mContext = context;
        mHomeCategories = homeCategories;
    }

    int pos=0;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public int getCount() {
        return mHomeCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mHomeCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_test_list,null);
        return convertView;
    }
}
