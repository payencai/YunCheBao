package com.example.yunchebao.blacklist;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/5/20 11:45
 * 邮箱：771548229@qq..com
 */
public class BlackuserAdapter extends BaseQuickAdapter<BlackUser, BaseViewHolder> {
    public BlackuserAdapter(int layoutResId, @Nullable List<BlackUser> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlackUser item) {
        helper.addOnClickListener(R.id.btnDelete);
        CircleImageView iv_head=helper.getView(R.id.iv_head);
        TextView  tv_name=helper.getView(R.id.tv_name);
        tv_name.setText(item.getName());
        Glide.with(mContext).load(item.getHeader()).into(iv_head);
    }
}
