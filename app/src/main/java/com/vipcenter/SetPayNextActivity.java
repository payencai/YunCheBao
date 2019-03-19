package com.vipcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tool.view.PasswordView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPayNextActivity extends AppCompatActivity implements PasswordView.PasswordListener{
    @BindView(R.id.passwordView)
    PasswordView passwordView;
    String code;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_next);
        code=getIntent().getExtras().getString("code");
        phone= MyApplication.getUserInfo().getUsername();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        passwordView.setMode(PasswordView.Mode.RECT);
        passwordView.setPasswordListener(this);
        //passwordView.setPasswordLength(6);
        passwordView.setCursorEnable(true);


    }

    @Override
    public void passwordChange(String changeText) {

    //   Log.e("password",changeText+"")  ;
    }

    @Override
    public void passwordComplete() {
         Log.e("password",passwordView.getPassword())  ;
        // finish();
        setPassword(passwordView.getPassword());
    }
    private void setPassword(String password){
        Map<String,Object> params=new HashMap<>();
        params.put("telephone",phone);
        params.put("password",password);
        params.put("code",code);
        HttpProxy.obtain().post(PlatformContans.User.updatePayPassword, MyApplication.token, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(SetPayNextActivity.this,"设置成功");
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


    @Override
    public void keyEnterPress(String password, boolean isComplete) {
       // Log.e("password",password+"")  ;
    }
}
