package com.cheyibao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.LongRentShop;
import com.example.yunchebao.R;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LongRentShopAdapter extends BaseQuickAdapter<LongRentShop, LongRentShopAdapter.ViewHolder> {


    public LongRentShopAdapter(@Nullable List<LongRentShop> data) {
        super(R.layout.item_long_rent_shop, data);
    }

    @Override
    protected void convert(ViewHolder helper, LongRentShop item) {
        helper.shopNameView.setText(item.getName());
        helper.dayPriceView.setText(String.format("￥%s",item.getDayPrice()));
        helper.addressView.setText(item.getAddress());
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        helper.distanceView.setText(String.format("%skm",decimalFormat.format(item.getDistance())));
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_name_view)
        TextView shopNameView;
        @BindView(R.id.day_price_view)
        TextView dayPriceView;
        @BindView(R.id.address_view)
        TextView addressView;
        @BindView(R.id.distance_view)
        TextView distanceView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
