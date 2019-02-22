package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.Mypay;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/20 11:55
 * 邮箱：771548229@qq..com
 */
public class MyPayAdapter extends BaseQuickAdapter<Mypay,BaseViewHolder> {
    public MyPayAdapter(int layoutResId, @Nullable List<Mypay> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Mypay item) {
        TextView tv_method=helper.getView(R.id.tv_method);
        TextView tv_time=helper.getView(R.id.tv_time);
        TextView tv_price=helper.getView(R.id.tv_price);
        tv_method.setText(item.getPayMethod());
        tv_time.setText(item.getCreateTime().substring(0,10));
        tv_price.setText("-"+item.getPrice());
    }
}
