package com.vipcenter.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.GiftCoin;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/9 15:42
 * 邮箱：771548229@qq..com
 */
public class GiftMoneyAdapter extends BaseQuickAdapter<GiftCoin, BaseViewHolder> {
    public GiftMoneyAdapter(int layoutResId, @Nullable List<GiftCoin> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftCoin item) {
        TextView item1 = helper.getView(R.id.item1);
        TextView item2 = helper.getView(R.id.item2);
        TextView item3 = helper.getView(R.id.item3);
        item1.setText(item.getContents());
        item2.setText(item.getCreateTime());
        if (item.getState() == 2)
            item3.setText("-" + item.getCoinCount());
        else {
            item3.setText("+" + item.getCoinCount());
        }
    }
}
