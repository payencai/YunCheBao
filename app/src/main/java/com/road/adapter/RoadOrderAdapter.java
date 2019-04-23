package com.road.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.road.model.RoadOrder;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/22 17:48
 * 邮箱：771548229@qq..com
 */
public class RoadOrderAdapter  extends BaseQuickAdapter<RoadOrder, BaseViewHolder> {
    public RoadOrderAdapter(int layoutResId, @Nullable List<RoadOrder> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RoadOrder item) {

    }
}
