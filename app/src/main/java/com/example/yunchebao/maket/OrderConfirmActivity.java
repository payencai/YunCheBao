package com.example.yunchebao.maket;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.adapter.GoodsPayAdapter;
import com.example.yunchebao.maket.model.GoodsSelect;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tool.MathUtil;
import com.tool.listview.PersonalListView;
import com.vipcenter.AddressListActivity;
import com.vipcenter.model.PersonAddress;
import com.vipcenter.view.PayCashierDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tool.MyProgressDialog.dialog;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderConfirmActivity extends NoHttpBaseActivity {
    private Context ctx;
    ArrayList<GoodsSelect> mGoodsSelects = new ArrayList<>();
    ArrayList<PhoneGoodEntity> mselectGoods = new ArrayList<>();
    GoodsPayAdapter mGoodsPayAdapter;
    @BindView(R.id.lv_select)
    PersonalListView lv_select;
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

    List<PhoneGoodEntity> newSortGoods = new ArrayList<>();
    List<GoodsSelect> newGoodsSelects = new ArrayList<>();
    PersonAddress personAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(this).inflate(R.layout.order_confim_layout, null);
        setContentView(view);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        ctx = this;
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
        mGoodsSelects = (ArrayList<GoodsSelect>) bundle.getSerializable("list");
        mselectGoods = (ArrayList<PhoneGoodEntity>) bundle.getSerializable("select");}
        initView();
    }

    Map<String, Integer> mapCount = new HashMap<>();
    int lastNum = 0;
    double money;

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAddress();
        for (int i = 0; i < mGoodsSelects.size(); i++) {
            int count = 0;
            GoodsSelect mGoodsSelect = mGoodsSelects.get(i);
            String shopid = mGoodsSelect.getShopId();
            for (int j = 0; j < mselectGoods.size(); j++) {
                PhoneGoodEntity phoneGoodEntity = mselectGoods.get(j);
                if (phoneGoodEntity.getShopId().equals(shopid)) {
                    count++;
                }
            }
            mapCount.put(shopid, count);
        }
        for (int i = 0; i < mGoodsSelects.size(); i++) {
            GoodsSelect mGoodsSelect = mGoodsSelects.get(i);
            String shopid = mGoodsSelect.getShopId();
            int num = mapCount.get(shopid);
            Log.e("num", num + "");
            double totalmoney = 0;
            for (int j = lastNum; j < mselectGoods.size(); j++) {
                PhoneGoodEntity phoneGoodEntity = mselectGoods.get(j);
                if (phoneGoodEntity.getShopId().equals(shopid)) {
                    if (num == 1) {//只有一个
                        phoneGoodEntity.setFlag(3);//添加首尾
                        totalmoney = totalmoney + phoneGoodEntity.getCount() * Double.parseDouble(MathUtil.getDoubleTwo(phoneGoodEntity.getPrice()));
                        phoneGoodEntity.setTotalPrice(totalmoney);
                        money = money + totalmoney;
                        lastNum++;
                    } else if (num > 1) {
                        totalmoney = totalmoney + phoneGoodEntity.getCount() * Double.parseDouble(MathUtil.getDoubleTwo(phoneGoodEntity.getPrice()));
                        if (j == lastNum) {
                            phoneGoodEntity.setFlag(1);

                        } else if (j == (lastNum + num - 1)) {
                            phoneGoodEntity.setFlag(2);
                            phoneGoodEntity.setTotalPrice(totalmoney);
                            money = money + totalmoney;

                            lastNum = j + 1;
                        }
                    }
                    newSortGoods.add(phoneGoodEntity);
                }

            }
            newGoodsSelects.add(mGoodsSelect);
        }
        tv_totalmoney.setText("￥" + money);
        mGoodsPayAdapter = new GoodsPayAdapter(ctx, newSortGoods);
        lv_select.setAdapter(mGoodsPayAdapter);

    }


    public void saveEditData(int position, String str) {
        PhoneGoodEntity goodEntity = newSortGoods.get(position);
        goodEntity.setRemark(str);
        newSortGoods.remove(position);
        newSortGoods.add(position, goodEntity);

        //Toast.makeText(this,str+"----"+position,Toast.LENGTH_LONG).show();
    }

    /**
     * 初始化支付方式Dialog
     */
    private void initDialog() {
        // 隐藏输入法
        dialog = new PayCashierDialog(this, R.style.recharge_pay_dialog, true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //确认充值
            }
        });
        dialog.show();
    }

    List<GoodsSelect> finalGoodsSelect = new ArrayList<>();

    @OnClick({R.id.back, R.id.submit, R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                if (MyApplication.isIsLogin())
                    for (int i = 0; i < newGoodsSelects.size(); i++) {
                        String remark = "hhh";
                        GoodsSelect goodsSelect = newGoodsSelects.get(i);
                        for (int j = 0; j < newSortGoods.size(); j++) {
                            if (goodsSelect.getShopId().equals(newSortGoods.get(j).getShopId())) {
                                if (newSortGoods.get(j).getFlag() >= 2) {
                                    remark = newSortGoods.get(j).getRemark();
                                }
                            }
                        }
                        goodsSelect.setRemark(remark);
                        goodsSelect.setName(personAddress.getNickname());
                        goodsSelect.setTelephone(personAddress.getTelephone());
                        goodsSelect.setAddress(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict() + personAddress.getAddress());
                        finalGoodsSelect.add(goodsSelect);
                    }
                String json = new Gson().toJson(finalGoodsSelect);
                json = "{\n" +
                        "  \"data\": " + json + "}";
                Log.e("newGoodsSelects", json);
                takeOrder(json);
               // initDialog();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(OrderConfirmActivity.this, AddressListActivity.class), 1);
                break;
        }
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
                PayTask alipay = new PayTask(OrderConfirmActivity.this);
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
    int type=1;
    private static final int SDK_PAY_FLAG = 1;
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
                        ToastUtil.showToast(OrderConfirmActivity.this,"支付成功");
                    }
                    break;
                }
            }
        };
    };
    private void takeOrder(String json){
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            return;
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
      //  Map<String,Object> params=new HashMap<>();
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addOrderByShoppingCar, token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult",result);
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
                        tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
                        if (personAddress.getIsDefault() == 2) {
                            tv_default.setVisibility(View.GONE);
                        }
                        tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
                        tv_detail.setText(personAddress.getAddress());

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
