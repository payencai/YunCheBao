package com.example.yunchebao.gasstation.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.example.yunchebao.R;
import com.example.yunchebao.gasstation.model.StationService;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/23 10:23
 * 邮箱：771548229@qq..com
 */
public class StationServiceAdapter extends BaseQuickAdapter<StationService, BaseViewHolder> {
    public StationServiceAdapter(int layoutResId, @Nullable List<StationService> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StationService item) {
        TextView goodsCategoryName= (TextView) helper.getView(R.id.goodsCategoryName);
        TextView price= (SuperTextView) helper.getView(R.id.price);
        TextView tvGoodsDescription= (TextView) helper.getView(R.id.tvGoodsDescription);
        goodsCategoryName.setText(item.getTitle());
        price.setText("￥"+item.getPrice());
        tvGoodsDescription.setText(item.getContent());
    }
}
