package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.drive.model.ReplaceOrder;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/26 11:15
 * 邮箱：771548229@qq..com
 */
public class DriveOrderAdapter extends BaseQuickAdapter<ReplaceOrder,BaseViewHolder> {
    public DriveOrderAdapter(int layoutResId, @Nullable List<ReplaceOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplaceOrder item) {
        helper.addOnClickListener(R.id.fukuan);
        helper.addOnClickListener(R.id.delete);
        if(item.getState()>=2){
            helper.getView(R.id.fukuan).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.fukuan).setVisibility(View.VISIBLE);
        }
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_name.setText(item.getShopName());
        tv_time.setText("发布时间: "+item.getCreateTime());
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
    }
}
