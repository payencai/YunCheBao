package com.system.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caryibao.NewCar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.system.model.WashRepair;
import com.tool.MathUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class SearchNewAdapter extends BaseQuickAdapter<NewCar,BaseViewHolder> {
    public SearchNewAdapter(int layoutResId, @Nullable List<NewCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCar item) {
        ImageView iv_logo=helper.getView(R.id.img);

        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_price=helper.getView(R.id.tv_price);

        String imgs=item.getCarCategoryDetail().getBanner1();
        if(!TextUtils.isEmpty(imgs)){
            String images[]=imgs.split(",");
            Glide.with(helper.itemView.getContext()).load(images[0]).into(iv_logo);
        }

        tv_name.setText(item.getFirstName()+" "+item.getSecondName()+" "+item.getThirdName());
        tv_price.setText("￥"+item.getMinPrice());

    }
}
