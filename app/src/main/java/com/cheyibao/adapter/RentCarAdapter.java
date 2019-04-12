package com.cheyibao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.RentCar;
import com.example.yunchebao.R;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentCarAdapter extends BaseQuickAdapter<RentCar,RentCarAdapter.ViewHolder> {

    public RentCarAdapter(@Nullable List<RentCar> data) {
        super(R.layout.item_rentcar, data);
    }

    @Override
    protected void convert(ViewHolder helper, RentCar item) {
        helper.tvAddress.setText(item.getAddress());
        helper.tvName.setText(item.getName());
        DecimalFormat decimalFormat=new DecimalFormat("0.00");
        helper.tvDis.setText(String.format("%skm",decimalFormat.format(item.getDistance())));
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_dis)
        TextView tvDis;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
