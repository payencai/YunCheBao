package com.example.yunchebao.yuedan.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.model.YueOrder;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/26 11:15
 * 邮箱：771548229@qq..com
 */
public class YuedanAdapter extends BaseQuickAdapter<YueOrder,BaseViewHolder> {
    public YuedanAdapter(int layoutResId, @Nullable List<YueOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YueOrder item) {
        helper.addOnClickListener(R.id.fukuan);
        helper.addOnClickListener(R.id.delete);
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        SuperTextView fukuan=helper.getView(R.id.fukuan);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_name.setText(item.getShopName());
        tv_time.setText("发布时间: "+item.getCreateTime());
        if(item.getState()==2){
            fukuan.setVisibility(View.GONE);
        }
        Glide.with(helper.itemView.getContext()).load(item.getShopImg()).into(iv_logo);
    }
}
