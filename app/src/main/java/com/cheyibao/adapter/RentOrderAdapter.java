package com.cheyibao.adapter;

import android.text.TextUtils;
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

public class RentOrderAdapter extends BaseQuickAdapter<RentOrder, BaseViewHolder> {

    public RentOrderAdapter(List<RentOrder> rentOrderList) {
        super(R.layout.item_service_order, rentOrderList);
    }

    @Override
    protected void convert(BaseViewHolder helper, RentOrder item) {
        helper.addOnClickListener(R.id.tv_job);
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_status = helper.getView(R.id.tv_status);
        TextView tv_total = helper.getView(R.id.tv_total);
        TextView tv_date = helper.getView(R.id.tv_date);
        TextView tv_job = helper.getView(R.id.tv_job);
        tv_name.setText(item.getBrand()+item.getCarTategory());
        tv_total.setText("价格：" + item.getTotal());
        tv_date.setText(item.getCreateTime().substring(0, 10));
        Glide.with(helper.itemView.getContext()).load(item.getImage()).into(iv_logo);
        if (item.getState() == 3) {
            tv_status.setText("待评价");
            tv_job.setText("评价");
        } else if (item.getState() == 4) {
            tv_status.setText("已完成");
            tv_job.setText("查看评价");
        } else if (item.getState() == 2) {
            tv_status.setText("服务中");
            tv_job.setText("取消");
        }

    }

}
