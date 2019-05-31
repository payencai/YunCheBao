package com.newversion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.adapter.FourShopCommentAdapter;

import java.util.ArrayList;

public class SelectPicGridAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<String> pathList;
    private boolean isShow;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public SelectPicGridAdapter(Context context, ArrayList<String> pathList) {
        this.pathList = pathList;
        this.context = context;
    }
    public OnImageClick onImageClick;
    public interface  OnImageClick{
        void onClick(int pos);
    }
    public void setOnImageClick(OnImageClick onImageClick){
        this.onImageClick=onImageClick;
    }
    @Override
    public int getCount() {
        return pathList.size();
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
            holder.iv_del=convertView.findViewById(R.id.iv_del);
            holder.iv_dynamic_photo = (ImageView) convertView.findViewById(R.id.iv_dynamic_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageClick.onClick(position);
            }
        });
        if (position == 0) {
            holder.iv_del.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.add_photo).into(holder.iv_dynamic_photo);
        } else {
            if(isShow)
                 holder.iv_del.setVisibility(View.VISIBLE);
            Glide.with(context).load(pathList.get(position)).into(holder.iv_dynamic_photo);
        }

        return convertView;
    }

    public class ViewHolder {
        ImageView iv_dynamic_photo;
        ImageView iv_del;
    }

}