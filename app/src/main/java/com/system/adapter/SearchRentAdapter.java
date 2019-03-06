package com.system.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.NewCar;
import com.cheyibao.model.RentCar;
import com.example.yunchebao.R;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class SearchRentAdapter extends BaseQuickAdapter<RentCar, BaseViewHolder> {
    public SearchRentAdapter(int layoutResId, @Nullable List<RentCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RentCar item) {

        TextView tv_address = (TextView) helper.getView(R.id.tv_address);
        TextView tv_name = (TextView) helper.getView(R.id.tv_name);
        TextView tv_dis = (TextView) helper.getView(R.id.tv_dis);
        tv_name.setText(item.getName());
        tv_address.setText(item.getAddress());
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        tv_dis.setText(decimalFormat.format(item.getDistance()) + "km");
    }

}
