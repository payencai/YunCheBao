package com.order;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.example.yunchebao.R;
import com.tool.MathUtil;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/28 16:43
 * 邮箱：771548229@qq..com
 */
public class NewPublishAdapter extends BaseQuickAdapter<NewPublish, BaseViewHolder> {
    public NewPublishAdapter(int layoutResId, @Nullable List<NewPublish> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewPublish item) {
        LoaderTextView tv_name = helper.getView(R.id.tv_name);
        LoaderTextView tv_time = helper.getView(R.id.tv_time);
        LoaderTextView tv_state = helper.getView(R.id.tv_state);
        LoaderTextView tv_cancel = helper.getView(R.id.tv_cancel);
        helper.addOnClickListener(R.id.tv_cancel);
        LoaderTextView tv_price = helper.getView(R.id.tv_price);
        ImageView iv_car = helper.getView(R.id.iv_car);
        String name = item.getFirstName() + " " + item.getSecondName() + " " + item.getThirdName();
        name = name.replace("null", "");
        tv_name.setText(name);
        if (item.getOldPrice() > 10000)
            tv_price.setText(MathUtil.getDoubleTwo((item.getOldPrice()/10000))+ "万");
        else {
            tv_price.setText(MathUtil.getDoubleTwo(item.getOldPrice()) + "元");
        }
        tv_time.setText(item.getCreateTime().substring(0, 10));
        String imgs="";
        String banner1=item.getCarCategoryDetail().getBanner1();
        String banner2=item.getCarCategoryDetail().getBanner2();
        String banner3=item.getCarCategoryDetail().getBanner3();
        if(!TextUtils.isEmpty(banner1)){
            if (banner1.contains(",")) {
                String[] images = banner1.split(",");
                imgs=images[0];
            } else {
               imgs=banner1;
            }
            Glide.with(mContext).load(imgs).into(iv_car);
        }else{
            Glide.with(mContext).load(banner2).into(iv_car);
        }

         if(item.getAudit()==3){
             tv_state.setText("已拒绝");
             tv_cancel.setVisibility(View.GONE);
         }
         else if (item.getAudit() == 2) {
             if(item.getState()==1){
                 tv_state.setText("售卖中");
                 tv_state.setVisibility(View.VISIBLE);
             }else{
                 tv_state.setText("已完成");
                 tv_cancel.setVisibility(View.GONE);
             }

        }

    }
}
