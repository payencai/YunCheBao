package com.example.yunchebao.friendcircle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bbcircle.view.SampleCoverVideo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.NewFriendCircleActivity;
import com.newversion.CircleData;


import com.newversion.DynamicCommonsAdapter;
import com.newversion.DynamicPicGridAdapter;
import com.newversion.FriendsCircleActivity;
import com.newversion.InputTextMsgDialog;
import com.newversion.LikesView;
import com.payencai.library.view.CircleImageView;
import com.tool.listview.PersonalListView;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：凌涛 on 2019/4/26 17:30
 * 邮箱：771548229@qq..com
 */
public class CircleDataAdapter extends BaseQuickAdapter<CircleData, BaseViewHolder> {
    private NewFriendCircleActivity friendsCircleActivity;

    private DynamicCommonsAdapter commonsAdapter;
    /**
     * 动态类型  0 图片说说  1 小视频说说 2 转发链接  3 纯文字说说
     */
    private int dynamicType = 0;
    private InputTextMsgDialog inputTextMsgDialog;
    public CircleDataAdapter(int layoutResId, @Nullable List<CircleData> data) {
        super(layoutResId, data);
    }
    public CircleDataAdapter(Context context,int layoutResId, @Nullable List<CircleData> data) {
        this(layoutResId, data);
        friendsCircleActivity= (NewFriendCircleActivity) context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CircleData item) {
        helper.setIsRecyclable(false);
        helper.addOnClickListener(R.id.iv_head);
        CircleImageView head = (CircleImageView) helper.getView(R.id.iv_head);
        TextView name = (TextView) helper.getView(R.id.tv_name);
        TextView time = (TextView) helper.getView(R.id.tv_time);
        TextView content = (TextView) helper.getView(R.id.tv_dynamic_content);
        TextView tv_location = (TextView) helper.getView(R.id.tv_location);
        FrameLayout frame_video_player = (FrameLayout) helper.getView(R.id.frame_video_player);
        SampleCoverVideo sampleCoverVideo = (SampleCoverVideo) helper.getView(R.id.sampleCoverVideo);
        ImageView iv_play = (ImageView) helper.getView(R.id.iv_play);

        GridView gvDynamicPhotos = (GridView) helper.getView(R.id.gv_dynamic_photos);
        gvDynamicPhotos.setFocusableInTouchMode(false);
        gvDynamicPhotos.setFocusable(false);
        ImageView ivVimg = (ImageView) helper.getView(R.id.iv_vimg);
        ImageView iv_delete = (ImageView) helper.getView(R.id.iv_delete);
        ImageView iv_replay = (ImageView) helper.getView(R.id.iv_replay);
        LikesView likeView = (LikesView) helper.getView(R.id.likeView);

        PersonalListView lv_comment = (PersonalListView) helper.getView(R.id.lv_comment);
        lv_comment.setFocusableInTouchMode(false);
        lv_comment.setFocusable(false);
        if (item.getCommentList() == null || item.getCommentList().size() == 0) {
            lv_comment.setVisibility(View.GONE);
        } else {
            lv_comment.setVisibility(View.VISIBLE);
            commonsAdapter = new DynamicCommonsAdapter(mContext, item.getCommentList());
            lv_comment.setAdapter(commonsAdapter);
        }

        if(!TextUtils.isEmpty(item.getAddress())){
            tv_location.setText(item.getAddress());
        }

        likeView.setListener(new LikesView.onItemClickListener() {
            @Override
            public void onItemClick(CircleData.ClickListBean bean) {
                String userId = bean.getUserId();
                // 跳转到他人朋友圈
                Intent intent = new Intent(mContext, NewFriendCircleActivity.class);
                intent.putExtra("userId", userId);
                mContext.startActivity(intent);
            }
        });

        if (item.getClickList() == null || item.getClickList().size() == 0) {
            likeView.setVisibility(View.GONE);
        } else {
            likeView.setVisibility(View.VISIBLE);
            likeView.setList(item.getClickList());
            likeView.notifyDataSetChanged();
        }

        if (!TextUtils.isEmpty(item.getImgs()) && item.getImgs().split(",").length > 0) {
            dynamicType = 0;//图片说说
            frame_video_player.setVisibility(View.GONE);
            if (item.getImgs().split(",").length == 1) {//只有一张图片（宽高自适应）
                gvDynamicPhotos.setVisibility(View.GONE);
                ivVimg.setVisibility(View.VISIBLE);

                WindowManager manager = (WindowManager) mContext
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                ViewGroup.LayoutParams l = ivVimg.getLayoutParams();
                l.width = display.getWidth() / 2;

                Glide.with(mContext).load(item.getImgs().split(",")[0]).into(ivVimg);
            } else {//2张及以上图片，用九宫格展示
                gvDynamicPhotos.setVisibility(View.VISIBLE);
                ivVimg.setVisibility(View.GONE);
                List<String> dynamicPicList = Arrays.asList(item.getImgs().split(","));
                gvDynamicPhotos.setAdapter(new DynamicPicGridAdapter(mContext, dynamicPicList));
            }
        } else if (!TextUtils.isEmpty(item.getVideo())) {
            dynamicType = 1;//视频说说
            gvDynamicPhotos.setVisibility(View.GONE);
            frame_video_player.setVisibility(View.VISIBLE);

            String videoPath = item.getVideo();
            sampleCoverVideo.setUpLazy(videoPath, true, null, null, "");
            sampleCoverVideo.getTitleTextView().setVisibility(View.GONE);
            sampleCoverVideo.getBackButton().setVisibility(View.GONE);

            sampleCoverVideo.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_play.setVisibility(View.GONE);
                    sampleCoverVideo.startWindowFullscreen(v.getContext(), false, true);
                }
            });
            sampleCoverVideo.loadCoverImage(videoPath, R.mipmap.pic1);

        } else if (item.getType() == 2) {
            dynamicType = 2;//转发链接
            frame_video_player.setVisibility(View.GONE);
            gvDynamicPhotos.setVisibility(View.GONE);
            ivVimg.setVisibility(View.GONE);
        } else {
            frame_video_player.setVisibility(View.GONE);
            dynamicType = 3;//纯文字说说
            gvDynamicPhotos.setVisibility(View.GONE);
            ivVimg.setVisibility(View.GONE);
        }

        ImageView ivPrise = (ImageView) helper.getView(R.id.iv_prise);

        Glide.with(mContext).load(item.getHeadPortrait()).into(head);
        name.setText(item.getName());
        time.setText(item.getCreateTime());

        if (TextUtils.isEmpty(item.getContent())) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(item.getContent());
        }


        if (item.getIsClick().equals("1")) {
            ivPrise.setImageResource(R.mipmap.icon_praise_selected);
        } else {
            ivPrise.setImageResource(R.mipmap.icon_praise_normal);
        }

        ivPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean didPraise;

                if (item.getIsClick().equals("1")) {
                    didPraise = false;
                    item.setIsClick("0");
                    ivPrise.setImageResource(R.mipmap.icon_praise_normal);

                    for (int i = 0; i < item.getClickList().size(); i++) {
                        if (item.getClickList().get(i).getUserId().equals(MyApplication.getUserInfo().getId())) {
                            item.getClickList().remove(i);
                        }
                    }

                } else {
                    didPraise = true;
                    item.setIsClick("1");
                    ivPrise.setImageResource(R.mipmap.icon_praise_selected);

                    CircleData.ClickListBean clickListBean = new CircleData.ClickListBean();
                    clickListBean.setHeadPortrait(MyApplication.getUserInfo().getHeadPortrait());
                    clickListBean.setName(MyApplication.getUserInfo().getName());
                    clickListBean.setUserId(MyApplication.getUserInfo().getId());

                    if (!item.getClickList().contains(clickListBean)) {
                        item.getClickList().add(clickListBean);
                    }
                }
                likeView.setList(item.getClickList());

                if (item.getClickList() == null || item.getClickList().size() == 0) {
                    likeView.setVisibility(View.GONE);
                } else {
                    likeView.setVisibility(View.VISIBLE);
                    likeView.notifyDataSetChanged();
                }
                notifyDataSetChanged();
                friendsCircleActivity.performPraise(didPraise, item.getId());
            }
        });

//        head.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userId = item.getUserId();
//                // 跳转到他人朋友圈
//                Intent intent = new Intent(mContext, NewFriendCircleActivity.class);
//                intent.putExtra("userId", userId);
//                mContext.startActivity(intent);
//            }
//        });

        if (TextUtils.equals(item.getUserId(), MyApplication.getUserInfo().getId())) {
            //我自己的朋友圈
            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friendsCircleActivity.showDeleteCirclePoppupWindow(iv_delete, item.getId());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }


        iv_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReplay(item);
            }
        });
    }
    /**
     * 弹窗输入回复内容
     *
     * @param circleInfo
     */
    public void addReplay(CircleData circleInfo) {
        inputTextMsgDialog = new InputTextMsgDialog(mContext, R.style.dialog_center);
        inputTextMsgDialog.setHint("评论");
        inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                friendsCircleActivity.addReplay(circleInfo.getId(), msg);
                CircleData.CommentListBean commentListBean = new CircleData.CommentListBean();
                commentListBean.setContent(msg);
                commentListBean.setName(MyApplication.getUserInfo().getName());
                commentListBean.setUserId(MyApplication.getUserInfo().getId());

                commentListBean.setType(1);//表示添加回复

                circleInfo.getCommentList().add(commentListBean);
                if(commonsAdapter!=null){
                    commonsAdapter.notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }
        });

        inputTextMsgDialog.show();
    }
}
