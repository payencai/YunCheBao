package com.system.fragment;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.newversion.FriendsCircleActivity;
import com.newversion.MyTagsActivity;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.example.yunchebao.babycircle.selfdrive.NewSelfDrvingActivity;
import com.example.yunchebao.rongcloud.activity.AddFriendActivity;
import com.example.yunchebao.rongcloud.activity.CreateGroupActivity;
import com.example.yunchebao.rongcloud.activity.stranger.SaomaActivity;
import com.example.yunchebao.rongcloud.activity.stranger.StrangerMsgActivity;
import com.example.yunchebao.rongcloud.model.MyFriend;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vipcenter.UserCenterActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewBabyFragment extends ConversationListFragment {
    List<View> mViews;


    static Uri uri;
    public  static NewBabyFragment newInstance(){
        NewBabyFragment newBabyFragment=new NewBabyFragment();
        uri = Uri.parse("rong://com.example.yunchebao").buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//设置群组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        newBabyFragment.setUri(uri);
        return newBabyFragment;
    }


    @Override
    public boolean shouldFilterConversation(Conversation.ConversationType type, String targetId) {

        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
        boolean isExits=false;

        if(type == Conversation.ConversationType.PRIVATE){
            isExits=true;
            for (int i = 0; i <myFriends.size() ; i++) {
                if(myFriends.get(i).getUserId().equals(targetId)){
                    Log.v("userid",myFriends.get(i).getUserId()+"---"+targetId);
                    isExits=false;
                    break;
                }
            }
        }
        Log.v("userid",""+isExits);
        return isExits;
    }

    @Override
    protected void initFragment(Uri uri) {

       super.initFragment(uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private void initWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(getContext());
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_four_menu, null);
        LinearLayout ll_friends = (LinearLayout) v.findViewById(R.id.ll_friends);
        LinearLayout ll_group = (LinearLayout) v.findViewById(R.id.ll_group);
        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);
        LinearLayout ll_qrcode = (LinearLayout) v.findViewById(R.id.ll_qrcode);
        ll_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });
        ll_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
            }
        });
        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SaomaActivity.class),1);
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
    protected List<View> onAddHeaderView() {
        mViews=new ArrayList<>();
        View headerView=LayoutInflater.from(getContext()).inflate(R.layout.header_baby,null);
        LinearLayout ll_self= (LinearLayout) headerView.findViewById(R.id.ll_self);
        LinearLayout ll_carfriend= (LinearLayout) headerView.findViewById(R.id.ll_carfriend);
        LinearLayout ll_item1=(LinearLayout) headerView.findViewById(R.id.ll_item1);
        LinearLayout ll_item2=(LinearLayout) headerView.findViewById(R.id.ll_item2);
        LinearLayout ll_item3=(LinearLayout) headerView.findViewById(R.id.ll_item3);
        LinearLayout ll_circle=(LinearLayout) headerView.findViewById(R.id.ll_circle);
        LinearLayout ll_tags=(LinearLayout) headerView.findViewById(R.id.ll_tags);
        RelativeLayout rl_stranger=(RelativeLayout) headerView.findViewById(R.id.rl_stranger);
        ImageView user_center_icon= (ImageView) headerView.findViewById(R.id.user_center_icon);
        ImageView messenger_icon= (ImageView) headerView.findViewById(R.id.messenger_icon);
        ll_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NewContactsActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
            }
        });
        ll_tags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MyTagsActivity.class));
            }
        });
        rl_stranger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StrangerMsgActivity.class));
            }
        });
        messenger_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   initWindow(v);
            }
        });
        user_center_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    startActivityForResult(new Intent(getContext(), UserCenterActivity.class),12);
                }
            }
        });
        ll_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NewContactsActivity.class);
                intent.putExtra("type",2);
                startActivity(intent);
            }
        });
        ll_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FriendsCircleActivity.class));
            }
        });
        ll_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NewContactsActivity.class);
                intent.putExtra("type",3);
                startActivity(intent);
            }
        });
        ll_self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewSelfDrvingActivity.class));
            }
        });
        ll_carfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), NewCarFriendActivity.class));
            }
        });
        mViews.add(headerView);
        return mViews;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
