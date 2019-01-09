package com.rongcloud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.application.MyApplication;
import com.baidu.platform.comapi.map.A;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.adapter.FriendAdapter;
import com.rongcloud.adapter.GroupAdapter;
import com.rongcloud.model.Friend;
import com.rongcloud.model.Group;
import com.vipcenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {
    @BindView(R.id.et_search)
    EditText tv_search;
    @BindView(R.id.lv_result)
    ListView lv_result;
    String type;
    String content;
    FriendAdapter mFriendAdapter;
    GroupAdapter mGroupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        content=getIntent().getStringExtra("content");
        type=getIntent().getStringExtra("type");
        tv_search.setText(content);
        if(type.equals("0")){//好友搜索
            searchFriend(content);
        }else if(type.equals("1")){//群组搜索
            searchGroup(content);
        }
        lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type.equals("0")){//好友搜索
                    Friend friend= (Friend) mFriendAdapter.getItem(position);
                    Intent intent=new Intent(SearchResultActivity.this,AddFriendDetailActivity.class);
                    intent.putExtra("friend",friend);
                    startActivity(intent);
                }else if(type.equals("1")){//群组搜索
                    Group group= (Group) mGroupAdapter.getItem(position);
                    Intent intent=new Intent(SearchResultActivity.this,AddGroupDetailActivity.class);
                    intent.putExtra("group",group);
                    startActivity(intent);
                }
            }
        });
        tv_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 String content=s.toString();
                if(type.equals("0")){//好友搜索
                    searchFriend(content);
                }else if(type.equals("1")){//群组搜索
                    searchGroup(content);
                }
            }
        });
    }
    private void searchFriend(final String content){
        Map<String,Object> params=new HashMap<>();
        params.put("keyWord",content);
        UserInfo userInfo= MyApplication.getUserInfo();
        if(userInfo!=null)
        HttpProxy.obtain().get(PlatformContans.Chat.searchFriendByKeyWord,params, userInfo.getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("friend",content+"--"+result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray data=jsonObject.getJSONArray("data");
                    List<Friend>friends=new ArrayList<>();
                    for (int i = 0; i <data.length() ; i++) {
                        JSONObject item=data.getJSONObject(i);
                        Friend friend=new Gson().fromJson(item.toString(),Friend.class);
                        friends.add(friend);
                    }
                    mFriendAdapter=new FriendAdapter(SearchResultActivity.this,friends);
                    lv_result.setAdapter(mFriendAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void searchGroup(String content){
        Map<String,Object> params=new HashMap<>();
        params.put("keyWord",content);
        UserInfo userInfo= MyApplication.getUserInfo();
        if(userInfo!=null)
            HttpProxy.obtain().get(PlatformContans.Chat.searchCrowdsByKeyWord,params, userInfo.getToken(), new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("group",result);
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        JSONArray data=jsonObject.getJSONArray("data");
                        List<Group>groups=new ArrayList<>();
                        for (int i = 0; i <data.length() ; i++) {
                            JSONObject item=data.getJSONObject(i);
                            Group friend=new Gson().fromJson(item.toString(),Group.class);
                            groups.add(friend);
                        }
                        mGroupAdapter=new GroupAdapter(SearchResultActivity.this,groups);
                        lv_result.setAdapter(mGroupAdapter);
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
