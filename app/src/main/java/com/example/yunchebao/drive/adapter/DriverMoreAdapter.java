package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.drive.model.DriveMan;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.vipcenter.adapter.PhotoAdapter;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/26 11:15
 * 邮箱：771548229@qq..com
 */
public class DriverMoreAdapter extends BaseQuickAdapter<DriveMan,BaseViewHolder> {
    public DriverMoreAdapter(int layoutResId, @Nullable List<DriveMan> data) {
        super(layoutResId, data);
    }
    PhotoAdapter mPhotoAdapter;
    List<String> images;
    @Override
    protected void convert(BaseViewHolder helper, DriveMan item) {
        ImageView iv_head= (ImageView) helper.getView(R.id.iv_head);
        TextView tv_name= (TextView) helper.getView(R.id.tv_name);
        TextView tvgrade= (TextView) helper.getView(R.id.tv_rate);
        SimpleRatingBar simpleRatingBar= (SimpleRatingBar) helper.getView(R.id.sr_score);
        helper.addOnClickListener(R.id.tv_select);
        tv_name.setText(item.getName());
        simpleRatingBar.setRating(item.getScore());
        tvgrade.setText(item.getGrade()+"");
        Glide.with(mContext).load(item.getHeadPortrait()).apply(RequestOptions.bitmapTransform(new CenterCrop())).into(iv_head);
    }
}
