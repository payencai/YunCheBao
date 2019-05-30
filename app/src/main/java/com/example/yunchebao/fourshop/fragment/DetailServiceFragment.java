package com.example.yunchebao.fourshop.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.adapter.ServiceContentAdapter;
import com.example.yunchebao.fourshop.adapter.ServiceTypeAdapter;
import com.example.yunchebao.fourshop.bean.ServiceContent;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tool.MyListView;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailServiceFragment extends Fragment {
    ServiceContentAdapter mServiceContentAdapter;
    ServiceTypeAdapter mServiceTypeAdapter;
    @BindView(R.id.lv_left)
    MyListView lv_left;
    @BindView(R.id.lv_right)
    ListViewForScrollView lv_right;
    @BindView(R.id.tv_type)
    TextView tv_type;
    FourShopDetailActivity mActivity;
    List<ServiceContent> mServiceContents;
     List<ServiceContent.ServeListBean> mServeListBeans ;
    String id;
    public DetailServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this,view);
        mActivity= (FourShopDetailActivity) getActivity();
        id=mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mServeListBeans=new ArrayList<>();
        mServiceContents=new ArrayList<>();
        mServiceTypeAdapter=new ServiceTypeAdapter(getContext(),mServiceContents);
        lv_left.setAdapter(mServiceTypeAdapter);
        lv_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_type.setText(mServiceContents.get(position).getCategoryName());
                mServeListBeans.clear();
                mServeListBeans.addAll(mServiceContents.get(position).getServeList());
                mServiceTypeAdapter.setPos(position);
                mServiceTypeAdapter.notifyDataSetChanged();
                mServiceContentAdapter.notifyDataSetChanged();
            }
        });
        lv_left.setNestedScrollingEnabled(false);
        lv_right.setNestedScrollingEnabled(false);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourServeResultList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ServiceContent serverType = new Gson().fromJson(item.toString(), ServiceContent.class);
                        mServiceContents.add(serverType);
                    }

                    mServiceTypeAdapter.notifyDataSetChanged();
                    if (mServiceContents.size() > 0) {
                        tv_type.setText(mServiceContents.get(0).getCategoryName());
                        initDetaiListView();
                    }else{
                        tv_type.setVisibility(View.GONE);
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
    private void initDetaiListView() {
        mServeListBeans.addAll(mServiceContents.get(0).getServeList());
        mServiceContentAdapter = new ServiceContentAdapter(getContext(), mServeListBeans);
        lv_right.setAdapter(mServiceContentAdapter);
        lv_right.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(MyApplication.isLogin){
                    String serviceId= mServeListBeans.get(position).getId();
                    addOrder(serviceId);
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }

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
                PayTask alipay = new PayTask(getActivity());
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
        HttpProxy.obtain().post(PlatformContans.Pay.fourShopPay, MyApplication.token, params, new ICallBack() {
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

        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        dialog.show();
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_selectpay, null);
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

    int type = 1;
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
                        Log.e("code", resultStatus);
                        ToastUtil.showToast(getContext(),"支付成功");

                    }
                    break;
                }
            }
        }

        ;
    };
    private void addOrder(String id){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.FourShop.addFourShopOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String orderId=jsonObject.getString("data");
                    showPayDialog(orderId);
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
