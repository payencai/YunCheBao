package com.newversion;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bbcircle.view.SampleCoverVideo;
import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.payencai.library.view.CircleImageView;
import com.tool.listview.PersonalListView;

import java.util.Arrays;
import java.util.List;

public class CircleAdapter extends BaseAdapter {
    private Context mContext;
    private FriendsCircleActivity friendsCircleActivity;
    private List<CircleData> circleData;
    private DynamicCommonsAdapter commonsAdapter;
    /**
     * 动态类型  0 图片说说  1 小视频说说 2 转发链接  3 纯文字说说
     */
    private int dynamicType = 0;

    public CircleAdapter(Context mContext, List<CircleData> circleData) {
        this.friendsCircleActivity = (FriendsCircleActivity) mContext;
        this.mContext = mContext;
        this.circleData = circleData;

    }

    @Override
    public int getCount() {
        return circleData.size();
    }

    @Override
    public Object getItem(int position) {
        return circleData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friends_dynamic, null);

        CircleData circleInfo = circleData.get(position);

        CircleImageView head = (CircleImageView) convertView.findViewById(R.id.iv_head);
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView time = (TextView) convertView.findViewById(R.id.tv_time);
        TextView content = (TextView) convertView.findViewById(R.id.tv_dynamic_content);
        TextView tv_location = (TextView) convertView.findViewById(R.id.tv_location);
        FrameLayout frame_video_player = (FrameLayout) convertView.findViewById(R.id.frame_video_player);
        SampleCoverVideo sampleCoverVideo = (SampleCoverVideo) convertView.findViewById(R.id.sampleCoverVideo);
        ImageView iv_play = (ImageView) convertView.findViewById(R.id.iv_play);

        GridView gvDynamicPhotos = (GridView) convertView.findViewById(R.id.gv_dynamic_photos);
        ImageView ivVimg = (ImageView) convertView.findViewById(R.id.iv_vimg);
        ImageView iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
        ImageView iv_replay = (ImageView) convertView.findViewById(R.id.iv_replay);
        LikesView likeView = (LikesView) convertView.findViewById(R.id.likeView);

        PersonalListView lv_comment = (PersonalListView) convertView.findViewById(R.id.lv_comment);

        if (circleInfo.getCommentList() == null || circleInfo.getCommentList().size() == 0) {
            lv_comment.setVisibility(View.GONE);
        } else {
            lv_comment.setVisibility(View.VISIBLE);
            commonsAdapter = new DynamicCommonsAdapter(mContext, circleInfo.getCommentList());
            lv_comment.setAdapter(commonsAdapter);
        }

        if(!TextUtils.isEmpty(circleInfo.getAddress())){
            tv_location.setText(circleInfo.getAddress());
        }

        likeView.setListener(new LikesView.onItemClickListener() {
            @Override
            public void onItemClick(CircleData.ClickListBean bean) {
                String userId = bean.getUserId();
                // 跳转到他人朋友圈
                Intent intent = new Intent(mContext, FriendsCircleActivity.class);
                intent.putExtra("userId", userId);
                mContext.startActivity(intent);
            }
        });

        if (circleInfo.getClickList() == null || circleInfo.getClickList().size() == 0) {
            likeView.setVisibility(View.GONE);
        } else {
            likeView.setVisibility(View.VISIBLE);
            likeView.setList(circleInfo.getClickList());
            likeView.notifyDataSetChanged();
        }

        if (!TextUtils.isEmpty(circleInfo.getImgs()) && circleInfo.getImgs().split(",").length > 0) {
            dynamicType = 0;//图片说说
            frame_video_player.setVisibility(View.GONE);
            if (circleInfo.getImgs().split(",").length == 1) {//只有一张图片（宽高自适应）
                gvDynamicPhotos.setVisibility(View.GONE);
                ivVimg.setVisibility(View.VISIBLE);

                WindowManager manager = (WindowManager) mContext
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                ViewGroup.LayoutParams l = ivVimg.getLayoutParams();
                l.width = display.getWidth() / 2;

                Glide.with(mContext).load(circleInfo.getImgs().split(",")[0]).into(ivVimg);
            } else {//2张及以上图片，用九宫格展示
                gvDynamicPhotos.setVisibility(View.VISIBLE);
                ivVimg.setVisibility(View.GONE);
                List<String> dynamicPicList = Arrays.asList(circleInfo.getImgs().split(","));
                gvDynamicPhotos.setAdapter(new DynamicPicGridAdapter(mContext, dynamicPicList));
            }
        } else if (!TextUtils.isEmpty(circleInfo.getVideo())) {
            dynamicType = 1;//视频说说
            gvDynamicPhotos.setVisibility(View.GONE);
            frame_video_player.setVisibility(View.VISIBLE);

            String videoPath = circleInfo.getVideo();
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

        } else if (circleInfo.getType() == 2) {
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

        ImageView ivPrise = (ImageView) convertView.findViewById(R.id.iv_prise);

        Glide.with(mContext).load(circleInfo.getHeadPortrait()).into(head);
        name.setText(circleInfo.getName());
        time.setText(circleInfo.getCreateTime());

        if (TextUtils.isEmpty(circleInfo.getContent())) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(circleInfo.getContent());
        }


        if (circleInfo.getIsClick().equals("1")) {
            ivPrise.setImageResource(R.mipmap.icon_praise_selected);
        } else {
            ivPrise.setImageResource(R.mipmap.icon_praise_normal);
        }

        ivPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean didPraise;

                if (circleInfo.getIsClick().equals("1")) {
                    didPraise = false;
                    circleInfo.setIsClick("0");
                    ivPrise.setImageResource(R.mipmap.icon_praise_normal);

                    for (int i = 0; i < circleInfo.getClickList().size(); i++) {
                        if (circleInfo.getClickList().get(i).getUserId().equals(MyApplication.getUserInfo().getId())) {
                            circleInfo.getClickList().remove(i);
                        }
                    }

                } else {
                    didPraise = true;
                    circleInfo.setIsClick("1");
                    ivPrise.setImageResource(R.mipmap.icon_praise_selected);

                    CircleData.ClickListBean clickListBean = new CircleData.ClickListBean();
                    clickListBean.setHeadPortrait(MyApplication.getUserInfo().getHeadPortrait());
                    clickListBean.setName(MyApplication.getUserInfo().getName());
                    clickListBean.setUserId(MyApplication.getUserInfo().getId());

                    if (!circleInfo.getClickList().contains(clickListBean)) {
                        circleInfo.getClickList().add(clickListBean);
                    }
                }
                likeView.setList(circleInfo.getClickList());

                if (circleInfo.getClickList() == null || circleInfo.getClickList().size() == 0) {
                    likeView.setVisibility(View.GONE);
                } else {
                    likeView.setVisibility(View.VISIBLE);
                    likeView.notifyDataSetChanged();
                }

                notifyDataSetChanged();
                friendsCircleActivity.performPraise(didPraise, circleInfo.getId());
            }
        });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId = circleInfo.getUserId();
                // 跳转到他人朋友圈
                Intent intent = new Intent(mContext, FriendsCircleActivity.class);
                intent.putExtra("userId", userId);
                mContext.startActivity(intent);
            }
        });

        if (TextUtils.equals(circleInfo.getUserId(), MyApplication.getUserInfo().getId())) {
            //我自己的朋友圈
            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friendsCircleActivity.showDeleteCirclePoppupWindow(iv_delete, circleInfo.getId());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }


        iv_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReplay(circleInfo);
            }
        });

        return convertView;
    }

    private InputTextMsgDialog inputTextMsgDialog;

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