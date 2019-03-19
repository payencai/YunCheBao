package com.vipcenter.adapter;

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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.GiftRecord;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 礼品汇兑换记录列表适配器
 */
public class RecordListAdapter extends BaseQuickAdapter<GiftRecord, BaseViewHolder> {


    public RecordListAdapter(int layoutResId, @Nullable List<GiftRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftRecord item) {
        TextView tv_coin=helper.getView(R.id.tv_coin);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_num=helper.getView(R.id.tv_num);
        TextView tv_time=helper.getView(R.id.tv_time);
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        tv_coin.setText("宝币："+item.getCommodityCoinCount());
        tv_name.setText(item.getCommodityName());
        tv_time.setText(item.getCreateTime().substring(0,10));
        tv_num.setText("件数："+item.getCommodityNumber());
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
