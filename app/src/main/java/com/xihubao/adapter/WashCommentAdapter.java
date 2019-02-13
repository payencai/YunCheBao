package com.xihubao.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.ShopComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.vipcenter.adapter.PhotoAdapter;
import com.xihubao.model.WashComment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/17 14:26
 * 邮箱：771548229@qq..com
 */
public class WashCommentAdapter extends BaseAdapter {
    Context mContext;
    List<WashComment> mWashComments;
    PhotoAdapter mPhotoAdapter;
    public WashCommentAdapter(Context context, List<WashComment> washComments) {
        mContext = context;
        mWashComments = washComments;
    }

    @Override
    public int getCount() {
        return mWashComments.size();
    }

    @Override
    public Object getItem(int position) {
        return mWashComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_comment, null);
        TextView iv_content = (TextView) convertView.findViewById(R.id.iv_content);
        ImageView userhead = (ImageView) convertView.findViewById(R.id.userhead);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        GridView gv_photo= (GridView) convertView.findViewById(R.id.gv_photo);
        SimpleRatingBar starbar = (SimpleRatingBar) convertView.findViewById(R.id.starbar);
        WashComment item=mWashComments.get(position);
        iv_content.setText(item.getContent());
        tv_name.setText(item.getNickName());
        tv_time.setText(item.getCreateTime().substring(0, 10));
        starbar.setRating(item.getScore());
        Glide.with(mContext).load(item.getHeadPortrait()).into(userhead);
        String imgs=item.getImgs();

        List<String> photos=new ArrayList<>();
        if(!TextUtils.isEmpty(imgs)){
            String []images=imgs.split(",");
            for (int i = 0; i <images.length ; i++) {
                photos.add(images[i]) ;
            }
        }
        mPhotoAdapter=new PhotoAdapter(mContext,photos);
        gv_photo.setAdapter(mPhotoAdapter);
        return convertView;
    }
}
