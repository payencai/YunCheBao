package com.example.yunchebao.maket;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.model.GoodDetail;
import com.example.yunchebao.maket.model.GoodParam;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.vipcenter.AddressListActivity;
import com.vipcenter.ManaAddressActivity;
import com.vipcenter.model.PersonAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePayActivity extends AppCompatActivity {
    GoodParam mGoodParam;
    GoodParam.SecondSpecificationsBean mGoddsParamChild;
    PersonAddress personAddress;
    GoodDetail mGoodDetail;
    String money="";
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_default)
    TextView tv_default;
    @BindView(R.id.tv_totalmoney)
    TextView tv_totalmoney;
    @BindView(R.id.tv_addrname)
    TextView tv_addrname;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.addressLay)
    LinearLayout addressLay;
    @BindView(R.id.tv_shop)
    TextView tv_shop;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_param)
    TextView tv_param;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_remark)
    TextView et_remark;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    int count;
    int type=1;
    private static final int SDK_PAY_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pay);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mGoodParam= (GoodParam) bundle.getSerializable("param");
            mGoddsParamChild= (GoodParam.SecondSpecificationsBean) bundle.getSerializable("child");
            mGoodDetail= (GoodDetail) bundle.getSerializable("detail");
            count=bundle.getInt("count");
        }

        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        initView();
    }
    private void initView(){
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_name.setText(mGoodDetail.getName());
        tv_shop.setText(mGoodDetail.getBabyMerchantName());
        tv_param.setText(mGoodParam.getSpecificationsValue()+"*"+mGoddsParamChild.getSpecificationsValue());
        tv_count.setText("x"+count);
        tv_total.setText("￥"+(count*mGoddsParamChild.getPrice()));
        tv_totalmoney.setText("￥"+(count*mGoddsParamChild.getPrice()));
        tv_newprice.setText("￥"+mGoddsParamChild.getPrice());
        String imgs=mGoodDetail.getCommodityImage();
        if(!TextUtils.isEmpty(imgs)){
            if(imgs.contains(",")){
                Glide.with(this).load(imgs.split(",")[0]).into(iv_logo);
            }else{
                Glide.with(this).load(mGoodDetail.getCommodityImage()).into(iv_logo);
            }
        }

        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SinglePayActivity.this, ManaAddressActivity.class), 1);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(personAddress==null){
                    ToastUtils.showLongToast(SinglePayActivity.this,"请先去选择添加一个地址");
                    return;
                }
                takeOrder();
            }
        });
        getAddress();
    }
    private void getAddress() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("isDefault", 1);
        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("getAddress", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            personAddress = new Gson().fromJson(item.toString(), PersonAddress.class);

                        }
                        if(personAddress!=null){
                            tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
                            if (personAddress.getIsDefault() == 2) {
                                tv_default.setVisibility(View.GONE);
                            }
                            tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
                            tv_detail.setText(personAddress.getAddress());
                        }else{
                            tv_addrname.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
                            tv_detail.setText(MyApplication.getaMapLocation().getAddress());
                        }


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

    private void payByWechat(String data){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",data);
        HttpProxy.obtain().post(PlatformContans.WechatPay.babyMerchantOrderPay, MyApplication.token, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data=jsonObject.getJSONObject("data") ;
                        WechatRes wechatRes=new Gson().fromJson(data.toString(),WechatRes.class);
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
    private void startWechatPay(WechatRes payReponse){
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
    private void startAlipay(String orderId){
        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SinglePayActivity.this);
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
    private void payByAlipay(String data){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",data);
        HttpProxy.obtain().post(PlatformContans.Pay.babyMerchantOrderPay, MyApplication.token, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String data=jsonObject.getString("data") ;
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
        RelativeLayout rl_wechat= (RelativeLayout) contentView.findViewById(R.id.rl_wechat);
        RelativeLayout rl_alipay= (RelativeLayout) contentView.findViewById(R.id.rl_alipay);
        SuperTextView tv_confirm= (SuperTextView) contentView.findViewById(R.id.tv_confirm);
        ImageView iv_wechat= (ImageView) contentView.findViewById(R.id.iv_choose);
        ImageView iv_alipay= (ImageView) contentView.findViewById(R.id.iv_choose2);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(type==1)  {
                    payByWechat(data);
                }else{
                    payByAlipay(data);
                }
            }
        });
        rl_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=1;
                iv_wechat.setVisibility(View.VISIBLE);
                iv_alipay.setVisibility(View.GONE);
            }
        });
        rl_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=2;
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
                        Log.e("code",resultStatus);
                        ToastUtil.showToast(SinglePayActivity.this,"支付成功");
                    }
                    break;
                }
            }
        };
    };

    private void takeOrder(){
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            return;
        }
        String commodityId=mGoodParam.getBabyCommodityId();
        int number=count;
        String name=personAddress.getNickname();
        String telephone=personAddress.getTelephone();
        String address=personAddress.getProvince()+personAddress.getCity()+personAddress.getDistrict()+personAddress.getAddress();
        String remark=et_remark.getEditableText().toString();
        String firstSpecificationId=mGoodParam.getId();
        String secondSpecificationId=mGoddsParamChild.getId();
         Map<String,Object> params=new HashMap<>();
        params.put("commodityId",commodityId);
        params.put("number",number);
        params.put("name",name);
        params.put("telephone",telephone);
        params.put("address",address);
        params.put("remark",remark);
        params.put("firstSpecificationId",firstSpecificationId);
        params.put("secondSpecificationId",secondSpecificationId);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addOrder, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("addOrder",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String orderId=jsonObject.getString("data");
                        showPayDialog(orderId);
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
        if (requestCode == 1 && data != null) {
            personAddress = (PersonAddress) data.getSerializableExtra("address");
            tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
            if (personAddress.getIsDefault() == 2) {
                tv_default.setVisibility(View.GONE);
            }
            tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
            tv_detail.setText(personAddress.getAddress());
        }
    }
}
