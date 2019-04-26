package com.order;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/28 14:25
 * 邮箱：771548229@qq..com
 */
public class CarOrderAdapter extends BaseQuickAdapter<CarOrder, BaseViewHolder> {
    public CarOrderAdapter(int layoutResId, @Nullable List<CarOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarOrder item) {
        helper.addOnClickListener(R.id.tv_job);
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_status=helper.getView(R.id.tv_status);
        TextView tv_total=helper.getView(R.id.tv_total);
        TextView tv_date=helper.getView(R.id.tv_date);
        TextView tv_job=helper.getView(R.id.tv_job);
        tv_name.setText(item.getShopName());
        tv_total.setText("价格："+item.getTotal());
        tv_date.setText(item.getCreateTime().substring(0,10));
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
        if(item.getType()==5){
            if(item.getState()==2){
                tv_status.setText("待评价");
                tv_job.setText("评价");
            }else if(item.getState()==3){
                tv_status.setText("已完成");
                tv_job.setText("查看评价");
            }
        }else{
            if(item.getState()==3){
                tv_status.setText("待评价");
                tv_job.setText("评价");
            }else if(item.getState()==4){
                tv_status.setText("已完成");
                tv_job.setText("查看评价");
            }else if(item.getState()==2){
                tv_status.setText("服务中");
                tv_job.setText("取消");
            }else if(item.getState()==1){
                tv_status.setText("待付款");
                tv_job.setText("付款");
            }
        }


    }
}
