package com.example.yunchebao.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.vipcenter.BindPhoneActivity;
import com.vipcenter.model.UserInfo;


import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private static final int WX_LOGIN = 1;
    private static IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iwxapi =MyApplication.mWxApi;
        iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.d("BaseResp", "onReq: 你妹夫");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        /*微信登录为getType为1，分享网页为2*/

        Log.d("BaseResp", "" + baseResp.getType());

        if (baseResp.getType() == WX_LOGIN) {
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    requestOpenId("wx13acff5b460a0164", "27ea7c24ddd659fad3d02b831f8013d4", code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    ToastUtil.showToast(this, "拒绝授权");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    ToastUtil.showToast(this, "用户取消");
                    finish();
                    break;
            }
        }
        if (baseResp.getType() == 2) {//网页分享
            if (baseResp.errCode == 0) {
                //ToaskUtil.showToast(this, "分享成功");
            } else {
                //ToaskUtil.showToast(this, "分享失败");
            }
            finish();
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
                       // Toast.makeText(RegisterActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                       // getData();
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(3, intent);
                        finish();
                    }
                    else if(code==7000){
                        Intent  intent=new Intent(WXEntryActivity.this,BindPhoneActivity.class);
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



    public void requestOpenId(String appid, String secret, String code) {
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        HttpProxy.obtain().get(url, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject object = new JSONObject(result);
                    String openid = object.getString("openid");
                    if (!TextUtils.isEmpty(openid)) {
                        EventBus.getDefault().post(openid);
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

}
