package com.bbcircle.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bbcircle.data.CircleComment;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/28 10:26
 * 邮箱：771548229@qq..com
 */
public class CircleCommentAdapter extends BaseQuickAdapter<CircleComment,BaseViewHolder> {
    CircleReplyAdapter mCircleReplyAdapter;

    public CircleCommentAdapter(int layoutResId, @Nullable List<CircleComment> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleComment item) {
        RecyclerView rv_reply=helper.getView(R.id.rv_reply);
        TextView tv_nick=helper.getView(R.id.tv_nick);
        TextView tv_time=helper.getView(R.id.tv_time);
        helper.addOnClickListener(R.id.tv_reply);
        TextView tv_content=helper.getView(R.id.tv_content);
        CircleImageView iv_head=helper.getView(R.id.iv_head);
        tv_content.setText(item.getContent());
        tv_nick.setText(item.getName());
        tv_time.setText(item.getCreateTime());
        Glide.with(helper.itemView.getContext()).load(item.getHeadPortrait()).into(iv_head);
        mCircleReplyAdapter=new CircleReplyAdapter(R.layout.item_comment_reply,item.getCommentReplyList());
        rv_reply.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext()));
        rv_reply.setAdapter(mCircleReplyAdapter);
    }
}
