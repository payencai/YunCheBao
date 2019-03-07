package com.system.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caryibao.NewCar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;
import com.system.model.ChatNotice;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class ChatNoticeAdapter extends BaseQuickAdapter<ChatNotice,BaseViewHolder> {
    public ChatNoticeAdapter(int layoutResId, @Nullable List<ChatNotice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatNotice item) {
        CircleImageView iv_logo=helper.getView(R.id.iv_logo);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_time=helper.getView(R.id.tv_time);
        TextView tv_content=helper.getView(R.id.tv_content);
        Glide.with(helper.itemView.getContext()).load(item.getHeadPortrait()).into(iv_logo);
        tv_name.setText(item.getName());
        tv_time.setText(item.getCreateTime().substring(0,10));
        tv_content.setText(item.getContent());

    }
}
