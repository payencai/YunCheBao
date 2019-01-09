package com.vipcenter;

import android.content.Intent;
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
                    JSONObject data=object.getJSONObject("data");
                    int code = object.getInt("resultCode");
                    if(code==0){
                        UserInfo userInfo=new Gson().fromJson(data.toString(),UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        Intent intent=new Intent();
                        intent.putExtra("user",userInfo);
                        setResult(1,intent);
                        finish();
                    }
                    finish();

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
}
