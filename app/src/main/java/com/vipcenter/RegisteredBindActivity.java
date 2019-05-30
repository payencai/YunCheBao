package com.vipcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisteredBindActivity extends AppCompatActivity {
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.login)
    TextView login;
    String phone;
    String openid;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_bind);
        openid = getIntent().getExtras().getString("id");
        type = openid.substring(openid.length() - 1);
        openid = openid.substring(0, openid.length() - 1);
        phone = getIntent().getExtras().getString("phone");
        ButterKnife.bind(this);
        account.setText(phone);
        Log.e("openid",openid+","+type);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(account.getEditableText().toString())) {
                    return;
                }
                if (account.getEditableText().toString().length() < 11) {
                    return;
                }
                if (TextUtils.isEmpty(pwd.getEditableText().toString())) {
                    return;
                }
                bindAndLogin();
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void bindAndLogin() {
        String url = PlatformContans.User.bindQQ;
        Map<String, Object> params = new HashMap<>();
        params.put("username", account.getEditableText().toString());
        params.put("nickName", "云车" + account.getEditableText().toString().substring(7));
        params.put("headPortrait", "http://pic.616pic.com/ys_bnew_img/00/12/71/u3OyCgBP5v.jpg");
        params.put("password", pwd.getEditableText().toString());
        if (type.equals("1")) {
            url = PlatformContans.User.bindQQ;
            params.put("qqId", openid);
        } else if (type.equals("2")) {
            url = PlatformContans.User.bindWechat;
            params.put("wxId", openid);
        }
        Log.e("params",params.toString());
        HttpProxy.obtain().post(url, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    // Toast.makeText(RegisteredBindActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Toast.makeText(RegisteredBindActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(3, intent);
                        finish();
                    } else {
                        String message = object.getString("message");
                        Toast.makeText(RegisteredBindActivity.this, message, Toast.LENGTH_SHORT).show();
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
