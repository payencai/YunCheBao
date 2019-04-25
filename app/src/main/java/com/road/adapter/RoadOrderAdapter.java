package com.road.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.road.model.RoadOrder;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/22 17:48
 * 邮箱：771548229@qq..com
 */
public class RoadOrderAdapter  extends BaseQuickAdapter<RoadOrder, BaseViewHolder> {
    public RoadOrderAdapter(int layoutResId, @Nullable List<RoadOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoadOrder item) {
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
        tv_name.setText(item.getName());
        tv_time.setText("发布时间: "+item.getCreateTime());
        String imgs=item.getImgs();
        Glide.with(helper.itemView.getContext()).load(imgs).into(iv_logo);
    }
}
