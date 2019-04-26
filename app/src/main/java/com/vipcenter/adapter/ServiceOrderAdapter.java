package com.vipcenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vipcenter.model.ServiceOrder;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/23 16:56
 * 邮箱：771548229@qq..com
 */
public class ServiceOrderAdapter extends BaseQuickAdapter<ServiceOrder, BaseViewHolder> {
    public ServiceOrderAdapter(int layoutResId, @Nullable List<ServiceOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceOrder item) {

    }
}
