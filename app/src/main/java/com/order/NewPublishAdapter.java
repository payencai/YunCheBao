package com.order;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/28 16:43
 * 邮箱：771548229@qq..com
 */
public class NewPublishAdapter extends BaseQuickAdapter<NewPublish,BaseViewHolder> {
    public NewPublishAdapter(int layoutResId, @Nullable List<NewPublish> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewPublish item) {
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_time=helper.getView(R.id.tv_time);
        TextView tv_state=helper.getView(R.id.tv_state);
        TextView tv_cancel=helper.getView(R.id.tv_cancel);
        TextView tv_price=helper.getView(R.id.tv_price);
        ImageView iv_car=helper.getView(R.id.iv_car);
        tv_name.setText(item.getFirstName()+item.getSecondName()+item.getFirstName());
        tv_price.setText("￥"+item.getNewPrice());
        tv_time.setText(item.getCreateTime().substring(0,10));
        if(item.getCarImage().contains(",")){
            String [] images=item.getCarImage().split(",");
            Glide.with(mContext).load(images[0]).into(iv_car);
        }else{
            Glide.with(mContext).load(item.getCarImage()).into(iv_car);
        }

        if(item.getState()==2){
            tv_state.setText("已完成");
            tv_cancel.setVisibility(View.GONE);

        }

    }
}
