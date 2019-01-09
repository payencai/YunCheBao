package com.vipcenter.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.GiftRecord;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 礼品汇兑换记录列表适配器
 */
public class RecordListAdapter extends BaseQuickAdapter<GiftRecord,BaseViewHolder> {


    public RecordListAdapter(int layoutResId, @Nullable List<GiftRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftRecord item) {

    }
}
