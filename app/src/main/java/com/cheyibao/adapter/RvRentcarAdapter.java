package com.cheyibao.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.RentCarType;
import com.cheyibao.model.ShopComment;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/17 14:26
 * 邮箱：771548229@qq..com
 */
public class RvRentcarAdapter extends BaseQuickAdapter<RentCarType, BaseViewHolder> {
    public RvRentcarAdapter(int layoutResId, @Nullable List<RentCarType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RentCarType item) {
        ImageView iv_car = (ImageView) helper.getView(R.id.iv_car);
        TextView tv_name1 = (TextView) helper.getView(R.id.tv_name1);
        TextView tv_name2 = (TextView) helper.getView(R.id.tv_name2);
        TextView tv_price = (TextView) helper.getView(R.id.tv_price);
        TextView tv_param = (TextView) helper.getView(R.id.tv_param);
        if (item != null) {
            Glide.with(mContext).load(item.getPhoto()).into(iv_car);
            tv_name1.setText(item.getBrand());
            tv_name2.setText(item.getModel());
            tv_price.setText("￥" + item.getDayPrice());
            tv_param.setText(item.getManualAutomatic() + "/" + item.getSeat() + "座");
        }
    }
}
