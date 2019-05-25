package com.example.yunchebao.road.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.example.yunchebao.road.model.RoadOrder;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/22 17:48
 * 邮箱：771548229@qq..com
 */
public class RoadOrderAdapter extends BaseQuickAdapter<RoadOrder, BaseViewHolder> {
    public RoadOrderAdapter(int layoutResId, @Nullable List<RoadOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoadOrder item) {
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_time = helper.getView(R.id.tv_time);
        SuperTextView sp_pay = helper.getView(R.id.fukuan);
        helper.addOnClickListener(R.id.fukuan);
        helper.addOnClickListener(R.id.delete);
        if (item.getState() == 1) {
            sp_pay.setText("付款");
            //未付款
        } else {
            if (item.getIsComment() == 0) {
                sp_pay.setText("评价");
            } else {
                sp_pay.setText("查看评价");
            }
        }

        tv_name.setText(item.getShopName());
        tv_time.setText("发布时间: " + item.getCreateTime());

        Glide.with(mContext).load(item.getLogo()).into(iv_logo);
    }
}
