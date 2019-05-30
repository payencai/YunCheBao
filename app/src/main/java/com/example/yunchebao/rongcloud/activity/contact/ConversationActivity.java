package com.example.yunchebao.rongcloud.activity.contact;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.GroupDetail;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.activity.StrangerDelActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class ConversationActivity extends AppCompatActivity {
    String targetId;//传递的融云的id
    String title; //传递的融云名称/用户昵称
    @BindView(R.id.title)
    TextView tv_title;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    String type = "";
    UserMsg mUserMsg;
    GroupDetail mGroupUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        //ImmersionBar.with(this).autoDarkModeEnable(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {

        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        tv_title.setText(title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(mUserMsg!=null){
                    if ("1".equals(mUserMsg.getIsFriend())) {
                        intent = new Intent(ConversationActivity.this, FriendDetailActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);
                    } else {
                        intent = new Intent(ConversationActivity.this, StrangerDelActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);

                    }
                }
                if(mGroupUser!=null){
                    if (mGroupUser.getCrowdUserId().equals(MyApplication.getUserInfo().getId())) {
                        intent = new Intent(ConversationActivity.this, MyGroupDetailActivity.class);
                        intent.putExtra("id", mGroupUser.getHxCrowdId());
                        startActivity(intent);
                    } else {
                        intent = new Intent(ConversationActivity.this, GroupDetailActivity.class);
                        intent.putExtra("id", mGroupUser.getHxCrowdId());
                        startActivity(intent);
                    }
                }



            }
        });
        if (targetId.contains("-")) {
            getUserDetail(targetId);
        } else {
            getGroupDetail(targetId);

        }

    }
    private void setSendMessageListener(String groupId,String groupName,String avatar) {
        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
            @Override
            public Message onSend(Message message) {
                if (null != message.getContent() && message.getContent() instanceof TextMessage) {
                    TextMessage txtMsg = (TextMessage) message.getContent();
                    if (null != txtMsg) {
                        if (message.getConversationType().equals(Conversation.ConversationType.GROUP)) {
                            //获取群昵称查看群昵称是否与原昵称相同，相同就不增加extra字段
//                            requestGroupNickName(mTargetId);
                            GroupUserInfo groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(groupId, MyApplication.getUserInfo().getId());
                            //必须去判断是否为空，否则会报空指针
                            if (null != groupUserInfo) {
                                if (null != groupUserInfo.getNickname()) {
                                    if (!groupUserInfo.getNickname().equals(MyApplication.getUserInfo().getName())) {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("groupName", groupName);
                                        params.put("groupUserId", groupId);
                                        params.put("groupAvatar", avatar);
                                        params.put("nickName", groupUserInfo.getNickname());
                                        params.put("userId", groupUserInfo.getUserId());
                                        params.put("avatar", MyApplication.getUserInfo().getHeadPortrait());
                                        params.put("toNickName", groupUserInfo.getNickname());
                                        params.put("toUserId", groupUserInfo.getUserId());
                                        params.put("toAvatar", MyApplication.getUserInfo().getHeadPortrait());
                                        String extra = new Gson().toJson(params);
                                        txtMsg.setExtra(extra );
                                    }
                                }
                            }
                        }
                    }
                }
                return message;
            }

            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                if (message.getSentStatus() == Message.SentStatus.FAILED) {
                    if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_CHATROOM) {
                        //不在聊天室
                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION) {
                        //不在讨论组
                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.NOT_IN_GROUP) {
                        //不在群组
                    } else if (sentMessageErrorCode == RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST) {
                        //你在他的黑名单中
                    }
                }
                MessageContent messageContent = message.getContent();
                if (messageContent instanceof TextMessage) {//文本消息
                    TextMessage textMessage = (TextMessage) messageContent;
                    Log.d("TAG", "onSent-TextMessage:" + textMessage.getContent());
                } else if (messageContent instanceof ImageMessage) {//图片消息
                    ImageMessage imageMessage = (ImageMessage) messageContent;
                    Log.d("TAG", "onSent-ImageMessage:" + imageMessage.getRemoteUri());
                } else if (messageContent instanceof VoiceMessage) {//语音消息
                    VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                    Log.d("TAG", "onSent-voiceMessage:" + voiceMessage.getUri().toString());
                } else if (messageContent instanceof RichContentMessage) {//图文消息
                    RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                    Log.d("TAG", "onSent-RichContentMessage:" + richContentMessage.getContent());
                } else {
                    Log.d("TAG", "onSent-其他消息，自己来判断处理");
                }



                return false;
            }
        });
    }
    private void getGroupDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("hxCrowdId", id);
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByHxCrowdId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject Json = new JSONObject(result);
                    JSONObject data = Json.getJSONObject("data");
                    mGroupUser = new Gson().fromJson(data.toString(), GroupDetail.class);
                    setSendMessageListener(targetId,mGroupUser.getCrowdName(),mGroupUser.getImage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getUserDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mUserMsg = new Gson().fromJson(data.toString(), UserMsg.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
