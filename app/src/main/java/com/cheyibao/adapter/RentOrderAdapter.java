package com.cheyibao.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.RentOrder;
import com.example.yunchebao.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentOrderAdapter extends BaseQuickAdapter<RentOrder, RentOrderAdapter.ViewHolder> {

    public RentOrderAdapter(List<RentOrder> rentOrderList) {
        super(R.layout.item_rent_order, rentOrderList);
    }

    @Override
    protected void convert(ViewHolder helper, RentOrder item) {
        Glide.with(helper.carBannerView).load(item.getImage()).into(helper.carBannerView);
        helper.carBrandView.setText(item.getBrand());
        helper.carModelsView.setText(item.getCarTategory());
        helper.seatView.setText(String.format("%s/%s", item.getVariableBox(), item.getSeat()));
        helper.dayPriceView.setText(String.format("￥%s", item.getDayPrice()));
        String text;
        switch (item.getState()){
            case 0:
                text = "已取消";
                break;
            case 1:
                text = "未付款";
                break;
            case 2:
                text = "服务中";
                break;
            case 3:
                text = "待评论";
                break;
            case 4:
                text = ".已完成";
                break;
                default:
                    text = "未知状态";
        }
        helper.statusView.setText(text);
        helper.addOnClickListener(R.id.cancel_order_click).addOnClickListener(R.id.comment_click);
        if (item.getState() == 2){
            helper.cancelOrderClick.setVisibility(View.VISIBLE);
            helper.commentClick.setVisibility(View.GONE);
        }else if (item.getState()==3){
            helper.cancelOrderClick.setVisibility(View.GONE);
            helper.commentClick.setVisibility(View.VISIBLE);
        }else {
            helper.cancelOrderClick.setVisibility(View.GONE);
            helper.commentClick.setVisibility(View.GONE);
            ((View)helper.commentClick.getParent()).setVisibility(View.GONE);
        }
        helper.x3.setText(String.format("x%s",item.getRentDay()));
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.car_banner_view)
        ImageView carBannerView;
        @BindView(R.id.car_brand_view)
        TextView carBrandView;
        @BindView(R.id.status_view)
        TextView statusView;
        @BindView(R.id.car_models_view)
        TextView carModelsView;
        @BindView(R.id.seat_view)
        TextView seatView;
        @BindView(R.id.label)
        TextView label;
        @BindView(R.id.day_price_view)
        TextView dayPriceView;
        @BindView(R.id.x3)
        TextView x3;
        @BindView(R.id.price_parent_view)
        RelativeLayout priceParentView;
        @BindView(R.id.comment_click)
        TextView commentClick;
        @BindView(R.id.cancel_order_click)
        TextView cancelOrderClick;
        @BindView(R.id.divider)
        View divider;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
