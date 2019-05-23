package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.system.model.HomeImage;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/23 15:17
 * 邮箱：771548229@qq..com
 */
public class BrandAdapter extends BaseQuickAdapter<HomeImage, BaseViewHolder> {
    public BrandAdapter(int layoutResId, @Nullable List<HomeImage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeImage item) {
        ImageView iv_logo= (ImageView) helper.getView(R.id.iv_logo);
        Glide.with(mContext).load(item.getImage()).into(iv_logo);
    }
}
