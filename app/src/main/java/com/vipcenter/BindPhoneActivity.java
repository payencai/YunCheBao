package com.vipcenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.xw.repo.XEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import go.error;

public class BindPhoneActivity extends AppCompatActivity {
    String openid;
    @BindView(R.id.phoneNum)
    XEditText mXEditText;
    @BindView(R.id.next)
    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openid = getIntent().getStringExtra("openid");
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mXEditText.getEditableText().toString();
                checkIsExists(phone);

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkIsExists(final String account) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", account);
        HttpProxy.obtain().get(PlatformContans.User.isExitsAccount, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        String data = object.getString("data");
                        if (data.equals("0")) {
                            Intent intent = new Intent(BindPhoneActivity.this, UnregisterBindActivity.class);
                            intent.putExtra("id", openid);
                            intent.putExtra("phone", account);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(BindPhoneActivity.this, RegisteredBindActivity.class);
                            intent.putExtra("id", openid);
                            intent.putExtra("phone", account);
                            startActivityForResult(intent, 1);
                        }
                    }else{
                        String msg=object.getString("message");
                        ToastUtil.showToast(BindPhoneActivity.this,msg);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode >= 3 && data != null) {
            setResult(1, data);
            finish();
        }
    }
}
