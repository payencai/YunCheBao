package com.example.yunchebao.applyenter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/30 11:07
 * 邮箱：771548229@qq..com
 */
public class AgencyAdapter extends BaseQuickAdapter<Agency, BaseViewHolder> {
    public AgencyAdapter(@Nullable List<Agency> data) {
        super(R.layout.item_agency_service, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Agency item) {
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        tv_name.setText(item.getName());
        tv_dis.setText(item.getSex());
        helper.addOnClickListener(R.id.select);
}
}
