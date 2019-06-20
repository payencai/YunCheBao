package com.example.yunchebao.maket.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.maket.model.GoodList;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/16 10:47
 * 邮箱：771548229@qq..com
 */
public class GoodsAdapter extends BaseQuickAdapter<GoodList,BaseViewHolder>{

    public GoodsAdapter(int layoutResId, @Nullable List<GoodList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodList item) {

    }
}
