package com.vipcenter.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.GoodsCollect;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 全部收藏商品列表适配器
 */
public class GoodCollectAdapter extends BaseQuickAdapter<GoodsCollect,BaseViewHolder> {
    public GoodCollectAdapter(int layoutResId, @Nullable List<GoodsCollect> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCollect item) {
        ImageView iv_logo= (ImageView) helper.getView(R.id.iv_logo);
        TextView tv_name= (TextView) helper.getView(R.id.tv_name);
        TextView tv_sale= (TextView) helper.getView(R.id.tv_sale);
        TextView tv_new = (TextView) helper.getView(R.id.tv_newprice);
        TextView tv_old = (TextView) helper.getView(R.id.tv_oldprice);
        String urls=item.getCommodityImage();
        if(urls.contains(",")){
            String []images=item.getCommodityImage().split(",");
            Glide.with(helper.itemView.getContext()).load(images[0]).into(iv_logo);
        }else{
            Glide.with(helper.itemView.getContext()).load(urls).into(iv_logo);
        }
        tv_sale.setVisibility(View.GONE);
        tv_name.setText(item.getCommodityName());
        tv_new.setText("￥"+item.getDiscountPrice());
        tv_old.setText("￥"+item.getOriginalPrice());
        tv_old.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
    }




}
