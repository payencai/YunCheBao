package com.cheyibao.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.RentCarModel;
import com.example.yunchebao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarModelAdapter extends BaseQuickAdapter<RentCarModel, RentCarModelAdapter.ViewHolder> {
    private boolean isDisplayPrice = true;

    public RentCarModelAdapter(@Nullable List<RentCarModel> data) {
        super(R.layout.item_rent_car_model, data);
    }

    public void setDisplayPrice(boolean isDisplayPrice) {
        this.isDisplayPrice = isDisplayPrice;
    }

    @Override
    protected void convert(ViewHolder helper, RentCarModel item) {
        Glide.with(helper.carBannerView).load(item.getImage()).into(helper.carBannerView);
        helper.carBrandView.setText(item.getBrand());
        helper.carModelsView.setText(item.getCarTategory());
        String seat = TextUtils.isEmpty(item.getSeat())?"":item.getSeat().replace("座", "");
        helper.seatView.setText(String.format("%s/%s座", item.getVariableBox(), seat));
        if (isDisplayPrice) {
            helper.priceParentView.setVisibility(View.VISIBLE);
        } else {
            helper.priceParentView.setVisibility(View.GONE);
        }
        helper.dayPriceView.setText(String.format("￥%s", item.getDayPrice()));
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.car_banner_view)
        ImageView carBannerView;
        @BindView(R.id.car_brand_view)
        TextView carBrandView;
        @BindView(R.id.car_models_view)
        TextView carModelsView;
        @BindView(R.id.seat_view)
        TextView seatView;
        @BindView(R.id.day_price_view)
        TextView dayPriceView;
        @BindView(R.id.price_parent_view)
        LinearLayout priceParentView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
