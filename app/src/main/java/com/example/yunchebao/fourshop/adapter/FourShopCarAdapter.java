package com.example.yunchebao.fourshop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.FourShopCar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/17 17:05
 * 邮箱：771548229@qq..com
 */
public class FourShopCarAdapter extends BaseQuickAdapter<FourShopCar, BaseViewHolder> {
    public FourShopCarAdapter(int layoutResId, @Nullable List<FourShopCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FourShopCar item) {
        ImageView iv_img=helper.getView(R.id.iv_img);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_price=helper.getView(R.id.tv_price);
        tv_price.setText((item.getNakedCarPrice()/10000)+"万");
        tv_name.setText(item.getFirstName()+item.getSecondName()+item.getThirdName());
        Glide.with(helper.itemView.getContext()).load(item.getCarCategoryDetail().getBanner1()).into(iv_img);

    }
}
