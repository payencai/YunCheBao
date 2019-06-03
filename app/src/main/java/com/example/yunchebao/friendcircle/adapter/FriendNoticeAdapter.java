package com.example.yunchebao.friendcircle.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.model.FriendNotice;
import com.payencai.library.view.CircleImageView;

import java.util.List;

public class FriendNoticeAdapter extends BaseQuickAdapter<FriendNotice, BaseViewHolder> {
    public FriendNoticeAdapter(int layoutResId, @Nullable List<FriendNotice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendNotice item) {
        CircleImageView iv_head=helper.getView(R.id.iv_head);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_time=helper.getView(R.id.tv_time);
        TextView tv_title=helper.getView(R.id.tv_title);
        TextView tv_reply=helper.getView(R.id.tv_reply);
        ImageView iv_praise=helper.getView(R.id.iv_praise);
        ImageView iv_video=helper.getView(R.id.iv_video);
        ImageView iv_play=helper.getView(R.id.iv_play);
        RelativeLayout rl_video=helper.getView(R.id.rl_video);
        if(item.getType()==1){
            iv_praise.setVisibility(View.VISIBLE);
            tv_reply.setVisibility(View.GONE);
        }else{
            iv_praise.setVisibility(View.GONE);
            tv_reply.setVisibility(View.VISIBLE);
            tv_reply.setText(item.getCommentContent());
        }
        Glide.with(mContext).load(item.getOtherHeadPortrait()).into(iv_head);
        tv_name.setText(item.getOtherName());
        tv_time.setText(item.getCreateTime());
        if(!TextUtils.isEmpty(item.getVimg())){
            rl_video.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.getVimg()).into(iv_video);
        }else{
            if(!TextUtils.isEmpty(item.getImgs())){
                String [] imgs=item.getImgs().split(",");
                rl_video.setVisibility(View.VISIBLE);
                iv_video.setVisibility(View.VISIBLE);
                iv_play.setVisibility(View.GONE);
                if(imgs.length>1)
                   Glide.with(mContext).load(imgs[0]).into(iv_video);
                else{
                    Glide.with(mContext).load(item.getImgs()).into(iv_video);
                }
            }else{
                rl_video.setVisibility(View.GONE);
                tv_title.setText(item.getContent());
                tv_title.setVisibility(View.VISIBLE);
            }

        }
    }
}
