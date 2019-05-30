package com.vipcenter;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.tool.AccountUtil;
import com.vipcenter.model.UserInfo;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.callkit.util.SPUtils;

public class LoginByaccountActivity extends AppCompatActivity {
    @BindView(R.id.tv_login)
    TextView tv_change;
    @BindView(R.id.cancelBtn)
    ImageView cancelBtn;
    @BindView(R.id.iv_ask)
    ImageView iv_ask;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.pwd)
    EditText password;
    @BindView(R.id.iv_qq)
    ImageView iv_qq;
    @BindView(R.id.iv_wechat)
    ImageView iv_wechat;
    private IWXAPI iwxapi;
    Tencent mTencent;
    BaseUiListener mBaseUiListener = new BaseUiListener();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_byaccount);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.gray_ee).init();
        mTencent = Tencent.createInstance("101536200", getApplicationContext());
        initView();
    }
    private void initView(){
        iv_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWXLogin();
            }
        });
        iv_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QQLogin();
            }
        });
        iv_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=account.getEditableText().toString();
                if(TextUtils.isEmpty(phone)){
                    ToastUtil.showToast(LoginByaccountActivity.this,"请先输入手机号");
                    return;
                }
                Intent intent=new Intent(LoginByaccountActivity.this,FindPwdActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc=account.getEditableText().toString();
                String pwd=password.getEditableText().toString();
                if(!TextUtils.isEmpty(acc)&&!TextUtils.isEmpty(pwd))
                   loginByPwd(acc,pwd);
                else{
                    Toast.makeText(LoginByaccountActivity.this,"输入不能为空",Toast.LENGTH_SHORT).show();

                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
    private IWXAPI api;
    String getUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=" + "access" + "&openid=" + "openId";
    private void setWXLogin() {
        api= MyApplication.mWxApi;
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
                        Toast.makeText(LoginByaccountActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.token=userInfo.getToken();
                        MyApplication.setIsLogin(true);
                  //      getData();
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(3, intent);
                        finish();
                    }
                    else if(code==7000){
                        Intent  intent=new Intent(LoginByaccountActivity.this,BindPhoneActivity.class);
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
                 ToastUtil.showToast(LoginByaccountActivity.this,"网络出错");
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
                        Toast.makeText(LoginByaccountActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.token=userInfo.getToken();
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(1, intent);
                        finish();
                    }
                    else if(code==7001){
                        Intent  intent=new Intent(LoginByaccountActivity.this,BindPhoneActivity.class);
                        intent.putExtra("openid",openid+"1");
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showToast(LoginByaccountActivity.this,"网络出错");
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_jump, null);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(LoginByaccountActivity.this,IDCardCertificationActivity.class));
                finish();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent();
                //intent.putExtra("user", userInfo);
                setResult(5, intent);
                finish();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

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
                        SPUtils.put(LoginByaccountActivity.this,"phone",account);
                        SPUtils.put(LoginByaccountActivity.this,"pwd",pwd);
                        int isShow= (int) SPUtils.get(LoginByaccountActivity.this,"isShow",0);
                        if(isShow==0){
                            SPUtils.put(LoginByaccountActivity.this,"isShow",1);
                            showDialog();
                        }
                        else if(isShow==1){
                            Intent intent = new Intent();
                            intent.putExtra("user", userInfo);
                            setResult(5, intent);
                            finish();
                        }
                    }else{
                        Toast.makeText(LoginByaccountActivity.this,msg,Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showToast(LoginByaccountActivity.this,"网络出错");
            }
        });
    }
}
