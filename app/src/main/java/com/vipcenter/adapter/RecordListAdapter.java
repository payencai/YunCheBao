package com.vipcenter.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
public class RecordListAdapter extends BaseQuickAdapter<GiftRecord, BaseViewHolder> {


    public RecordListAdapter(int layoutResId, @Nullable List<GiftRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftRecord item) {
        SimpleDraweeView img = helper.getView(R.id.img);
        TextView name = helper.getView(R.id.name);
        TextView tv_coin = helper.getView(R.id.tv_coin);
        TextView tv_time = helper.getView(R.id.tv_time);
        name.setText(item.getCommodityName());
        tv_coin.setText(item.getCommodityCoinCount()+"");
        String url="";
        if(!TextUtils.isEmpty(url)){
            Uri uri = Uri.parse(url);
            img.setImageURI(uri);
        }else{
            Uri uri = Uri.parse(item.getCommodityImage());
            img.setImageURI(uri);
        }
        tv_time.setText(item.getCreateTime().substring(0,10));
    }
}
