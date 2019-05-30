package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class UnregisterBindActivity extends AppCompatActivity {
    String openid;
    String phone;
    @BindView(R.id.sendphone)
    TextView sendphone;
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
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.codeLay)
    LinearLayout codeLay;
    @BindView(R.id.login)
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openid = getIntent().getExtras().getString("id");
        phone = getIntent().getExtras().getString("phone");
        getCodeByType(1, phone);
        setContentView(R.layout.activity_unregister_bind);
        ButterKnife.bind(this);
        sendphone.setText(phone);
        codeLay.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           showSoftInputFromWindow(codeNumEdit);
                                       }
                                   }
        );
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeNumEdit.getText().toString();
                if(code.length()<4){
                    return;
                }
                if(TextUtils.isEmpty(pwd.getEditableText().toString())){
                    return;
                }
                bindAndLogin();
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
                        if(TextUtils.isEmpty(pwd.getEditableText().toString())){
                            return;
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
                    code5.setText("");
                    code6.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void showSoftInputFromWindow(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

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
                        Toast.makeText(UnregisterBindActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
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

    private void bindAndLogin() {
        Map<String, Object> params = new HashMap<>();
        params.put("username", phone);
        params.put("nickName", phone);
        params.put("nickName", codeNumEdit.getEditableText().toString());
        params.put("headPortrait", "http://1234.jpg");
        params.put("password", pwd.getEditableText().toString());
        params.put("qqId", openid);
        HttpProxy.obtain().post(PlatformContans.User.bindQQ, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    // Toast.makeText(RegisteredBindActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = object.getJSONObject("data");
                        Toast.makeText(UnregisterBindActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        UserInfo userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        MyApplication.setUserInfo(userInfo);
                        MyApplication.setIsLogin(true);
                        Intent intent = new Intent();
                        intent.putExtra("user", userInfo);
                        setResult(3, intent);
                        finish();
                    } else {
                        String message = object.getString("message");
                        Toast.makeText(UnregisterBindActivity.this, message, Toast.LENGTH_SHORT).show();
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
