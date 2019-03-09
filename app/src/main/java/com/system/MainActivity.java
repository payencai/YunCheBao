package com.system;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.rongcloud.model.MyFriend;
import com.rongcloud.model.MyGroup;
import com.system.fragment.AnotherBabyFragment;
import com.system.fragment.BBCircleFragment;
import com.system.fragment.CheyiFragment;
import com.system.fragment.HomeFragment;
import com.system.fragment.BaikeFragment;
import com.system.fragment.MallFragment;
import com.system.fragment.NewBabyFragment;
import com.system.fragment.NewBaikeFragment;
import com.tool.FitStateUI;
import com.vipcenter.LoginByaccountActivity;
import com.vipcenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.callkit.util.SPUtils;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.FileMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class MainActivity extends NoHttpFragmentBaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    HomeFragment fragment1;
    CheyiFragment fragment2;
    AnotherBabyFragment fragment3;
    NewBaikeFragment fragment4;
    MallFragment fragment5;

    boolean isFrist = true;
    private FragmentManager fm;
    private List<Fragment> fragments;

    View tab1, tab2, tab3, tab4, tab5;
    private ImageView iv_tab1;
    private ImageView iv_tab2;
    private ImageView iv_tab3;
    private ImageView iv_tab4;
    private ImageView iv_tab5;
    private TextView tv_tab1;
    private TextView tv_tab2;
    private TextView tv_tab3;
    private TextView tv_tab4;
    private TextView tv_tab5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FitStateUI.setImmersionStateMode(this);
        setContentView(R.layout.activity_main);

        //openGPS(this);
        autoLogin();
    }

    private void autoLogin() {
        String phone = (String) SPUtils.get(MainActivity.this, "phone", "");
        String pwd = (String) SPUtils.get(MainActivity.this, "pwd", "");
        if (!TextUtils.isEmpty(phone)) {
            loginByPwd(phone, pwd);
        } else {
            initview();
        }
    }

    private void loginByPwd(String account, String pwd) {
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
                    JSONObject data = object.getJSONObject("data");
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        //Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        initview();
                        getData();

                    } else {
                        initview();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    initview();
                }

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    //关闭时解除监听器
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }

    private void initview() {
        tab1 = findViewById(R.id.main_fl_1);
        tab2 = findViewById(R.id.main_fl_2);
        tab3 = findViewById(R.id.main_fl_3);
        tab4 = findViewById(R.id.main_fl_4);
        tab5 = findViewById(R.id.main_fl_5);

        iv_tab1 = (ImageView) findViewById(R.id.main_iv_1);
        iv_tab2 = (ImageView) findViewById(R.id.main_iv_2);
        iv_tab3 = (ImageView) findViewById(R.id.main_iv_3);
        iv_tab4 = (ImageView) findViewById(R.id.main_iv_4);
        iv_tab5 = (ImageView) findViewById(R.id.main_iv_5);
        tv_tab1 = (TextView) findViewById(R.id.main_tv_1);
        tv_tab2 = (TextView) findViewById(R.id.main_tv_2);
        tv_tab3 = (TextView) findViewById(R.id.main_tv_3);
        tv_tab4 = (TextView) findViewById(R.id.main_tv_4);
        tv_tab5 = (TextView) findViewById(R.id.main_tv_5);

        fm = getSupportFragmentManager();
        fragment1 = new HomeFragment();
        fragment2 = new CheyiFragment();
        fragment3 = new AnotherBabyFragment();
        fragment4 = new NewBaikeFragment();
        fragment5 = new MallFragment();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        for (Fragment fragment : fragments) {
            fm.beginTransaction().add(R.id.main_frame, fragment).commit();
        }
        //显示主页
        resetStateForTagbar(R.id.main_fl_1);
        hideAllFragment();
        showFragment(0);
        initListener();
    }

    private void initListener() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);
        tab5.setOnClickListener(this);
    }


    private void resetStateForTagbar(int viewId) {
        clearTagbarState();
        if (viewId == R.id.main_fl_1) {
            tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab1.setImageResource(R.mipmap.home_icon_1y);
            return;
        }
        if (viewId == R.id.main_fl_2) {
            tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab2.setImageResource(R.mipmap.home_icon_2y);
            return;
        }
        if (viewId == R.id.main_fl_3) {
            tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab3.setImageResource(R.mipmap.home_icon_3y);
            return;
        }
        if (viewId == R.id.main_fl_4) {
            tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab4.setImageResource(R.mipmap.home_icon_4y);
            return;
        }
        if (viewId == R.id.main_fl_5) {
            tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            iv_tab5.setImageResource(R.mipmap.home_icon_5y);
            return;
        }
    }

    private void clearTagbarState() {
        tv_tab1.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab1.setImageResource(R.mipmap.home_icon_1);
        tv_tab2.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab2.setImageResource(R.mipmap.home_icon_2);
        tv_tab3.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab3.setImageResource(R.mipmap.home_icon_3);
        tv_tab4.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab4.setImageResource(R.mipmap.home_icon_4);
        tv_tab5.setTextColor(ContextCompat.getColor(this, R.color.black_33));
        iv_tab5.setImageResource(R.mipmap.home_icon_5);
    }

    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    private void showFragment(int position) {
        fm.beginTransaction().show(fragments.get(position)).commitAllowingStateLoss();
    }

    boolean isEmpty = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_fl_1:
                //状态重置
                resetStateForTagbar(R.id.main_fl_1);
                hideAllFragment();
                showFragment(0);
                break;

            case R.id.main_fl_2:
                //状态重置
                resetStateForTagbar(R.id.main_fl_2);
                hideAllFragment();
                showFragment(1);
                break;
            case R.id.main_fl_3:
                //状态重置
                resetStateForTagbar(R.id.main_fl_3);
                hideAllFragment();
                showFragment(2);

                break;
            case R.id.main_fl_4:
                //状态重置
                resetStateForTagbar(R.id.main_fl_4);
                hideAllFragment();
                showFragment(3);
                break;
            case R.id.main_fl_5:
                //状态重置
                resetStateForTagbar(R.id.main_fl_5);
                hideAllFragment();
                showFragment(4);
                //fragment5.onResume();
                break;

            default:
                break;
        }
    }

    private void getContacts() {
        LitePal.deleteAll(MyFriend.class);
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getMyFriendList, userinfo.getToken(), new ICallBack() {
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
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getCrowdsList, userinfo.getToken(), new ICallBack() {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            Toast.makeText(this, "GPS模块已开启", Toast.LENGTH_SHORT).show();
            fragment1.startLocate();
        }

    }
}
