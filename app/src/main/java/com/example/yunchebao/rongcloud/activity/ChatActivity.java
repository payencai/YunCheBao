package com.example.yunchebao.rongcloud.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.activity.contact.MyContactActivity;
import com.example.yunchebao.rongcloud.activity.stranger.SaomaActivity;
import com.example.yunchebao.rongcloud.activity.stranger.StrangerMsgActivity;
import com.example.yunchebao.rongcloud.model.MyGroup;
import com.gyf.immersionbar.ImmersionBar;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;

public class ChatActivity extends FragmentActivity {
    @BindView(R.id.add)
    ImageView tv_add;
    @BindView(R.id.rl_add)
    RelativeLayout rl_add;
    @BindView(R.id.rl_cloud)
    RelativeLayout cloud;
    @BindView(R.id.rl_stranger)
    RelativeLayout rl_stranger;
    @BindView(R.id.back)
    ImageView back;
    Uri uri;
    public ConversationListFragment mConversationFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversationlist);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
        enterFragment();

        //startConversationList();
    }

    private void setRongUserInfo(final String targetid) {
//        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
//        for (int i = 0; i < myFriends.size(); i++) {
//            MyFriend myFriend = myFriends.get(i);
//            String myid=myFriend.getMyid();
//            final String name = myFriend.getName();
//            final String head = myFriend.getHeadPortrait();
//            if(targetid.equals(myid)){
//                Log.e("myid",myid);
//                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//                    @Override
//                    public UserInfo getUserInfo(String s) {
//                         Log.e("params",s+name+head);
//                        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(s, name, Uri.parse(head));
//                        RongIM.getInstance().refreshUserInfoCache(userInfo);
//                        return userInfo;
//                    }
//                }, true);
//            }
//           /// Log.e("name", myFriend.getMyid() + name + head);
//
////            RongIM.getInstance().refreshUserInfoCache(userInfo);
////            return userInfo;
//        }
    }

    private void setGroupInfo(final String targetid) {
        if (RongIM.getInstance() != null) {
            RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
                @Override
                public Group getGroupInfo(String s) {
                    Group group = null;
                    List<MyGroup> myGroups = LitePal.findAll(MyGroup.class);
                    if (myGroups != null)
                        Log.e("mygroup", myGroups.size() + "");
                    for (int i = 0; i < myGroups.size(); i++) {
                        MyGroup myGroup = myGroups.get(i);
                        if (s.equals(myGroup.getCrowdUserId())) {
                            String name = myGroup.getCrowdName();
                            String head = myGroup.getImage();
                            group = new Group(s, name, Uri.parse(head));
                            RongIM.getInstance().refreshGroupInfoCache(group);
                            return group;
                        }
                    }
                    return group;
                }
            }, true);
        }
    }

    /**
     * 初始化会话列表
     *
     * @return 会话列表
     */

    public void enterFragment() {

//        RongIM.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
//            @Override
//            public void onSuccess(List<Conversation> conversations) {
//                if (conversations != null)
//                    for (int i = 0; i < conversations.size(); i++) {
//                        String targetid = conversations.get(i).getTargetId();
//                        Log.e("userid", targetid);
//
//                        if (conversations.get(i).getConversationType().getName().equals(Conversation.ConversationType.PRIVATE.getName())) {
//                            setRongUserInfo(targetid);
//                        }
//                        if (conversations.get(i).getConversationType().getName().equals(Conversation.ConversationType.GROUP.getName())) {
//                            setGroupInfo(targetid);
//                        }
//                    }
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//
//            }
//        });


        mConversationFragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);

         uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
         if(mConversationFragment!=null)
           mConversationFragment.setUri(uri);

    }


    private void initView() {
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWindow(rl_add);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, MyContactActivity.class));
            }
        });
        rl_stranger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, StrangerMsgActivity.class));
            }
        });
    }

    private void initWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_four_menu, null);
        LinearLayout ll_friends = (LinearLayout) v.findViewById(R.id.ll_friends);
        LinearLayout ll_group = (LinearLayout) v.findViewById(R.id.ll_group);
        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);
        LinearLayout ll_qrcode = (LinearLayout) v.findViewById(R.id.ll_qrcode);
        ll_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, AddFriendActivity.class));
            }
        });
        ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, CreateGroupActivity.class));
            }
        });
        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ChatActivity.this, SaomaActivity.class),1);
            }
        });
        ll_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(ChatActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(ChatActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
