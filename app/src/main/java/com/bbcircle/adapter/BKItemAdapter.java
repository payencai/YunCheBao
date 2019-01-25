package com.bbcircle.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baike.model.BaikeItem;
import com.bbcircle.data.CarFriend;
import com.bbcircle.data.SelfDrive;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 宝贝圈列表适配器
 */
public class BKItemAdapter extends BaseQuickAdapter<BaikeItem, BaseViewHolder> {


    public BKItemAdapter(int layoutResId, @Nullable List<BaikeItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, BaikeItem baikeItem) {
      //  View convertView = LayoutInflater.from(ctx).inflate(R.layout.item_baike, null);

        ImageView ivAvatar = (ImageView) helper.getView(R.id.iv_pic);
        TextView tv_title = (TextView) helper.getView(R.id.tv_title);
        TextView tv_time = (TextView) helper.getView(R.id.tv_time);
        TextView tv_content=(TextView) helper.getView(R.id.tv_content);

        tv_time.setText(baikeItem.getCreateTime());
        tv_title.setText(baikeItem.getTitle());
        tv_content.setText(baikeItem.getSynopsis());
        if(!TextUtils.isEmpty(baikeItem.getPicture()))
        Glide.with(helper.itemView.getContext()).load(baikeItem.getPicture()).into(ivAvatar);
    }




}
