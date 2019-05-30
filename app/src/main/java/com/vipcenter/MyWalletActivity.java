package com.vipcenter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.model.MemberCard;
import com.vipcenter.model.MyWallet;

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

/**
 * Created by sdhcjhss on 2018/1/2.
 */

public class MyWalletActivity extends AppCompatActivity {
    @BindView(R.id.tv_coin)
    TextView tv_coin;
    @BindView(R.id.tv_total)
    TextView tv_total;
    int type=1;
    private static final int SDK_PAY_FLAG = 1;
    List<MemberCard> mMemberCards = new ArrayList<>();
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet_layout);
        ButterKnife.bind(this);
       //ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showDialog() {
        List<View> mViews = new ArrayList<>();
        for (int i = 0; i < mMemberCards.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_card, null);
            RelativeLayout rlcard= (RelativeLayout) view.findViewById(R.id.rl_card);
            TextView tv_name= (TextView) view.findViewById(R.id.tv_name);
            TextView tv_price= (TextView) view.findViewById(R.id.tv_price);
            tv_name.setText(mMemberCards.get(i).getName());
            tv_price.setText(mMemberCards.get(i).getPrice()+"");
            int finalI = i;
            rlcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String id= mMemberCards.get(finalI).getId();
                    buyCard(id);
                }
            });
            mViews.add(view);
        }

        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_card, null);
        ViewPager viewPager = (ViewPager) contentView.findViewById(R.id.vp_card);
        NoticePagerAdapter noticePagerAdapter = new NoticePagerAdapter(mViews);
        viewPager.setAdapter(noticePagerAdapter);
        dialog = new Dialog(this, R.style.dialog);
        dialog.show();
        dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT; //使用这种方式更改了dialog的框宽
        window.setAttributes(params);
    }

    private void payByWechat(String data){
        Map<String,Object> params=new HashMap<>();
        params.put("orderId",data);
        HttpProxy.obtain().post(PlatformContans.WechatPay.memberCardPay, MyApplication.token, params,new ICallBack() {
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
                PayTask alipay = new PayTask(MyWalletActivity.this);
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
        HttpProxy.obtain().post(PlatformContans.MemberCard.memberCardPay, MyApplication.token, params,new ICallBack() {
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
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Log.e("code",resultStatus);
                        ToastUtil.showToast(MyWalletActivity.this,"支付成功");
                        if(TextUtils.isEmpty(myWallet.getPassword())){
                            ActivityAnimationUtils.commonTransition(MyWalletActivity.this, SetPayPasswordActivity.class, ActivityConstans.Animation.FADE);
                        }
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






    private void buyCard(String id) {
        Map<String,Object> params=new HashMap<>();
        params.put("id",id);
        HttpProxy.obtain().post(PlatformContans.MemberCard.addMemberCardOrder, MyApplication.token, params,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String data=jsonObject.getString("data") ;
                        showPayDialog(data);
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

    MyWallet myWallet;
    private void getWallet() {
        HttpProxy.obtain().get(PlatformContans.User.getMyWallet, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data=jsonObject.getJSONObject("data") ;
                        myWallet=new Gson().fromJson(data.toString(),MyWallet.class);
                        tv_coin.setText(myWallet.getGoldCoin()+"");
                        tv_total.setText(myWallet.getTotal()+"");
                        if(myWallet.getIsHasMemberCard()==0){
                            tv_total.setText("未购买");
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

    private void getMemberCard() {
        mMemberCards.clear();
        HttpProxy.obtain().get(PlatformContans.MemberCard.getMemberCardRuleList,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            MemberCard memberCard = new Gson().fromJson(item.toString(), MemberCard.class);
                            mMemberCards.add(memberCard);
                        }
                        showDialog();
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

    private void initView() {

        getWallet();
    }





    public static class NoticePagerAdapter extends PagerAdapter {

        private List<View> views;

        public NoticePagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);  //添加页卡
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));   //删除页卡
        }
    }

    @OnClick({R.id.back, R.id.consumptionLay, R.id.depositLay, R.id.toDeposit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.toMoreBtn:
//                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, GiftMarketHomeActivity.class, ActivityConstans.Animation.FADE);
//                break;
            case R.id.consumptionLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, MyPayActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.depositLay:
                ActivityAnimationUtils.commonTransition(MyWalletActivity.this, SetPayPasswordActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.toDeposit:
                //getMemberCard();
                //ActivityAnimationUtils.commonTransition(MyWalletActivity.this,AccountDepositActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
