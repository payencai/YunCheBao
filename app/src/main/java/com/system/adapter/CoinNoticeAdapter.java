package com.system.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.system.model.ChatNotice;
import com.system.model.CoinNotice;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class CoinNoticeAdapter extends BaseQuickAdapter<CoinNotice,BaseViewHolder> {
    public CoinNoticeAdapter(int layoutResId, @Nullable List<CoinNotice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinNotice item) {
        TextView tv_title=helper.getView(R.id.tv_title);
        TextView tv_time=helper.getView(R.id.tv_time);
        tv_time.setText(item.getCreateTime().substring(0,10));
        tv_title.setText(item.getTitle());
    }
}
