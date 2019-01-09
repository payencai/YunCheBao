package com.vipcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 浏览历史列表适配器
 */
public class ArticleListAdapter extends BaseAdapter {
    private List<PhoneArticleEntity> list;
    private Context ctx;

    public ArticleListAdapter(Context ctx, List<PhoneArticleEntity> list) {
        this.list = list;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_article, null);
            vh.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            vh.video = (ImageView) convertView.findViewById(R.id.video);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Glide.with(convertView.getContext()).load(list.get(position).getImage()).into(vh.iv_head);
        //vh.item1.setText("去年今日此门中，人面桃花相映红");
        vh.tv_title.setText(list.get(position).getTitle());
        if(list.get(position).getType()>=3){
            vh.video.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    public class ViewHolder {
        ImageView iv_head;
        ImageView video;
        TextView tv_title;

    }

}
