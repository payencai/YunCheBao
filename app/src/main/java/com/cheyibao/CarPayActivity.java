package com.cheyibao;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodsPayActivity;
import com.payencai.library.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarPayActivity extends AppCompatActivity {


    String orderId;
    int type=3;
    @BindView(R.id.ll_Alipay)
    LinearLayout ll_Alipay;
    @BindView(R.id.ll_wechat)
    LinearLayout ll_wechat;
    @BindView(R.id.ll_payMember)
    LinearLayout ll_payMember;
    @BindView(R.id.iv_alipay)
    ImageView iv_alipay;
    @BindView(R.id.iv_wechat)
    ImageView iv_wechat;
    @BindView(R.id.iv_member)
    ImageView iv_member;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.price1)
    TextView price1;
    @BindView(R.id.price2)
    TextView price2;
    String money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_pay);
        orderId=getIntent().getStringExtra("orderid");
        money=getIntent().getStringExtra("money");
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e("code",resultStatus);
                        ToastUtil.showToast(CarPayActivity.this,"支付成功");
                        finish();
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        //showAlert(PayDemoActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
            }
        };
    };

    private static final int SDK_PAY_FLAG = 1;
    private void alipay(final String orderInfo){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(CarPayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
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
    private void initView(){
        price1.setText("￥"+money);
        price2.setText("￥"+money);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(CarPayActivity.this,"支付成功！");
                finish();

//                switch (type){
//                    case 1:
//                        getMemberCardOrder(orderId);
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        getAlipayOrder(orderId);
//                        break;
//                }
            }
        });
        ll_Alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_member.setImageResource(R.drawable.btn_unselected);
                iv_wechat.setImageResource(R.drawable.btn_unselected);
                iv_alipay.setImageResource(R.drawable.btn_selected);
                type=3;
            }
        });
        ll_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_member.setImageResource(R.drawable.btn_unselected);
                iv_wechat.setImageResource(R.drawable.btn_selected);
                iv_alipay.setImageResource(R.drawable.btn_unselected);
                type=2;
            }
        });
        ll_payMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_member.setImageResource(R.drawable.btn_selected);
                iv_wechat.setImageResource(R.drawable.btn_unselected);
                iv_alipay.setImageResource(R.drawable.btn_unselected);
                type=1;
            }
        });
    }

    private void getAlipayOrder(String orderId){

        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        HttpProxy.obtain().post(PlatformContans.Pay.carOrderPay, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String sign=jsonObject.getString("data");
                        alipay(sign);

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
    private void getMemberCardOrder(String orderId){

        Map<String,Object> params=new HashMap<>();
        params.put("orderId",orderId);
        HttpProxy.obtain().post(PlatformContans.Pay.memberCardPay, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String sign=jsonObject.getString("data");

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