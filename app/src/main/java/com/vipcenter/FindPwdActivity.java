package com.vipcenter;

import android.os.CountDownTimer;
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
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindPwdActivity extends AppCompatActivity {
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    int count=60;
    TimeCount mTimeCount;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        mTimeCount = new TimeCount(60000, 1000);
        phone=getIntent().getStringExtra("phone");
        initView();
    }

    private void initView() {
        tv_phone.setText("当前手机号："+phone);
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setEnabled(false);
            tv_send.setTextColor(getResources().getColor(R.color.gray_99));
            count--;
            //倒计时的过程中回调该函数
            tv_send.setText(count + "s");
        }

        @Override
        public void onFinish() {
            count=60;
            tv_send.setText("重新获取");
            tv_send.setEnabled(true);
            tv_send.setTextColor(getResources().getColor(R.color.black_33));
            //倒计时结束时回调该函数
        }
    }
    private void getCodeByType( String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", phone);
        params.put("type", 2);
        HttpProxy.obtain().get(PlatformContans.User.getVeriCode, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        mTimeCount.start();
                        Toast.makeText(FindPwdActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        Toast.makeText(FindPwdActivity.this, "获取验证码异常", Toast.LENGTH_LONG).show();
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
    @OnClick({R.id.back,R.id.tv_send,R.id.tv_submit})
    void OnClick(View view ){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.tv_submit:
                String password=et_pwd.getEditableText().toString();
                String code=et_code.getEditableText().toString();
                if(TextUtils.isEmpty(password)){
                    ToastUtil.showToast(this,"请输入密码");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showToast(this,"请输入验证码");
                    return;
                }
                updatePwd(password,code);
                break;
            case R.id.tv_send:
                getCodeByType(phone);
                break;
        }
    }
    private void updatePwd(String password,String code){
        Map<String,Object> params=new HashMap<>();
        params.put("username",phone);
        params.put("password",password);
        params.put("code",code);
        HttpProxy.obtain().post(PlatformContans.User.updatePwd, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if(code==0){
                        ToastUtil.showToast(FindPwdActivity.this,"修改成功");
                        finish();
                    }else{
                        ToastUtil.showToast(FindPwdActivity.this,msg);
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
