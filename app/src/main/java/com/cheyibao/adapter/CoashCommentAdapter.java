package com.cheyibao.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.payencai.library.view.CircleImageView;
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
        ArrayList<String> images=new ArrayList<>();
        convertView= LayoutInflater.from(mContext).inflate(R.layout.item_coash_comment,null);
        CoashComment coashComment=mClassItems.get(position);
        GridView gv_photo= convertView.findViewById(R.id.gv_photo);

        TextView tv_content = (TextView) convertView.findViewById(R.id.tv_content);
        CircleImageView iv_userhead= (CircleImageView) convertView.findViewById(R.id.userhead);
        CircleImageView iv_coashhead= (CircleImageView) convertView.findViewById(R.id.coashhead);
        TextView tv_name= (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_coashname= (TextView) convertView.findViewById(R.id.tv_coashname);
        TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
        SimpleRatingBar sb_score = (SimpleRatingBar) convertView.findViewById(R.id.sb_score);
        tv_content.setText(coashComment.getContent());
        tv_name.setText(coashComment.getName());
        tv_time.setText("2011-12-10");
        sb_score.setRating(coashComment.getScore());

        if(!TextUtils.isEmpty(mClassItems.get(position).getPhoto())){
            if(mClassItems.get(position).getPhoto().contains(",")){
                String[] img=mClassItems.get(position).getPhoto().split(",");
                for (int i = 0; i <img.length ; i++) {
                    images.add(img[i]);
                }
            }else{
                images.add(mClassItems.get(position).getPhoto());
            }
        }

        iv_coashhead.setImageResource(R.mipmap.ic_default_head);
        tv_coashname.setText("匿名用户");
        PhotoAdapter mPhotoAdapter=new PhotoAdapter(mContext,images);
        gv_photo.setAdapter(mPhotoAdapter);

        gv_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("images",images.size()+"");
                PictureConfig config = new PictureConfig.Builder()
                        .setListData(images)  //图片数据List<String> list
                        .setPosition(position)                         //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("coach")        //图片下载文件夹地址
                        .setIsShowNumber(true)                  //是否显示数字下标
                        .needDownload(true)                     //是否支持图片下载
                        .setPlaceHolder(R.mipmap.ic_default_head)   //占位符
                        .build();
                config.gotoActivity(mContext, config);

            }

        });
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(mContext).load(coashComment.getHeadPortrait()).apply(mRequestOptions).into(iv_userhead);
        return convertView;
    }
}
