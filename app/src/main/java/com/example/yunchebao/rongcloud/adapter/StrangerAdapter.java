package com.example.yunchebao.rongcloud.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.Friend;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/11 10:00
 * 邮箱：771548229@qq..com
 */
public class StrangerAdapter extends BaseQuickAdapter<Friend, BaseViewHolder> {


    public StrangerAdapter(int layoutResId, @Nullable List<Friend> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Friend item) {
        helper.setIsRecyclable(false);
        ImageView imageView= (ImageView) helper.getView(R.id.iv_icon);
        TextView tv_name= (TextView) helper.getView(R.id.tv_name);
        tv_name.setText(item.getName()+" ("+item.getHxAccount() + ")");
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(mContext).load(item.getHeadPortrait()).apply(mRequestOptions).into(imageView);
    }
}
