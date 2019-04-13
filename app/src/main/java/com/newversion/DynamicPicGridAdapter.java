package com.newversion;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

public class DynamicPicGridAdapter extends BaseAdapter {

    private Context context;
    private List<String> pathList;

    public DynamicPicGridAdapter(Context context, List<String> pathList) {
        this.pathList = pathList;
        this.context = context;
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
            holder.iv_dynamic_photo = (ImageView) convertView.findViewById(R.id.iv_dynamic_photo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        ViewGroup.LayoutParams l = holder.iv_dynamic_photo.getLayoutParams();
        l.width=display.getWidth()*24/100;
        l.height=display.getWidth()*24/100;

        Glide.with(context).load(pathList.get(position)).into(holder.iv_dynamic_photo);

        return convertView;
    }

    public class ViewHolder {
        ImageView iv_dynamic_photo;
    }

}