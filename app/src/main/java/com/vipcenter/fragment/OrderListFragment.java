package com.vipcenter.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.application.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodsOrderDetailActivity;
import com.maket.SinglePayActivity;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.CheckLogisticsActivity;
import com.vipcenter.HaveGotGoodsActivity;
import com.vipcenter.OrderCommentSubmitActivity;
import com.vipcenter.RebackMoneyActivity;
import com.vipcenter.adapter.NewOrderAdapter;
import com.vipcenter.adapter.OrderListAdapter;
import com.vipcenter.view.PayWayDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.rong.imkit.RongIM;


public class OrderListFragment extends BaseFragment implements OnClickListener {

    NewOrderAdapter mNewOrderAdapter;
    private List<PhoneOrderEntity> mPhoneOrderEntityList;
    private boolean isLoadMore = false;
    private int page = 1;//查询页
    int state = 0;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_order_list, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mPhoneOrderEntityList = new ArrayList<>();
        state = getArguments().getInt("state");
        mNewOrderAdapter = new NewOrderAdapter(R.layout.item_order_all, mPhoneOrderEntityList);
        mNewOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PhoneOrderEntity phoneOrderEntity = (PhoneOrderEntity) adapter.getItem(position);
                Intent intent ;
                Log.e("state",phoneOrderEntity.getState()+"");
                if(phoneOrderEntity.getState()==5){
                    intent=new Intent(getContext(), RebackMoneyActivity.class);
                }else{
                    intent = new Intent(getContext(), GoodsOrderDetailActivity.class);
                }
                intent.putExtra("data", phoneOrderEntity);
                startActivity(intent);
            }
        });
        mNewOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;

                getData();
            }
        },rv_order);
        mNewOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PhoneOrderEntity phoneOrderEntity = (PhoneOrderEntity) adapter.getItem(position);
                String userId = phoneOrderEntity.getUserId();
                Intent intent;
                switch (view.getId()) {
                    case R.id.tixing:
                        RongIM.getInstance().startPrivateChat(getContext(), userId, phoneOrderEntity.getShopName());
                        break;
                    case R.id.delete:
                        deleteOrder(phoneOrderEntity.getId());
                        break;
                    case R.id.lianxi://联系卖家
                        RongIM.getInstance().startPrivateChat(getContext(), userId, phoneOrderEntity.getShopName());
                        break;
                    case R.id.quxiao://取消订单
                        showCancelDialog(phoneOrderEntity.getId());
                        break;
                    case R.id.fukuan://付款
                        showPayDialog(phoneOrderEntity.getId());
                        break;
                    case R.id.wuliu://物流
                        intent = new Intent(getContext(), CheckLogisticsActivity.class);
                        intent.putExtra("data", phoneOrderEntity);
                        startActivity(intent);
                        //showPayDialog(phoneOrderEntity.getId());
                        break;
                    case R.id.shouhou://退款
                        showCancelDialog(phoneOrderEntity.getId());
                        break;
                    case R.id.shouhuo://收货
                        confirmOrder(phoneOrderEntity);
                        break;
                    case R.id.pingjia:
                        intent = new Intent(getContext(), OrderCommentSubmitActivity.class);
                        intent.putExtra("data", phoneOrderEntity);
                        startActivity(intent);


                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mPhoneOrderEntityList.clear();
                mNewOrderAdapter.setNewData(mPhoneOrderEntityList);
                getData();
                refreshLayout.finishRefresh(1000);
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(mNewOrderAdapter);
        getData();
    }

    public static OrderListFragment newInstance(int state) {
        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", state);
        orderListFragment.setArguments(bundle);
        return orderListFragment;
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("state", state);
        HttpProxy.obtain().get(PlatformContans.GoodsOrder.getMyOrderList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodList", result+"");
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<PhoneOrderEntity> phoneOrderEntities=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        PhoneOrderEntity phoneOrderEntity = new Gson().fromJson(item.toString(), PhoneOrderEntity.class);
                        mPhoneOrderEntityList.add(phoneOrderEntity);
                        phoneOrderEntities.add(phoneOrderEntity);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        mNewOrderAdapter.addData(phoneOrderEntities);
                        mNewOrderAdapter.loadMoreComplete();
                    }else{
                        mNewOrderAdapter.setNewData(mPhoneOrderEntityList);
                        mNewOrderAdapter.loadMoreEnd(true);
                    }

                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void showFinishDialog(PhoneOrderEntity mPhoneOrderEntity) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order_finish, null);
        TextView tv_refused = (TextView) view.findViewById(R.id.tv_refused);
        TextView tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        tv_refused.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(getContext(), OrderCommentSubmitActivity.class);
                intent.putExtra("data",mPhoneOrderEntity);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    private void showCancelDialog(String id) {
        Dialog dialog = new Dialog(getContext(), R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order_cancel, null);
        TextView tv_refused = (TextView) view.findViewById(R.id.tv_refused);
        TextView tv_agree = (TextView) view.findViewById(R.id.tv_agree);
        tv_refused.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_agree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                cancelOrder(id);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }


    private void deleteOrder(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.deleteOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt", result);
                ToastUtil.showToast(getContext(), "删除成功");
                refresh();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void confirmOrder(PhoneOrderEntity  phoneOrderEntity) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", phoneOrderEntity.getId());
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.finishOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt", result);
                showFinishDialog(phoneOrderEntity);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void refresh(){
        page=1;
        mPhoneOrderEntityList.clear();
        mNewOrderAdapter.setNewData(mPhoneOrderEntityList);
        getData();
    }
    private void cancelOrder(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.cancelOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("reuslt", result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        ToastUtil.showToast(getContext(), "取消成功");
                        refresh();
                    }else{
                        String msg=jsonObject.getString("message");
                        ToastUtil.showToast(getContext(), msg);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refresh();
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
                        ToastUtil.showToast(getContext(),"支付成功");
                        refresh();
                    }
                    break;
                }
            }
        }

        ;
    };


    @Override
    public void onClick(View v) {


    }
}
