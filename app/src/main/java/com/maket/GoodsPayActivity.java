package com.maket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.OrderConfirmActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsPayActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_pay);
        orderId=getIntent().getStringExtra("orderid");
        ButterKnife.bind(this);
        initView();
    }


    private void initView(){
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case 1:
                        getMemberCardOrder(orderId);
                        break;
                    case 2:
                        break;
                    case 3:
                        getAlipayOrder(orderId);
                        break;
                }
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
        HttpProxy.obtain().post(PlatformContans.Pay.babyMerchantOrderPay, params, new ICallBack() {
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
