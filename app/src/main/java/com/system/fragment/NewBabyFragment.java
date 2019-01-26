package com.system.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.application.MyApplication;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.newversion.NewCarFriendActivity;
import com.newversion.NewContactsActivity;
import com.newversion.NewSelfDrvingActivity;
import com.vipcenter.RegisterActivity;

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
    protected void initFragment(Uri uri) {

       super.initFragment(uri);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        ll_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NewContactsActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
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


}
