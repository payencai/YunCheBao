package com.newversion;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/29 15:52
 * 邮箱：771548229@qq..com
 */
public class LabelUserAdapter extends BaseQuickAdapter<ContactModel,BaseViewHolder> {
    public LabelUserAdapter(int layoutResId, @Nullable List<ContactModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactModel item) {

    }
}
