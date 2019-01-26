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

import com.application.MyApplication;
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
import io.rong.callkit.util.SPUtils;

public class LoginByaccountActivity extends AppCompatActivity {
    @BindView(R.id.tv_login)
    TextView tv_change;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.pwd)
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_byaccount);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
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
                    JSONObject data=object.getJSONObject("data");
                    int code = object.getInt("resultCode");
                    if(code==0){
                        Toast.makeText(LoginByaccountActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        UserInfo userInfo =new Gson().fromJson(data.toString(),UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        SPUtils.put(LoginByaccountActivity.this,"phone",account);
                        SPUtils.put(LoginByaccountActivity.this,"pwd",pwd);
                        Intent intent=new Intent();
                        intent.putExtra("user",userInfo);
                        setResult(5,intent);
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
