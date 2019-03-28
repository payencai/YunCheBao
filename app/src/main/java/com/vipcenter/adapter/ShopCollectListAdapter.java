package com.vipcenter.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.vipcenter.model.ShopCollect;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 列表适配器
 */
public class ShopCollectListAdapter extends BaseQuickAdapter<ShopCollect,BaseViewHolder> {


    public ShopCollectListAdapter(int layoutResId, @Nullable List<ShopCollect> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, ShopCollect item) {
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
        tv_name.setText(item.getName());
    }



}
