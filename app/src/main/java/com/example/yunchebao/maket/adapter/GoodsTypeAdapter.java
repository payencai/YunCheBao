package com.example.yunchebao.maket.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.model.GoodList;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class GoodsTypeAdapter extends BaseQuickAdapter<GoodList,BaseViewHolder> {
    public GoodsTypeAdapter(int layoutResId, @Nullable List<GoodList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodList item) {
        ImageView iv_logo= (ImageView)helper.getView(R.id.iv_logo);
        TextView tv_name= (TextView) helper.getView(R.id.tv_name);
        TextView tv_new = (TextView) helper.getView(R.id.tv_newprice);
        TextView tv_old = (TextView) helper.getView(R.id.tv_oldprice);
        TextView tv_sale = (TextView) helper.getView(R.id.tv_sale);
        String urls=item.getCommodityImage();
        if(urls.contains(",")){
            String []images=item.getCommodityImage().split(",");
            Glide.with(helper.itemView.getContext()).load(images[0]).into(iv_logo);
        }else{
            Glide.with(helper.itemView.getContext()).load(urls).into(iv_logo);
        }
        tv_sale.setText("销量："+item.getNumber());
        tv_name.setText(item.getName());
        tv_new.setText("￥"+item.getDiscountPrice());
        tv_old.setText("￥"+item.getOriginalPrice());
        tv_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
    }
}
