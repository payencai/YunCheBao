package com.cheyibao.adapter;

import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/17 14:26
 * 邮箱：771548229@qq..com
 */
public class RvCommentAdapter extends BaseQuickAdapter<ShopComment,BaseViewHolder> {
    PhotoAdapter mPhotoAdapter;
    List<String> images;
    public RvCommentAdapter(int layoutResId, @Nullable List<ShopComment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopComment item) {
        TextView iv_content = (TextView) helper.getView(R.id.iv_content);
        ImageView userhead = (ImageView) helper.getView(R.id.userhead);
        TextView tv_name = (TextView) helper.getView(R.id.tv_name);
        GridView gv_photo = (GridView) helper.getView(R.id.gv_photo);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        SimpleRatingBar starbar = (SimpleRatingBar) helper.getView(R.id.starbar);
        if (item.getId()!=null) {
            iv_content.setText(item.getContent());
            tv_name.setText(item.getName());
            tv_time.setText(item.getCreateTime().substring(0,10)+"");
            starbar.setRating((float) item.getScore());
            Glide.with(mContext).load(item.getHeadPortrait()).into(userhead);
            images = new ArrayList<>();
            if (item.getPhoto() != null) {
                if (item.getPhoto().contains(",")) {
                    String[] img = item.getPhoto().split(",");
                    for (int i = 0; i < img.length; i++) {
                        images.add(img[i]);
                    }
                } else {
                    images.add(item.getPhoto());
                }
            }
            mPhotoAdapter = new PhotoAdapter(mContext, images);
            gv_photo.setAdapter(mPhotoAdapter);
        }
    }
}
