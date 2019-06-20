package com.example.yunchebao.maket;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.adapter.GoodsOrderChildAdapter;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tool.BottomMenuDialog;
import com.tool.listview.PersonalListView;
import com.vipcenter.CheckLogisticsActivity;
import com.vipcenter.OrderCommentSubmitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

public class GoodsOrderDetailActivity extends AppCompatActivity {
    PhoneOrderEntity mPhoneOrderEntity;
    @BindView(R.id.lv_goods)
    PersonalListView lv_goods;
    @BindView(R.id.tv_ordernum)
    TextView tv_ordernum;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.tv_goodsnum)
    TextView tv_goodsnum;
    @BindView(R.id.total1)
    TextView total1;
    @BindView(R.id.total2)
    TextView total2;
    @BindView(R.id.total3)
    TextView total3;
    @BindView(R.id.total4)
    TextView total4;
    @BindView(R.id.tv_pay)
    SuperTextView tv_pay;
    @BindView(R.id.tv_cancel)
    SuperTextView tv_cancel;
    @BindView(R.id.tv_wuliu)
    SuperTextView tv_wuliu;
    @BindView(R.id.tv_wuliu2)
    SuperTextView tv_wuliu2;
    @BindView(R.id.tv_confirm)
    SuperTextView tv_confirm;
    @BindView(R.id.tv_comment)
    SuperTextView tv_comment;
    @BindView(R.id.rl_contacts)
    RelativeLayout rl_contacts;
    @BindView(R.id.rl_phone)
    RelativeLayout rl_phone;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv_delete)
    SuperTextView tv_delete;
    @BindView(R.id.tv_lianxi)
    SuperTextView tv_lianxi;
    @BindView(R.id.ll_state1)
    LinearLayout ll_state1;
    @BindView(R.id.ll_state2)
    LinearLayout ll_state2;
    @BindView(R.id.ll_state3)
    LinearLayout ll_state3;
    @BindView(R.id.ll_state4)
    LinearLayout ll_state4;
    int type = 1;
    private static final int SDK_PAY_FLAG = 1;
    GoodsOrderChildAdapter mGoodsOrderChildAdapter;
    private String[] items = new String[]{"我不想买了", "信息填写错误，重新拍", "卖家缺货", "同城见面交易", "其他原因"};
    private BottomMenuDialog bottomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        initView();
    }

    private void showCancelDialog() {
        Dialog dialog = new Dialog(this, R.style.dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_cancel, null);
        TextView tv_refused = (TextView) view.findViewById(R.id.tv_refused);
        TextView tv_agree = (TextView) view.findViewById(R.id.tv_agree);
        tv_refused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelOrder();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void showFinishDialog() {
        Dialog dialog = new Dialog(this, R.style.dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_finish, null);
        TextView tv_refused = (TextView) view.findViewById(R.id.tv_refused);
        TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        tv_refused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GoodsOrderDetailActivity.this, OrderCommentSubmitActivity.class);
                intent.putExtra("data",mPhoneOrderEntity);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void initView(){
        List<PhoneOrderEntity.ItemListBean>itemListBeans=new ArrayList<>();
        for (int i = 0; i <mPhoneOrderEntity.getItemList().size() ; i++) {
            PhoneOrderEntity.ItemListBean itemListBean=mPhoneOrderEntity.getItemList().get(i);
            itemListBean.setState(mPhoneOrderEntity.getState());
            itemListBeans.add(itemListBean);
        }
        mGoodsOrderChildAdapter=new GoodsOrderChildAdapter(this,itemListBeans);
        lv_goods.setAdapter(mGoodsOrderChildAdapter);
        tv_ordernum.setText("订单号: "+mPhoneOrderEntity.getOrderNo());
        tv_goodsnum.setText("共"+mPhoneOrderEntity.getNumber()+"件商品");
        total1.setText("￥"+mPhoneOrderEntity.getTotal());
        total2.setText("￥"+mPhoneOrderEntity.getTotal());
        total3.setText("￥"+mPhoneOrderEntity.getTotal());
        total4.setText("￥"+mPhoneOrderEntity.getTotal());
        switch(mPhoneOrderEntity.getState()){
            case 0:
                tv_status.setText("交易取消");
                tv_delete.setVisibility(View.VISIBLE);
                break;

            case 1:
                tv_status.setText("待付款");
                ll_state1.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_status.setText("待发货");
                ll_state2.setVisibility(View.VISIBLE);
                break;
            case 3:
                tv_status.setText("待收货");
                ll_state3.setVisibility(View.VISIBLE);
                break;
            case 4:
                if(mPhoneOrderEntity.getIsComment()==0){
                    tv_status.setText("待评价");
                    ll_state4.setVisibility(View.VISIBLE);
                }else{
                    tv_status.setText("交易成功");
                    tv_delete.setVisibility(View.VISIBLE);
                }

                break;
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelDialog();
            }
        });
        tv_lianxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(GoodsOrderDetailActivity.this,mPhoneOrderEntity.getUserId(), mPhoneOrderEntity.getShopName());
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog(mPhoneOrderEntity.getId());
            }
        });
        rl_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(GoodsOrderDetailActivity.this,mPhoneOrderEntity.getUserId(), mPhoneOrderEntity.getShopName());
            }
        });
        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(mPhoneOrderEntity.getMerchTelephone());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //确认收货
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });
        tv_wuliu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsOrderDetailActivity.this, CheckLogisticsActivity.class);
                intent.putExtra("data",mPhoneOrderEntity);
                startActivity(intent);
            }
        });
        tv_wuliu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsOrderDetailActivity.this, CheckLogisticsActivity.class);
                intent.putExtra("data",mPhoneOrderEntity);
                startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsOrderDetailActivity.this, OrderCommentSubmitActivity.class);
                intent.putExtra("data",mPhoneOrderEntity);
                startActivity(intent);
            }
        });
    }
    private void payByWechat(String data) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", data);
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
                PayTask alipay = new PayTask(GoodsOrderDetailActivity.this);
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

    private void payByAlipay(String data) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", data);
        HttpProxy.obtain().post(PlatformContans.Pay.babyMerchantOrderPay, MyApplication.token, params, new ICallBack() {
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

    private void showPayDialog(String data) {

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
                dialog.dismiss();
                if (type == 1) {
                    payByWechat(data);
                } else {
                    payByAlipay(data);
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
                        ToastUtil.showToast(GoodsOrderDetailActivity.this,"支付成功");
                        finish();
                    }
                    break;
                }
            }
        }

        ;
    };

    private void confirmOrder(){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",mPhoneOrderEntity.getId());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.finishOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt",result);
                ToastUtil.showToast(GoodsOrderDetailActivity.this,"操作成功");
                showFinishDialog();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void cancelOrder(){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",mPhoneOrderEntity.getId());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.cancelOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt",result);
                ToastUtil.showToast(GoodsOrderDetailActivity.this,"取消成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void alertCancelPanel() {
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(this);
        builder.setTitle("请选择取消订单的理由");
        for (int i = 0; i < items.length; i++) {
            builder.addMenu(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomDialog.dismiss();
                    cancelOrder();
                }
            });
        }
        bottomDialog = builder.create();
        bottomDialog.show();
    }


}
