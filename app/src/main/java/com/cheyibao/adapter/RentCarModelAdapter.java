package com.cheyibao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.RentCarModel;
import com.example.yunchebao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarModelAdapter extends BaseQuickAdapter<RentCarModel,RentCarModelAdapter.ViewHolder> {

    public RentCarModelAdapter(@Nullable List<RentCarModel> data) {
        super(R.layout.item_rent_car_model, data);
    }

    @Override
    protected void convert(ViewHolder helper, RentCarModel item) {
        Glide.with(helper.itemView).load(item.getImage()).into(helper.carBannerView);
        helper.carModelsView.setText(item.getCarTategory());
        helper.seatView.setText(String.format("%s/%s",item.getVariableBox(),item.getSeat()));
        helper.dayPriceView.setText(String.format("￥%s",item.getDayPrice()));
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.car_banner_view)
        ImageView carBannerView;
        @BindView(R.id.car_models_view)
        TextView carModelsView;
        @BindView(R.id.seat_view)
        TextView seatView;
        @BindView(R.id.day_price_view)
        TextView dayPriceView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
