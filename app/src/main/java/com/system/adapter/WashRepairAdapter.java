package com.system.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.MathUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class WashRepairAdapter extends BaseQuickAdapter<CarShop,BaseViewHolder> {
    public WashRepairAdapter(int layoutResId, @Nullable List<CarShop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarShop item) {
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        SimpleRatingBar sb_score=helper.getView(R.id.sb_score);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_price=helper.getView(R.id.tv_price);
        TextView tv_num=helper.getView(R.id.tv_num);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
        sb_score.setRating((float) item.getScore());
        tv_name.setText(item.getShopName());
        tv_price.setText("￥"+item.getPrice());
        tv_num.setText("评分" + item.getGrade() + "|订单" + item.getOrderNum());
        tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance())+"km");
    }
}
