package com.vipcenter.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;

import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 浏览历史列表适配器
 */
public class ArticleListAdapter extends BaseQuickAdapter<PhoneArticleEntity,BaseViewHolder> {

    public ArticleListAdapter(int layoutResId, @Nullable List<PhoneArticleEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhoneArticleEntity item) {
        ImageView    iv_head = (ImageView) helper.getView(R.id.iv_head);
        ImageView   video = (ImageView) helper.getView(R.id.video);
        TextView tv_title = (TextView) helper.getView(R.id.tv_title);

        Glide.with(helper.itemView.getContext()).load(item.getImage()).into(iv_head);
        //vh.item1.setText("去年今日此门中，人面桃花相映红");
        tv_title.setText(item.getTitle());
        if(item.getType()>=3){
            video.setVisibility(View.VISIBLE);
        }
    }






}
