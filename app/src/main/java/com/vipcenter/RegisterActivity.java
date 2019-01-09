package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.model.MyFriend;
import com.rongcloud.model.MyGroup;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.vipcenter.model.UserInfo;
import com.example.yunchebao.wxapi.WXEntryActivity;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Group;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.code)
    TextView codeBtn;
    @BindView(R.id.phoneNum)
    XEditText phoneNum;
    Tencent mTencent;
    private Context ctx;
    @BindView(R.id.iv_qq)
    ImageView iv_qq;
    @BindView(R.id.iv_wechat)
    ImageView iv_wechat;
    @BindView(R.id.code1)
    TextView code1;
    @BindView(R.id.code2)
    TextView code2;
    @BindView(R.id.code3)
    TextView code3;
    @BindView(R.id.code4)
    TextView code4;
    @BindView(R.id.code5)
    TextView code5;
    @BindView(R.id.code6)
    TextView code6;
    @BindView(R.id.codeNum)
    EditText codeNumEdit;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.sendphone)
    TextView sendphone;
    BaseUiListener mBaseUiListener = new BaseUiListener();
    com.tencent.connect.UserInfo mInfo;
    private IWXAPI iwxapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        mTencent = Tencent.createInstance("101536200", getApplicationContext());
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEventMainThread(String openid) {
         loginByWeChat(openid);
    }
    /**
     * 登录方法
     */
    private void QQLogin() {
        //如果session不可用，则登录，否则说明已经登录
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", mBaseUiListener);
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
                            String myid = myFriend.getMyid();
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
    private void initOpenIdAndToken(Object object) {
        //Toast.makeText(RegisterActivity.this, "fdfgg", Toast.LENGTH_SHORT).show();
        JSONObject jb = (JSONObject) object;
        Log.e("jb",jb.toString());
        try {
            String openID = jb.getString("openid");  //openid用户唯一标识
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
            loginByQQ(openID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loginByQQ(final String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("qqId", openid);
        HttpProxy.obtain().post(PlatformContans.User.loginByQQ, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        getData();
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(1, intent);
                        finish();
                    }
                    else if(code==7001){
                       Intent  intent=new Intent(RegisterActivity.this,BindPhoneActivity.class);
                       intent.putExtra("openid",openid+"1");
                       startActivity(intent);
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

    private void loginByPhone(String phone, String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", phone);
        params.put("code", code);
        Log.e("params", params.toString());
        HttpProxy.obtain().post(PlatformContans.User.loginByPhone, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);
                    JSONObject data = object.getJSONObject("data");
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        getData();
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(1, intent);
                        finish();
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
    /**
     * 微信登录
     */
    private IWXAPI api;
    String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + "access" + "&openid=" + "openId";
    private void setWXLogin() {
        api= WXEntryActivity.getWXAPI();
        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            api.sendReq(req);
        } else {
            Toast.makeText(this, "您尚未安装微信", Toast.LENGTH_SHORT).show();
        }
    }

    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            initOpenIdAndToken(o);
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("error", uiError.errorMessage + uiError.errorCode + uiError.errorDetail);
        }

        @Override
        public void onCancel() {
            Log.e("error", "error");
        }
    }


    private void loginByWeChat(final String openid) {
        Map<String, Object> params = new HashMap<>();
        params.put("wxId", openid);
        HttpProxy.obtain().post(PlatformContans.User.loginByWechat, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = object.getJSONObject("data");
                         Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        getData();
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(3, intent);
                        finish();
                    }
                    else if(code==7000){
                        Intent  intent=new Intent(RegisterActivity.this,BindPhoneActivity.class);
                        intent.putExtra("openid",openid+"2");
                        startActivity(intent);
                        finish();
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





    private void initView() {
        ButterKnife.bind(this);
        ctx = this;
//        api = WXAPIFactory.createWXAPI(this, "", true);
//        api.registerApp("");

        iv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWXLogin();
            }
        });
        phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.toString() != null && !s.toString().equals("")) {
                    codeBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
                } else {
                    codeBtn.setTextColor(ContextCompat.getColor(ctx, R.color.gray_99));
                }
            }
        });
        codeNumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (codeNumEdit.getText() != null && !codeNumEdit.getText().toString().equals("")) {
                    String code = codeNumEdit.getText().toString();
                    if (code.length() > 5) {
                        code6.setText(s.charAt(5) + "");
                        if (ty == 1) {
                            Intent intent = new Intent(RegisterActivity.this, SetPasswordActivity.class);
                            intent.putExtra("code", code);
                            intent.putExtra("phone", sendphone.getText().toString());
                            startActivityForResult(intent, 0);
                        } else if (ty == 3) {
                            //finish();
                            loginByPhone(sendphone.getText().toString(), code);
                        }
                    } else {
                        code6.setText("");
                    }
                    if (code.length() > 4) {
                        code5.setText(s.charAt(4) + "");
                    } else {
                        code5.setText("");
                    }
                    if (code.length() > 3) {
                        code4.setText(s.charAt(3) + "");
                    } else {
                        code4.setText("");
                    }
                    if (code.length() > 2) {
                        code3.setText(s.charAt(2) + "");
                    } else {
                        code3.setText("");
                    }
                    if (code.length() > 1) {
                        code2.setText(s.charAt(1) + "");
                    } else {
                        code2.setText("");
                    }
                    if (code.length() > 0) {
                        code1.setText(s.charAt(0) + "");
                    } else {
                        code1.setText("");
                    }
                } else {
                    code1.setText("");
                    code2.setText("");
                    code3.setText("");
                    code4.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RegisterActivity.this, LoginByaccountActivity.class), 1);
            }
        });
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phoneNum.getTrimmedString();
                sendphone.setText(phone);
                checkIsExists(phone);
            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QQLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.e("result",resultCode+"");
        Tencent.onActivityResultData(requestCode, resultCode, data, mBaseUiListener);
        //Log.e("data", data.getDataString());
        if (requestCode == 1 || requestCode == 0) {
            if (data != null) {
                setResult(2, data);
                getData();
                finish();
            }
        }

    }




    int ty;

    private void checkIsExists(final String account) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", account);
        HttpProxy.obtain().get(PlatformContans.User.isExitsAccount, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    String data = object.getString("data");
                    int code = object.getInt("resultCode");

                    if (phoneNum.getText() != null && !phoneNum.getText().toString().equals("")) {
                        findViewById(R.id.view1).setVisibility(View.GONE);
                        findViewById(R.id.view2).setVisibility(View.VISIBLE);
                        showSoftInputFromWindow(codeNumEdit);
                    }
                    if (TextUtils.equals("0", data)) {
                        ty = 1;
                        //注册,并且登录
                        getCodeByType(ty, account);
                        //startActivity(new Intent(RegisterActivity.this,SetPasswordActivity.class));
                    } else {
                        ty = 3;//登录
                        getCodeByType(ty, account);
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

    private void getCodeByType(int type, String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", phone);
        params.put("type", type);
        HttpProxy.obtain().get(PlatformContans.User.getVeriCode, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(RegisterActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        //登录
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


    /**
     * EditText获取焦点并显示软键盘
     */
    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

    }

    @OnClick({R.id.cancelBtn, R.id.code, R.id.codeLay, R.id.codeGetAgain})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.cancelBtn:
                onBackPressed();
                break;
            case R.id.code:
                break;
            case R.id.codeGetAgain:
                getCodeByType(ty, phoneNum.getText().toString());
                codeNumEdit.setText("");
                break;
            case R.id.codeLay:
                showSoftInputFromWindow(codeNumEdit);
                break;
        }
    }
}
