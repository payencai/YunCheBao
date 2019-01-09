package com.vipcenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vipcenter.model.GiftCoin;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/9 15:42
 * 邮箱：771548229@qq..com
 */
public class GiftMoneyAdapter extends BaseQuickAdapter<GiftCoin,BaseViewHolder>{
    public GiftMoneyAdapter(int layoutResId, @Nullable List<GiftCoin> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftCoin item) {

    }
}
