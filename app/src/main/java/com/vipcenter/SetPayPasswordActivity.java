package com.vipcenter;

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
import com.http.HttpProxy;
import com.http.ICallBack;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pay_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
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