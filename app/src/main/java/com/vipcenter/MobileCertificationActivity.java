package com.vipcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/3.
 */

public class MobileCertificationActivity extends NoHttpBaseActivity {
    @BindView(R.id.et_phone)
    TextView et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_confirm)
    SuperTextView tv_confirm;
    @BindView(R.id.sendcode)
    SuperTextView sendcode;
    TimeCount mTimeCount;
    int count=60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_certification_layout);
        initView();
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sendcode.setEnabled(false);
            sendcode.setTextColor(getResources().getColor(R.color.gray_99));
            count--;
            //倒计时的过程中回调该函数
            sendcode.setText(count + "s");
        }

        @Override
        public void onFinish() {
            count=60;
            sendcode.setText("重新获取");
            sendcode.setEnabled(true);
            sendcode.setTextColor(getResources().getColor(R.color.yellow_02));
            //倒计时结束时回调该函数
        }
    }
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "手机认证");
        ButterKnife.bind(this);
        et_phone.setText(MyApplication.getUserInfo().getUsername());
        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=et_phone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(MobileCertificationActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                mTimeCount.start();
                getCodeByType(phone);
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=et_code.getEditableText().toString();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(MobileCertificationActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    return;
                }
                unBindPhone();
            }
        });
        mTimeCount = new TimeCount(60000, 1000);
    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    private void unBindPhone(){
        Map<String,Object> params=new HashMap<>();
        params.put("telephone",et_phone.getText().toString());
        params.put("code",et_code.getEditableText().toString());
        HttpProxy.obtain().post(PlatformContans.User.relieveTelephone ,MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if(code==0){
                        startActivity(new Intent(MobileCertificationActivity.this,NewPhoneActivity.class));
                    }else{
                        ToastUtil.showToast(MobileCertificationActivity.this,msg);
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
    private void getCodeByType( String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", phone);
        params.put("type", 4);
        HttpProxy.obtain().get(PlatformContans.User.getVeriCode, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(MobileCertificationActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        Toast.makeText(MobileCertificationActivity.this, "获取验证码异常", Toast.LENGTH_LONG).show();
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

}
