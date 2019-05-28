package com.example.yunchebao.rongcloud.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;
import com.example.yunchebao.rongcloud.model.Nearby;
import com.tool.MathUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/20 11:04
 * 邮箱：771548229@qq..com
 */
public class NearByAdapter extends BaseQuickAdapter<Nearby, BaseViewHolder> {
    public NearByAdapter(int layoutResId, @Nullable List<Nearby> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Nearby item) {
        helper.setIsRecyclable(false);
        CircleImageView iv_head = helper.getView(R.id.iv_head);
        ImageView iv_sex = helper.getView(R.id.iv_sex);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_dis = helper.getView(R.id.tv_dis);
        Glide.with(helper.itemView.getContext()).load(item.getHeadPortrait()).into(iv_head);
        tv_name.setText(item.getName());
        if (item.getDistance() < 1) {
            tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance() * 1000) + "m");
        } else {
            tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance()) + "km");
        }

        if ("男".equals(item.getSex())) {
            iv_sex.setImageResource(R.mipmap.ic_man);
            iv_sex.setVisibility(View.VISIBLE);
        } else if ("女". equals(item.getSex())) {
            iv_sex.setImageResource(R.mipmap.ic_women);
            iv_sex.setVisibility(View.VISIBLE);
        } else {
            iv_sex.setVisibility(View.GONE);
        }
    }
}
