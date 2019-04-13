package com.newversion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.ArrayList;

public class SelectPicGridAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<String> pathList;

    public SelectPicGridAdapter(Context context, ArrayList<String> pathList) {
        this.pathList = pathList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (pathList.size() < 9)
            return pathList.size() + 1;
        return 9;
    }

    @Override
    public Object getItem(int position) {
        return pathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_dynamic_photo, null);
            holder = new ViewHolder();
            holder.iv_dynamic_photo = (ImageView) convertView.findViewById(R.id.iv_dynamic_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == pathList.size()) {
            Glide.with(context).load(R.mipmap.add_photo).into(holder.iv_dynamic_photo);
        } else {
            Glide.with(context).load(pathList.get(position)).into(holder.iv_dynamic_photo);
        }

        return convertView;
    }

    public class ViewHolder {
        ImageView iv_dynamic_photo;
    }

}