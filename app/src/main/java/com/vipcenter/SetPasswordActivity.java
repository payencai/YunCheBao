package com.vipcenter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.MyFriend;
import com.example.yunchebao.rongcloud.model.MyGroup;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
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
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class SetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.finish)
    TextView finish;
    String  code;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        Intent intent=getIntent() ;
        code=intent.getStringExtra("code");
        phone=intent.getStringExtra("phone");
        finish.setEnabled(false);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
               String str= s.toString();
               if(str.length()>0){
                   finish.setTextColor(getResources().getColor(R.color.yellow_26));
                   finish.setEnabled(true);
               }else{
                   finish.setTextColor(getResources().getColor(R.color.gray_99));
                   finish.setEnabled(false);
               }
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd=password.getText().toString();
                 if(!TextUtils.isEmpty(pwd)){
                     register(phone,code,pwd);
                 }else{
                     return;
                 }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void login(String phone,String code){
        Map<String, Object> params = new HashMap<>();
        params.put("username", phone);
        params.put("code", code);
        HttpProxy.obtain().post(PlatformContans.User.loginByPhone, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    Toast.makeText(SetPasswordActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);

                    int code = object.getInt("resultCode");
                    if(code==0){
                        JSONObject data=object.getJSONObject("data");
                        UserInfo userInfo=new Gson().fromJson(data.toString(),UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        getData();
                        Intent intent=new Intent();
                        intent.putExtra("user",userInfo);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{

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
    private void register(final String phone, final String code, String password){
        Map<String, Object> params = new HashMap<>();
        params.put("username", phone);
        params.put("password", password);
        params.put("code", code);
        HttpProxy.obtain().post(PlatformContans.User.userRegister, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    if(resultCode==0){
                        login(phone,code);
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
                        connect(MyApplication.getUserInfo().getHxPassword());
                        MyApplication.getDataSave().setDataList("friends", myFriends);

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
                                                    Log.e("data",s);
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
                                            },true
                );
                RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
                    @Override
                    public GroupUserInfo getGroupUserInfo(String s, String s1) {
                        return null;
                    }
                },true);
                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                        List<MyFriend> myFriends = LitePal.findAll(MyFriend.class);
                        Log.e("data",s);
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

                        TextMessage textMessage= (TextMessage) message.getContent();
//                        FileMessage fileMessage= (FileMessage) message.getContent();
//                        VoiceMessage voiceMessage= (VoiceMessage) message.getContent();
//                        ImageMessage imageMessage= (ImageMessage) message.getContent();
                        String extra="";
                        if(textMessage!=null){
                            extra=textMessage.getExtra();
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
                        if(!TextUtils.isEmpty(extra)){
                            try {

                                JSONObject jsonObject=new JSONObject(extra);
                                String userid=jsonObject.getString("userId");
                                String username=jsonObject.getString("nickName");
                                String avatar=jsonObject.getString("avatar");
                                avatar=avatar.replaceAll( "\\\\",  "");
                                String finalAvatar = avatar;
                                Log.e("extra",extra);
                                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                                    @Override
                                    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
                                        io.rong.imlib.model.UserInfo userInfo=new io.rong.imlib.model.UserInfo(userid,username,Uri.parse(finalAvatar));
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
