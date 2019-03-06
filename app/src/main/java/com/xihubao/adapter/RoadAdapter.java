package com.xihubao.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.tool.MathUtil;
import com.xihubao.model.Road;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class RoadAdapter extends BaseQuickAdapter<Road,BaseViewHolder> {
    public RoadAdapter(int layoutResId, @Nullable List<Road> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Road item) {
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_detail=helper.getView(R.id.tv_detail);
        TextView tv_content=helper.getView(R.id.tv_content);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
        tv_content.setText(item.getServe());
        tv_name.setText(item.getShopName());
        tv_detail.setText(item.getRemark());
        tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance())+"km");
    }
}
