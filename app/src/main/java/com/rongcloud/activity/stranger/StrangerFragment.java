package com.rongcloud.activity.stranger;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.newversion.FriendCircleActivity;
import com.newversion.MyTagsActivity;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.newversion.NewSelfDrvingActivity;
import com.rongcloud.model.MyFriend;
import com.vipcenter.RegisterActivity;
import com.vipcenter.UserCenterActivity;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrangerFragment extends ConversationListFragment {

    static Uri uri;
    public  static StrangerFragment newInstance(){
        StrangerFragment newBabyFragment=new StrangerFragment();
        uri = Uri.parse("rong://com.example.yunchebao").buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .build();
        newBabyFragment.setUri(uri);
        return newBabyFragment;
    }


    @Override
    public boolean shouldFilterConversation(Conversation.ConversationType type, String targetId) {

        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
        boolean isExits=false;
        if(type == Conversation.ConversationType.PRIVATE){
            for (int i = 0; i <myFriends.size() ; i++) {
                if(myFriends.get(i).getMyid().equals(targetId)){
                    Log.v("userid",myFriends.get(i).getMyid()+"---"+targetId);
                    isExits=true;
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

}
