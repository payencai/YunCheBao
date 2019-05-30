package com.newversion;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.NewFriendCircleActivity;

import java.util.ArrayList;
import java.util.List;

public  class DynamicCommonsAdapter extends BaseAdapter {
    private Context mContext;
    private List<CircleData.CommentListBean> commentList = new ArrayList<>();
    private NewFriendCircleActivity friendsCircleActivity;

    public DynamicCommonsAdapter(Context mContext, List<CircleData.CommentListBean> commentList) {
        this.friendsCircleActivity = (NewFriendCircleActivity) mContext;
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_comment, null);

        TextView tv_commont = (TextView) convertView.findViewById(R.id.tv_commont);
        CircleData.CommentListBean commentListBean = commentList.get(position);

        String strContent;
        if (commentListBean.getType() == 1) {
            strContent = "<font color='#296eaf'>" + commentListBean.getName() + "：</font>" + commentListBean.getContent();
        } else {
            strContent = "<font color='#296eaf'>" + commentListBean.getName() + "</font>" + "  <font color='#b2b2b2'>回复</font>  " + "<font color='#296eaf'> " + commentListBean.getReplyName() + "： </font>" + commentListBean.getContent();
        }
        tv_commont.setText(Html.fromHtml(strContent));

        tv_commont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getUserInfo().getId().equals(commentListBean.getUserId())) {
                    //自己的评论  点击删除
                    showDeleteCommonDialog(commentListBean,position);
                } else {
                    //别人的评论  点击回复别人
                    sendReplay(commentList, commentListBean);
                }
            }
        });

        return convertView;
    }


    /**
     * 展示删除评论弹窗
     *
     * @param commentListBean
     * @param position
     */
    private void showDeleteCommonDialog(CircleData.CommentListBean commentListBean, int position) {
        View view = friendsCircleActivity.getLayoutInflater().inflate(R.layout.dialog_delete_common, null);

        final Dialog dialog = new Dialog(mContext, R.style.MyDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();


        TextView tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        LinearLayout ll_outside = (LinearLayout) view.findViewById(R.id.ll_outside);

        ll_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(commentListBean.getId()!=null){
                    friendsCircleActivity.deleteCommon(commentListBean.getId());
                }
                commentList.remove(position);
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);

    }

    private InputTextMsgDialog inputTextMsgDialog;

    /**
     * 弹窗输入回复内容
     *
     * @param commentList
     * @param commentListBean
     */
    public void sendReplay(List<CircleData.CommentListBean> commentList, CircleData.CommentListBean commentListBean) {
        inputTextMsgDialog = new InputTextMsgDialog(mContext, R.style.dialog_center);

        inputTextMsgDialog.setHint("回复" + commentListBean.getName());

        inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                friendsCircleActivity.sendReplay(commentListBean.getCircleId(), msg,commentListBean.getUserId());

                CircleData.CommentListBean commentBean = new CircleData.CommentListBean();
                commentBean.setContent(msg);
                commentBean.setName(MyApplication.getUserInfo().getName());
                commentBean.setReplyName(commentListBean.getName());
                commentBean.setUserId(MyApplication.getUserInfo().getId());
                commentBean.setType(0);//表示回复别人

                commentList.add(commentBean);
                notifyDataSetChanged();
            }
        });

        inputTextMsgDialog.show();
    }
}
