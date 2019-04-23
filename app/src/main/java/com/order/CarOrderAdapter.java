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

        LinearLayout ll_type1 = helper.getView(R.id.ll_type1);
        LinearLayout ll_type2 = helper.getView(R.id.ll_type2);
        LinearLayout ll_type3 = helper.getView(R.id.ll_type3);
        LinearLayout ll_type4 = helper.getView(R.id.ll_type4);
        if (item.getFlag() == 1) {
            ll_type1.setVisibility(View.VISIBLE);
            ll_type2.setVisibility(View.GONE);
            ll_type3.setVisibility(View.GONE);
            ll_type4.setVisibility(View.GONE);
            TextView tv_state = helper.getView(R.id.tv_washstate);
            TextView tv_name1 = helper.getView(R.id.tv_name1);
            TextView tv_time1 = helper.getView(R.id.tv_time1);
            TextView tv_washtype = helper.getView(R.id.tv_washtype);
            TextView tv_content = helper.getView(R.id.tv_content);
            TextView tv_price1 = helper.getView(R.id.tv_price1);
            TextView btn_comment = helper.getView(R.id.btn_comment);
            helper.addOnClickListener(R.id.btn_comment);
            tv_content.setText(item.getServeCategory());
            tv_name1.setText(item.getShopName());
            tv_washtype.setText(item.getServeTitle());
            tv_price1.setText("总价：￥" + item.getPrice());
            tv_time1.setText(item.getCreateTime().substring(0, 10));
            if (item.getState() == 2) {
                tv_state.setText("服务中");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("取消订单");
            } else if (item.getState() == 3) {
                tv_state.setText("待评价");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("评价");
            } else if (item.getState() == 4) {
                tv_state.setText("已完成");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("查看评论");

            } else if (item.getState() == 0) {
                tv_state.setText("已取消");
                btn_comment.setVisibility(View.GONE);
            } else {

            }

        } else if (item.getFlag() == 2) {
            ll_type3.setVisibility(View.GONE);
            ll_type2.setVisibility(View.VISIBLE);
            ll_type1.setVisibility(View.GONE);
            ll_type4.setVisibility(View.GONE);
            TextView tv_state = helper.getView(R.id.tv_carstate);
            TextView tv_note = helper.getView(R.id.tv_note);
            TextView tv_cancel = helper.getView(R.id.tv_cancel);
            helper.addOnClickListener(R.id.tv_cancel);
            ImageView iv_car = helper.getView(R.id.iv_car);
            if (item.getState() == 2) {

                tv_state.setText("服务中");
                tv_note.setVisibility(View.GONE);
                tv_cancel.setText("取消订单");
            }
            if (item.getState() == 3) {
                tv_note.setVisibility(View.GONE);
                tv_state.setText("待评价");
                tv_cancel.setText("评价");
            }
            if (item.getState() == 4) {
                tv_state.setText("已完成");
                tv_note.setVisibility(View.VISIBLE);
                tv_cancel.setText("查看评价");
            }
            TextView tv_date = helper.getView(R.id.tv_date);
            TextView tv_name = helper.getView(R.id.tv_name);
            TextView tv_auto = helper.getView(R.id.tv_auto);
            TextView tv_avgprice = helper.getView(R.id.tv_avgprice);
            TextView tv_carname = helper.getView(R.id.tv_name2);
            TextView tv_time = helper.getView(R.id.tv_time);
            LinearLayout ll_time = helper.getView(R.id.ll_time);
            Glide.with(mContext).load(item.getImage()).into(iv_car);

            if (item.getType() == 3) {
                //租车

                tv_carname.setText(item.getCarCategory());
                tv_auto.setText(item.getSeat() + "座");
                tv_avgprice.setText("￥" + item.getPrice());
                tv_date.setText("x" + item.getNumber());
                tv_name.setText("日均 ");
            } else if (item.getType() == 4) {
                //驾校
                tv_carname.setText(item.getShopName() + " " + item.getClassName());
                ll_time.setVisibility(View.VISIBLE);
                tv_auto.setVisibility(View.GONE);
                tv_date.setVisibility(View.GONE);
                tv_time.setText(item.getCreateTime().substring(0, 10));
                tv_avgprice.setText("￥" + item.getPrice());
                tv_name.setText("价格 ");
            }
        } else if(item.getFlag()==3){
            helper.addOnClickListener(R.id.tv_pjia);
            ll_type3.setVisibility(View.VISIBLE);
            ll_type2.setVisibility(View.GONE);
            ll_type1.setVisibility(View.GONE);
            ll_type4.setVisibility(View.GONE);
            TextView tv_driverstate = helper.getView(R.id.tv_driverstate);
            TextView tv_name3 = helper.getView(R.id.tv_name3);
            ImageView iv_logo=helper.getView(R.id.iv_logo);
            TextView tv_paytime = helper.getView(R.id.tv_paytime);
            TextView tv_driverMoney = helper.getView(R.id.tv_driverMoney);
            TextView tv_pjia = helper.getView(R.id.tv_pjia);
            tv_name3.setText(item.getShopName());
            Glide.with(mContext).load(item.getShopLogo()).into(iv_logo);
            tv_paytime.setText("付款时间："+item.getCreateTime());
            tv_driverMoney.setText("￥"+item.getPrice());
            if (item.getState() == 3) {
                tv_driverstate.setText("已完成");
                tv_pjia.setText("查看评价");
            } else {
                tv_driverstate.setText("待评价");
                tv_pjia.setText("评价");
            }
        }else if(item.getFlag()==4){
            ll_type3.setVisibility(View.GONE);
            ll_type2.setVisibility(View.GONE);
            ll_type1.setVisibility(View.GONE);
            ll_type4.setVisibility(View.VISIBLE);
            TextView tv_state = helper.getView(R.id.tv_fourstate);
            TextView tv_name1 = helper.getView(R.id.tv_name4);
            TextView tv_time1 = helper.getView(R.id.tv_time4);
            TextView tv_washtype = helper.getView(R.id.tv_fourtype);
            TextView tv_content = helper.getView(R.id.tv_detail);
            TextView tv_price1 = helper.getView(R.id.tv_price4);
            TextView btn_comment = helper.getView(R.id.btn_comment4);
            helper.addOnClickListener(R.id.btn_comment4);
            tv_content.setText(item.getServeTitle());
            tv_name1.setText(item.getShopName());
            tv_washtype.setText(item.getServeCategory());
            tv_price1.setText("总价：￥" + item.getPrice());
            tv_time1.setText(item.getCreateTime().substring(0, 10));
            if (item.getState() == 2) {
                tv_state.setText("服务中");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("取消订单");
            } else if (item.getState() == 3) {
                tv_state.setText("待评价");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("评价");
            } else if (item.getState() == 4) {
                tv_state.setText("已完成");
                btn_comment.setVisibility(View.VISIBLE);
                btn_comment.setText("查看评论");

            } else if (item.getState() == 0) {
                tv_state.setText("已取消");
                btn_comment.setVisibility(View.GONE);
                btn_comment.setText("评价");
            } else {

            }
        }
    }
}
