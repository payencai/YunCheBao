package com.cheyibao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cheyibao.model.CoachItem;
import com.cheyibao.model.CoashComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.vipcenter.adapter.PhotoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class CoashCommentAdapter extends BaseAdapter{
    private List<CoashComment> mClassItems;
    private Context mContext;
    PhotoAdapter mPhotoAdapter;
    List<String> images;
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
        return mClassItems.size();
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
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coash_comment,null);
        CoashComment coashComment=mClassItems.get(position);
        GridView gv_photo= (GridView) convertView.findViewById(R.id.gv_photo);
        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        ImageView iv_userhead= (ImageView) convertView.findViewById(R.id.userhead);
        ImageView iv_coashhead= (ImageView) convertView.findViewById(R.id.coashhead);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_coashname= (TextView) convertView.findViewById(R.id.tv_coashname);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        SimpleRatingBar sb_score = (SimpleRatingBar) convertView.findViewById(R.id.sb_score);
        tv_content.setText(coashComment.getContent());
        tv_name.setText(coashComment.getName());
        tv_time.setText("2011-12-10");
        sb_score.setRating(coashComment.getScore());
        images=new ArrayList<>();
        if(mClassItems.get(position).getPhoto().contains(",")){
            String[] img=mClassItems.get(position).getPhoto().split(",");
            for (int i = 0; i <img.length ; i++) {
                images.add(img[i]);
            }
        }else{
            images.add(mClassItems.get(position).getPhoto());
        }
        iv_coashhead.setImageResource(R.mipmap.ic_default_head);
        tv_coashname.setText("匿名用户");
        mPhotoAdapter=new PhotoAdapter(mContext,images);
        gv_photo.setAdapter(mPhotoAdapter);
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(mContext).load(coashComment.getHeadPortrait()).apply(mRequestOptions).into(iv_userhead);
        return convertView;
    }
}
