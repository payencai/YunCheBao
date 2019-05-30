package com.cheyibao.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.HighCarCategory;
import com.example.yunchebao.R;
import com.payencai.library.mediapicker.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighCarCategoryAdapter extends BaseQuickAdapter<HighCarCategory, HighCarCategoryAdapter.ViewHolder> {
    public HighCarCategoryAdapter(@Nullable List<HighCarCategory> data) {
        super(R.layout.item_high_car_ategory, data);
    }

    @Override
    protected void convert(HighCarCategoryAdapter.ViewHolder helper, HighCarCategory item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth(mContext)-ScreenUtils.dp2px(mContext,30), ScreenUtils.getScreenHeight(mContext) / 4);
        params.gravity = Gravity.CENTER;
        params.setMarginStart(ScreenUtils.dp2px(mContext,15));
        params.setMarginEnd(ScreenUtils.dp2px(mContext,15));
        params.topMargin = ScreenUtils.dp2px(mContext,10);
        helper.itemView.setLayoutParams(params);
        Glide.with(helper.itemView).load(item.getImage()).into((ImageView) helper.getView(R.id.banner_view));
        helper.setText(R.id.category_name_view, item.getName());
    }

    static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.banner_view)
        ImageView bannerView;
        @BindView(R.id.category_name_view)
        TextView categoryNameView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(GradientDrawable.RECTANGLE);
            int corner = ScreenUtils.dp2px(MyApplication.getContext(),4);
            gradientDrawable.setCornerRadii(new float[]{0,0,0,0,corner,corner,corner,corner});
            gradientDrawable.setColor(0x88000000);
            categoryNameView.setBackground(gradientDrawable);
        }


    }
}
