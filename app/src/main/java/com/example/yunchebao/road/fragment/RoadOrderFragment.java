package com.example.yunchebao.road.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.road.AddRoadCommentActivity;
import com.example.yunchebao.road.SeeRoadCommentActivity;
import com.example.yunchebao.road.adapter.RoadOrderAdapter;
import com.example.yunchebao.road.model.RoadOrder;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;

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
public class RoadOrderFragment extends Fragment {


    public RoadOrderFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_order)
    RecyclerView rv_content;
    List<RoadOrder> mRoadOrders;
    RoadOrderAdapter mRoadOrderAdapter;
    int page = 1;
    int state = 1;
    boolean isLoadMore = false;

    public static RoadOrderFragment newInstance(int state) {
        RoadOrderFragment orderItemFragment = new RoadOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        orderItemFragment.setArguments(bundle);
        return orderItemFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView
        View view = inflater.inflate(R.layout.fragment_road_order, container, false);
        ButterKnife.bind(this, view);
        state = getArguments().getInt("state");
        initView();
        return view;
    }

    private void initView() {
        mRoadOrders = new ArrayList<>();
        mRoadOrderAdapter = new RoadOrderAdapter(R.layout.item_yuedan_order, mRoadOrders);
        mRoadOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RoadOrder roadOrder = (RoadOrder) adapter.getItem(position);
                Intent intent;
                if (view.getId() == R.id.fukuan) {
                    if(roadOrder.getState()==1){
                        showPay(roadOrder.getId(), roadOrder.getName());
                        //未付款
                    }else {
                        if(roadOrder.getIsComment()==0){
                            intent = new Intent(getContext(), AddRoadCommentActivity.class);
                            intent.putExtra("id", roadOrder.getId());
                            startActivity(intent);
                        }else{
                            intent = new Intent(getContext(), SeeRoadCommentActivity.class);
                            intent.putExtra("id", roadOrder.getId());
                            startActivity(intent);
                        }
                    }

                } else if (view.getId() == R.id.delete) {
                    showDelDialog(roadOrder.getId());
                }
            }
        });
        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_content.setAdapter(mRoadOrderAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });
        getData();
    }

    private void refresh() {
        mRoadOrders.clear();
        page = 1;
        mRoadOrderAdapter.setNewData(mRoadOrders);
        getData();
    }

    private void deleteOrder(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().post(PlatformContans.RoadRescue.deleteMyRoadRescueOrderById, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt", result);
                ToastUtil.showToast(getContext(), "操作成功");
                refresh();
                // finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void payForOrder(String id, double money) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        params.put("price", money);
        HttpProxy.obtain().post(PlatformContans.Pay.roadRescueShopPay, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    showPayDialog(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Log.e("reuslt",result);
//                ToastUtil.showToast(getContext(),"操作成功");

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showPay(String id, String name) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_pay_order, null);
        ImageView iv_del = (ImageView) view.findViewById(R.id.iv_del);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        EditText et_money = (EditText) view.findViewById(R.id.et_money);

        final Dialog dialog = new Dialog(getContext(), R.style.PayDialog);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String money = et_money.getEditableText().toString();
                double price = Double.parseDouble(money);
                payForOrder(id, price);

            }
        });
        tv_name.setText(name);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = (int) (display.getWidth() * 0.8);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                SystemClock.sleep(300);
//                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        });
       // showKeyboard(et_money);

    }

    public void showKeyboard(EditText editText) {
        if(editText!=null){
            //设置可获得焦点
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            //请求获得焦点
            editText.requestFocus();
            //调用系统输入法
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText, 0);
        }
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state", state);
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getMyRoadRescueOrderList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getOrder", result);
                mRefreshLayout.finishRefresh();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<RoadOrder> roadOrders = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RoadOrder replaceDrive = new Gson().fromJson(item.toString(), RoadOrder.class);
                        mRoadOrders.add(replaceDrive);
                        roadOrders.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mRoadOrderAdapter.loadMoreEnd(true);
                        } else {
                            mRoadOrderAdapter.addData(roadOrders);
                            mRoadOrderAdapter.loadMoreComplete();
                        }
                    } else {
                        mRoadOrderAdapter.setNewData(mRoadOrders);

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

    private void showDelDialog(String id) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_order, null);
        TextView tv_back = (TextView) view.findViewById(R.id.tv_back);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteOrder(id);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
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

    int type = 1;
    private static final int SDK_PAY_FLAG = 1;

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
                    startAlipay(data);
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
                        ToastUtil.showToast(getContext(), "支付成功");
                        refresh();
                    }
                    break;
                }
            }
        }

        ;
    };
}
