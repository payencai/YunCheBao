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
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPayPasswordActivity extends AppCompatActivity {
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.et_code)
    EditText et_code;
    int count=60;
    TimeCount mTimeCount;
    boolean isSend=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_password);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ButterKnife.bind(this);
        initView();
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_submit.setEnabled(false);
            tv_submit.setTextColor(getResources().getColor(R.color.gray_99));
            count--;
            //倒计时的过程中回调该函数
            tv_submit.setText(count + "s");
        }

        @Override
        public void onFinish() {
            count=60;
            tv_submit.setText("重新获取");
            tv_submit.setEnabled(true);
            tv_submit.setTextColor(getResources().getColor(R.color.black_33));
            //倒计时结束时回调该函数
        }
    }
    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTimeCount = new TimeCount(60000, 1000);
        tv_phone.setText("请验证手机号"+ MyApplication.getUserInfo().getUsername());
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByType(MyApplication.getUserInfo().getUsername());
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSend){
                    ToastUtil.showToast(SetPayPasswordActivity.this,"请先去发送验证码");
                    return;
                }
                String code=et_code.getEditableText().toString();
                Bundle bundle=new Bundle();
                bundle.putString("code",code);
                if(!TextUtils.isEmpty(code))
                ActivityAnimationUtils.commonTransition(SetPayPasswordActivity.this, SetPayNextActivity.class, ActivityConstans.Animation.FADE,bundle);
                finish();
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
                        mTimeCount.start();
                        isSend=true;
                        Toast.makeText(SetPayPasswordActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        Toast.makeText(SetPayPasswordActivity.this, "获取验证码异常", Toast.LENGTH_LONG).show();
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
