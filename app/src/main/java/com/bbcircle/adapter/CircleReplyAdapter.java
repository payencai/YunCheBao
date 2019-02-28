package com.bbcircle.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bbcircle.data.CircleComment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/28 10:26
 * 邮箱：771548229@qq..com
 */
public class CircleReplyAdapter extends BaseQuickAdapter<CircleComment.CommentReplyListBean,BaseViewHolder> {

    public CircleReplyAdapter(int layoutResId, @Nullable List<CircleComment.CommentReplyListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleComment.CommentReplyListBean item) {
        TextView tv_nick=helper.getView(R.id.tv_name);
        TextView tv_content=helper.getView(R.id.tv_content);
        tv_content.setText(item.getContent());
        tv_nick.setText(item.getNickname());

    }
}
