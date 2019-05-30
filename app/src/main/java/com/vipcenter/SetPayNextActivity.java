package com.vipcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.view.VerCodeInputView;
import com.gyf.immersionbar.ImmersionBar;
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
    @BindView(R.id.vi_code)
    VerCodeInputView vicode;
    String code;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_next);
        code=getIntent().getExtras().getString("code");
        phone= MyApplication.getUserInfo().getUsername();
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        passwordView.setMode(PasswordView.Mode.RECT);
        passwordView.setPasswordListener(this);
        //passwordView.setPasswordLength(6);
        passwordView.setCursorEnable(true);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vicode.setAutoWidth();
        vicode.setOnCompleteListener(new VerCodeInputView.OnCompleteListener() {
            @Override
            public void onComplete(String content) {
                //ToastUtil.showToast(SetPayNextActivity.this,content);
                setPassword(content);
            }
        });
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
                    String msg=jsonObject.getString("message");
                    if (code == 0) {
                        ToastUtil.showToast(SetPayNextActivity.this,"设置成功");
                         finish();
                    }else{
                        ToastUtil.showToast(SetPayNextActivity.this,msg);
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
