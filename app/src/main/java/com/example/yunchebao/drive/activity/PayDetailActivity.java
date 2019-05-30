package com.example.yunchebao.drive.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.model.ReplaceOrder;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayDetailActivity extends AppCompatActivity {
    ReplaceOrder mReplaceOrder;
    @BindView(R.id.tv_driver)
    TextView tv_driver;
    @BindView(R.id.et_money)
    TextView et_money;
    int type = 1;
    private static final int SDK_PAY_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mReplaceOrder= (ReplaceOrder) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        tv_driver.setText(mReplaceOrder.getDriverName());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @OnClick({R.id.tv_pay})
    void onClcik(View view){
        switch (view.getId()){
            case R.id.tv_pay:
                //startActivity(new Intent(PayDetailActivity.this,AddOrderCommentActivity.class));
                showPayDialog();
                break;
        }
    }
    private void payByWechat() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", mReplaceOrder.getId());
        HttpProxy.obtain().post(PlatformContans.WechatPay.babyMerchantOrderPay, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        WechatRes wechatRes = new Gson().fromJson(data.toString(), WechatRes.class);
                        startWechatPay(wechatRes);
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
    private void startWechatPay(WechatRes payReponse) {
        PayReq req = new PayReq(); //调起微信APP的对象
        req.appId = payReponse.getAppid();
        req.partnerId = payReponse.getPartnerid();
        req.prepayId = payReponse.getPrepayid();
        req.nonceStr = payReponse.getNoncestr();
        req.timeStamp = payReponse.getTimestamp();
        req.packageValue = payReponse.getPackageX(); //Sign=WXPay
        req.sign = payReponse.getSign();
        MyApplication.mWxApi.sendReq(req); //发送调起微信的请求
    }

    private void startAlipay(String orderId) {
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(PayDetailActivity.this);
                Map<String, String> result = alipay.payV2(orderId, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void payByAlipay(String  price) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId",mReplaceOrder.getId());
        params.put("price",price);
        HttpProxy.obtain().post(PlatformContans.Pay.substituteDrivingPay, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String data = jsonObject.getString("data");
                        startAlipay(data);
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

    private void showPayDialog() {

        Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.show();
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_selectpay, null);
        RelativeLayout rl_wechat = (RelativeLayout) contentView.findViewById(R.id.rl_wechat);
        RelativeLayout rl_alipay = (RelativeLayout) contentView.findViewById(R.id.rl_alipay);
        SuperTextView tv_confirm = (SuperTextView) contentView.findViewById(R.id.tv_confirm);
        ImageView iv_wechat = (ImageView) contentView.findViewById(R.id.iv_choose);
        ImageView iv_alipay = (ImageView) contentView.findViewById(R.id.iv_choose2);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price=et_money.getEditableText().toString();
                dialog.dismiss();
                if (type == 1) {
                    payByWechat();
                } else {
                    payByAlipay(price);
                }
            }
        });
        rl_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                iv_wechat.setVisibility(View.VISIBLE);
                iv_alipay.setVisibility(View.GONE);
            }
        });
        rl_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                iv_alipay.setVisibility(View.VISIBLE);
                iv_wechat.setVisibility(View.GONE);
            }
        });
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT; //使用这种方式更改了dialog的框宽
        window.setAttributes(params);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e("code", resultStatus);
                        ToastUtil.showToast(PayDetailActivity.this,"支付成功");
                        Intent intent =new Intent(PayDetailActivity.this,AddOrderCommentActivity.class);
                        intent.putExtra("id",mReplaceOrder.getId());
                        startActivity(intent);
                        finish();
                    }
                    break;
                }
            }
        }

        ;
    };

}
