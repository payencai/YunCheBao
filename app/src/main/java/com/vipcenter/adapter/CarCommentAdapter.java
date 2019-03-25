package com.vipcenter.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.payencai.library.view.CircleImageView;
import com.vipcenter.model.CarOrderComment;
import com.vipcenter.model.WashOrderComment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/3/25 17:49
 * 邮箱：771548229@qq..com
 */
public class CarCommentAdapter extends BaseQuickAdapter<CarOrderComment, BaseViewHolder> {
    PhotoAdapter mPhotoAdapter;
    List<String> images;

    public CarCommentAdapter(int layoutResId, @Nullable List<CarOrderComment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarOrderComment item) {
        GridView gv_photo = (GridView) helper.getView(R.id.gv_photo);
        TextView iv_content = (TextView) helper.getView(R.id.iv_content);
        CircleImageView userhead = (CircleImageView) helper.getView(R.id.userhead);
        TextView tv_name = (TextView) helper.getView(R.id.tv_name);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        SimpleRatingBar starbar = (SimpleRatingBar) helper.getView(R.id.starbar);
        images=new ArrayList<>();
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
        iv_content.setText(item.getContent());
        tv_name.setText(item.getName());
        Glide.with(mContext).load(item.getHeadPortrait()).into(userhead);
        tv_time.setText(item.getCreateTime().substring(0,10));
        starbar.setRating((float)item.getScore());
        starbar.setEnabled(false);
    }



}
