package com.rongcloud.activity.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends AppCompatActivity {
    String targetId ;//传递的融云的id
    String title ; //传递的融云名称/用户昵称
    @BindView(R.id.title)
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        targetId=getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        tv_title.setText(title);
        RongIM.getInstance().setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                if(conversationType.getName().equals(Conversation.ConversationType.PRIVATE.getName())){
                    if(targetId.equals(userInfo.getUserId())){
                        Intent intent =new Intent(context,FriendDetailActivity.class);
                        intent.putExtra("userId",targetId);
                        context.startActivity(intent);
                    }
                }
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });
//        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
//            @Override
//            public Message onSend(Message message) {
//
//                return message;
//            }
//
//            @Override
//            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
//                return false;
//            }
//        });
//        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
//            @Override
//            public boolean onReceived(Message message, int i) {
//                return false;
//            }
//        });
    }
}
