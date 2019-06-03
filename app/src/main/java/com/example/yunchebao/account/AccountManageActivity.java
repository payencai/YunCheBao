package com.example.yunchebao.account;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.example.yunchebao.rongcloud.model.MyFriend;
import com.example.yunchebao.rongcloud.model.MyGroup;
import com.tool.AccountUtil;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;
import com.vipcenter.adapter.AccountAdapter;
import com.vipcenter.adapter.MyPublishListAdapter;
import com.vipcenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.callkit.util.SPUtils;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class AccountManageActivity extends AppCompatActivity {
    List<String> accountList;
    @BindView(R.id.lv_account)
    ListViewForScrollView lv_account;
    AccountAdapter mAccountAdapter;
    List<AccountUtil.Account> mAccounts;
    int oldpos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        accountList=new ArrayList<>();
        mAccounts=new ArrayList<>();
        accountList.addAll(AccountUtil.getAccountList(MyApplication.getContext()));
        for (int i = 0; i <accountList.size() ; i++) {
            AccountUtil.Account account=new Gson().fromJson(accountList.get(i), AccountUtil.Account.class);
            if(MyApplication.getUserInfo().getUsername().equals(account.getAccount())){
                account.setCurrent(true);
                oldpos=i;
            }

            mAccounts.add(account);
            Log.e("account",accountList.get(i));
        }
        mAccountAdapter=new AccountAdapter(this,mAccounts);
        lv_account.setAdapter(mAccountAdapter);
        mAccountAdapter.setOnDeleteClickListener(new MyPublishListAdapter.onDeleteClickListener() {
            @Override
            public void onClick(int pos) {
                if(MyApplication.getUserInfo().getUsername().equals(mAccounts.get(pos).getAccount())){
                    ToastUtil.showToast(AccountManageActivity.this,"当前账号不能删除");
                    return;
                }
                String accout=mAccounts.get(pos).getAccount();
                if(!TextUtils.isEmpty(accout))
                AccountUtil.deleteOneAccount(accout,MyApplication.getContext());
                accountList.clear();
                mAccounts.clear();
                accountList.addAll(AccountUtil.getAccountList(MyApplication.getContext()));
                for (int i = 0; i <accountList.size() ; i++) {
                    AccountUtil.Account account=new Gson().fromJson(accountList.get(i), AccountUtil.Account.class);
                    if(MyApplication.getUserInfo().getUsername().equals(account.getAccount()))
                        account.setCurrent(true);
                    mAccounts.add(account);
                    //Log.e("account",accountList.get(i));
                }
                mAccountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(int pos) {
                if(MyApplication.getUserInfo().getUsername().equals(mAccounts.get(pos).getAccount())){
                    return;
                }
                RongIM.getInstance().logout();
                loginByPwd(mAccounts.get(pos).getAccount(),mAccounts.get(pos).getPwd());
                mAccounts.get(pos).setCurrent(true);
                mAccounts.get(oldpos).setCurrent(false);
                mAccountAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.ll_bottom,R.id.back})
    void Onclick(View view){
        switch (view.getId()){
            case R.id.ll_bottom:
                startActivity(new Intent(AccountManageActivity.this, RegisterActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void loginByPwd(String account,String pwd){
        Map<String, Object> params = new HashMap<>();
        params.put("username", account);
        params.put("password", pwd);
        HttpProxy.obtain().post(PlatformContans.User.loginByPwd, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("loginByPwd", result);
                try {
                    //Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    String msg=object.getString("message");
                    if(code==0){
                        JSONObject data=object.getJSONObject("data");
                        UserInfo userInfo =new Gson().fromJson(data.toString(),UserInfo.class);
                        Map<String,Object> params=new HashMap<>();
                        params.put("account",account);
                        params.put("pwd",pwd);
                        params.put("photo",userInfo.getHeadPortrait());
                        params.put("isCurrent",false);
                        String json=new Gson().toJson(params);
                        AccountUtil.addToAccountList(json,MyApplication.getContext());
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.token=userInfo.getToken();
                        MyApplication.setIsLogin(true);
                        SPUtils.put(AccountManageActivity.this,"phone",account);
                        SPUtils.put(AccountManageActivity.this,"pwd",pwd);
                        accountList.clear();
                        accountList.addAll(AccountUtil.getAccountList(MyApplication.getContext()));
                        mAccounts.clear();
                        for (int i = 0; i <accountList.size() ; i++) {
                            AccountUtil.Account account=new Gson().fromJson(accountList.get(i), AccountUtil.Account.class);
                            if(MyApplication.getUserInfo().getUsername().equals(account.getAccount())){
                                account.setCurrent(true);
                                oldpos=i;
                            }
                            mAccounts.add(account);
                            Log.e("account",accountList.get(i));
                        }
                        mAccountAdapter.notifyDataSetChanged();
                        getData();
                    }else{
                       // Toast.makeText(AccountManageActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getContacts() {
        LitePal.deleteAll(MyFriend.class);
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getMyFriendList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("getContacts", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MyFriend> myFriends = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyFriend myFriend = new Gson().fromJson(item.toString(), MyFriend.class);
                            myFriends.add(myFriend);
                            if (!myFriend.isSaved())
                                myFriend.save();
                        }
                        // MyApplication.getDataSave().setDataList("friends", myFriends);
                        connect(MyApplication.getUserInfo().getHxPassword());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {

                }
            });
    }

    private void getData() {
        LitePal.deleteAll(MyGroup.class);
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getCrowdsList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("apply", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<MyGroup> myGroups = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyGroup myGroup = new Gson().fromJson(item.toString(), MyGroup.class);
                            myGroup.save();
                            myGroups.add(myGroup);
                        }
                        getContacts();
                        //MyApplication.getDataSave().setDataList("groups", myGroups);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {
                }
            });
    }

    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */

            @Override
            public void onTokenIncorrect() {

            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(MyApplication.getUserInfo().getId(), MyApplication.getUserInfo().getName(), Uri.parse(MyApplication.getUserInfo().getHeadPortrait())));
                RongIM.setGroupInfoProvider(new RongIM.GroupInfoProvider() {
                                                @Override
                                                public Group getGroupInfo(String s) {
                                                    List<MyGroup> myFriends = LitePal.findAll(MyGroup.class);
                                                    Log.e("data", s);
                                                    for (int i = 0; i < myFriends.size(); i++) {
                                                        MyGroup myFriend = myFriends.get(i);
                                                        String myid = myFriend.getHxCrowdId();
                                                        final String name = myFriend.getCrowdName();
                                                        final String head = myFriend.getImage();
                                                        if (s.equals(myid)) {
                                                            io.rong.imlib.model.Group userInfo = new io.rong.imlib.model.Group(s, name, Uri.parse(head));
                                                            RongIM.getInstance().refreshGroupInfoCache(userInfo);
                                                        }
                                                    }
                                                    return null;
                                                }
                                            }, true
                );
//                RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
//                    @Override
//                    public GroupUserInfo getGroupUserInfo(String s, String s1) {
//                        return null;
//                    }
//                }, true);
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
                        Log.e("data", s);
                        for (int i = 0; i < myFriends.size(); i++) {
                            MyFriend myFriend = myFriends.get(i);
                            String myid = myFriend.getUserId();
                            final String name = myFriend.getName();
                            final String head = myFriend.getHeadPortrait();
                            if (s.equals(myid)) {
                                io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(s, name, Uri.parse(head));
                                RongIM.getInstance().refreshUserInfoCache(userInfo);
                            }
                        }
                        return null;
                    }
                }, true);

                RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {
                    @Override
                    public Message onSend(Message message) {
                        Map<String, Object> params = new HashMap<>();
                        params.put("userId", message.getSenderUserId());
                        params.put("nickName", MyApplication.getUserInfo().getName());
                        params.put("avatar", MyApplication.getUserInfo().getHeadPortrait());
                        String extra = new Gson().toJson(params);
                        message.setExtra(extra);
                        return message;
                    }

                    @Override
                    public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {
                        return false;
                    }
                });
                RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
                    @Override
                    public boolean onReceived(Message message, int i) {

                        TextMessage textMessage = (TextMessage) message.getContent();
//                        FileMessage fileMessage= (FileMessage) message.getContent();
//                        VoiceMessage voiceMessage= (VoiceMessage) message.getContent();
//                        ImageMessage imageMessage= (ImageMessage) message.getContent();
                        String extra = "";
                        if (textMessage != null) {
                            extra = textMessage.getExtra();
                        }
//                        if(fileMessage!=null){
//                            extra=fileMessage.getExtra();
//                        }
//                        if(imageMessage!=null){
//                            extra=imageMessage.getExtra();
//                        }
//                        if(voiceMessage!=null){
//                            extra=voiceMessage.getExtra();
//                        }
                        if (!TextUtils.isEmpty(extra)) {
                            try {

                                JSONObject jsonObject = new JSONObject(extra);
                                String userid = jsonObject.getString("userId");
                                String username = jsonObject.getString("nickName");
                                String avatar = jsonObject.getString("avatar");
                                avatar = avatar.replaceAll("\\\\", "");
                                String finalAvatar = avatar;
                                Log.e("extra", extra);
                                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                                    @Override
                                    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                                        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(userid, username, Uri.parse(finalAvatar));
                                        return userInfo;
                                    }
                                }, true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return true;
                    }
                });


                //startActivity(new Intent(Re, ChatActivity.class));
                Log.d("LoginActivity", "--onSuccess" + userid);
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("LoginActivity", "--onSuccess" + errorCode.getMessage() + errorCode.getValue());
            }
        });
    }
}
