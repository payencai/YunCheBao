package com.xihubao.adapter;

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
 * 作者：凌涛 on 2019/4/17 17:05
 * 邮箱：771548229@qq..com
 */
public class WashRepairAdapter extends BaseQuickAdapter<CarShop, BaseViewHolder> {
    public WashRepairAdapter(int layoutResId, @Nullable List<CarShop> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarShop item) {
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_price=helper.getView(R.id.tv_price);
        SimpleRatingBar sb_score=helper.getView(R.id.sb_score);
        TextView tv_num=helper.getView(R.id.tv_num);
        TextView tv_addr=helper.getView(R.id.tv_addr);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        tv_name.setText(item.getShopName());
        int money= (int) item.getPrice();
        tv_price.setText("￥"+money);
        tv_addr.setText(item.getProvince()+item.getCity()+item.getArea()+item.getAddress());
        tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance())+"km");
        sb_score.setRating((float) item.getScore());
        tv_num.setText("评价"+item.getNumber()+"|"+"订单"+item.getOrderNum());
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
    }
}
