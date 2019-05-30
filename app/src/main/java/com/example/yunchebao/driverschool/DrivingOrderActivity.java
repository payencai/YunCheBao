package com.example.yunchebao.driverschool;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.example.yunchebao.MyApplication;
import com.bbcircle.data.ClassItem;
import com.cheyibao.ClassSelelctActivity;
import com.cheyibao.CoashSelectActivity;
import com.cheyibao.model.CoachItem;
import com.cheyibao.model.DrvingSchool;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.payencai.library.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrivingOrderActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.rl_class)
    RelativeLayout rl_class;
    @BindView(R.id.rl_coash)
    RelativeLayout rl_coash;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_schoolname)
    TextView tv_schoolname;
    @BindView(R.id.tv_selectedClass)
    TextView tv_selectedClass;
    @BindView(R.id.tv_selectedCoash)
    TextView tv_selectedCoash;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_coash)
    TextView tv_coash;

    ClassItem mClassItem;
    CoachItem mCoachItem;
    String id;
    DrvingSchool mDrvingSchool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_order);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        mClassItem = (ClassItem) getIntent().getSerializableExtra("class");
        mCoachItem = (CoachItem) getIntent().getSerializableExtra("coash");
        mDrvingSchool= (DrvingSchool) getIntent().getSerializableExtra("name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        initView();
    }

    private void initView() {
        rl_class.setOnClickListener(this);
        rl_coash.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        if(mClassItem!=null){
            tv_selectedClass.setText("已选班级: "+mClassItem.getClassName());
            tv_price.setText("价    格: ￥"+mClassItem.getClassPrice());
            tv_class.setText(mClassItem.getClassName());
        }
        tv_schoolname.setText(mDrvingSchool.getName());
        if(mCoachItem!=null){
            tv_selectedCoash.setText("已选教练: "+mCoachItem.getCoachName());
            tv_coash.setText(mCoachItem.getCoachName());
        }
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==1){
                mClassItem= (ClassItem) data.getSerializableExtra("class");
                tv_selectedClass.setText("已选班级: "+mClassItem.getClassName());
                tv_price.setText("价    格: ￥"+mClassItem.getClassPrice());
                tv_class.setText(mClassItem.getClassName());
            }
            if(requestCode==2){
                mCoachItem= (CoachItem) data.getSerializableExtra("coash");
                tv_selectedCoash.setText("已选教练: "+mCoachItem.getCoachName());
                tv_coash.setText(mCoachItem.getCoachName());
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.rl_class:
                intent = new Intent(this, ClassSelelctActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_coash:
                intent = new Intent(this, CoashSelectActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_submit:

                if(MyApplication.isLogin){
                    if(TextUtils.isEmpty(et_name.getEditableText().toString())){
                        ToastUtil.showToast(this,"请输入姓名");
                        return;
                    }
                    if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                        ToastUtil.showToast(this,"请输入手机号");
                        return;
                    }
                    if(mClassItem==null){
                        ToastUtil.showToast(this,"请选择班型");
                        return;
                    }
                    if(mCoachItem==null){
                        ToastUtil.showToast(this,"请选择教练");
                        return;
                    }
                    if (!MyApplication.isLogin) {
                        startActivity(new Intent(DrivingOrderActivity.this, RegisterActivity.class));
                        return;
                    }
                    postOrder();
                }else{
                    startActivity(new Intent(DrivingOrderActivity.this,RegisterActivity.class));
                }
                break;
        }
    }
    private void postOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("name",et_name.getEditableText().toString());//驾校
        params.put("telephone", et_phone.getEditableText().toString());//驾校名字
        params.put("shopId",mDrvingSchool.getId());//驾校
        params.put("shopName", mDrvingSchool.getName());//驾校名字
        params.put("title", mDrvingSchool.getName());//驾校名字
        params.put("image", mDrvingSchool.getLogo());//驾校图片
        params.put("number", 1);
        params.put("type", 4);
        params.put("commodityId", mClassItem.getId());
         params.put("className",mClassItem.getClassName());
        params.put("coachId", mCoachItem.getId());
        params.put("coachName", mCoachItem.getCoachName());
        HttpProxy.obtain().post(PlatformContans.CarOrder.addCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String orderId = jsonObject.getString("data");
                        showPayDialog(orderId);
                    }else if(code==9999){
                        ToastUtils.showLongToast(DrivingOrderActivity.this,"请先去实名认证");
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
    private void payByWechat(String data) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", data);
        HttpProxy.obtain().post(PlatformContans.WechatPay.carOrderPay, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        WechatRes wechatRes = new Gson().fromJson(data.toString(), WechatRes.class);
                        startWechatPay(wechatRes);
                    }else{
                        ToastUtil.showToast(DrivingOrderActivity.this,msg);
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
                PayTask alipay = new PayTask(DrivingOrderActivity.this);
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
        HttpProxy.obtain().post(PlatformContans.Pay.carOrderPay, MyApplication.token, params, new ICallBack() {
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

        Dialog dialog = new Dialog(DrivingOrderActivity.this, R.style.dialog);
        dialog.show();
        View contentView = LayoutInflater.from(DrivingOrderActivity.this).inflate(R.layout.dialog_selectpay, null);
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
                        ToastUtil.showToast(DrivingOrderActivity.this,"支付成功");

                    }
                    break;
                }
            }
        }

        ;
    };
}
