package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.vipcenter.model.Gift;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/19 18:10
 * 邮箱：771548229@qq..com
 */
public class GiftMoreAdapter extends BaseQuickAdapter<Gift,BaseViewHolder> {
    public GiftMoreAdapter(int layoutResId, @Nullable List<Gift> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Gift item) {
        TextView tv_coin=helper.getView(R.id.tv_coin);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_num=helper.getView(R.id.tv_num);
        TextView tv_time=helper.getView(R.id.tv_time);
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        tv_coin.setText("宝币："+item.getCoinCount());
        tv_name.setText(item.getCommodityName());
        tv_time.setText(item.getCreateTime().substring(0,10));
        tv_num.setText("件数："+item.getCommodityNum());
        String images = item.getCommodityImage();
        if (!TextUtils.isEmpty(images)) {
            if (images.contains(","))
                Glide.with(helper.itemView.getContext()).load(images.split(",")[0]).into(iv_logo);
            else{
                Glide.with(helper.itemView.getContext()).load(images).into(iv_logo);
            }
        }

    }
}
