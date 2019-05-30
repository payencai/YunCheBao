package com.example.yunchebao.collect.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.collect.model.CollectImage;

import java.util.List;

public class CollectImageAdapter extends BaseQuickAdapter<CollectImage, BaseViewHolder> {
    public CollectImageAdapter(int layoutResId, @Nullable List<CollectImage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectImage item) {
        ImageView iv_img=helper.getView(R.id.iv_img);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_name.setText(item.getTitle());
        tv_time.setText(item.getCreateTime());
        Glide.with(mContext).load(item.getImage()).into(iv_img);
    }
}
