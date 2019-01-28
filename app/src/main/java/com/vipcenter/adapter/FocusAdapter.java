package com.vipcenter.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vipcenter.model.MyFocus;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/28 19:30
 * 邮箱：771548229@qq..com
 */
public class FocusAdapter extends BaseQuickAdapter<MyFocus,BaseViewHolder> {
    public FocusAdapter(int layoutResId, @Nullable List<MyFocus> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFocus item) {

    }
}
