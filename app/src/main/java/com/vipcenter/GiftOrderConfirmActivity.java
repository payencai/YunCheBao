package com.vipcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.model.Gift;
import com.vipcenter.model.PersonAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/16.
 */

public class GiftOrderConfirmActivity extends NoHttpBaseActivity {
    Gift mGift;
    PersonAddress personAddress;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_default)
    TextView tv_default;
    @BindView(R.id.tv_addrname)
    TextView tv_addrname;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.gift_img)
    ImageView gift_img;
    @BindView(R.id.tv_giftname)
    TextView tv_giftname;
    @BindView(R.id.tv_giftcoin)
    TextView tv_giftcoin;
    @BindView(R.id.tv_gifttime)
    TextView tv_gifttime;
    @BindView(R.id.tv_coin1)
    TextView tv_coin1;
    @BindView(R.id.tv_coin2)
    TextView tv_coin2;
    @BindView(R.id.tv_coin3)
    TextView tv_coin3;
    @BindView(R.id.tv_coin4)
    TextView tv_coin4;
    @BindView(R.id.tv_send)
    SuperTextView tv_send;
    @BindView(R.id.et_code)
    EditText et_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_order_confirm);
        mGift = (Gift) getIntent().getExtras().getSerializable("gift");
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
        getAddress();
    }

    private void takeOrder(String json) {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.token;
        } else {
            return;
        }

        HttpProxy.obtain().post(PlatformContans.Gift.addGiftOrder, token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    String msg=jsonObject.getString("message");
                    if (code == 0) {
                       ToastUtil.showToast(GiftOrderConfirmActivity.this,"兑换成功");
                       finish();
                    }else{
                        ToastUtil.showToast(GiftOrderConfirmActivity.this,msg);
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


    private void getCodeByType(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", phone);
        params.put("type", 4);
        HttpProxy.obtain().get(PlatformContans.User.getVeriCode, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(GiftOrderConfirmActivity.this, "验证码已发送，请注意查收", Toast.LENGTH_LONG).show();
                        //注册,并且登录
                    } else {
                        //登录
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

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);

        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, params, MyApplication.token, new ICallBack() {
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
                            if(i==0)
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
                            tv_detail.setText(MyApplication.getaMapLocation().getAddress());
                            tv_addrname.setText(MyApplication.getaMapLocation().getProvince()+MyApplication.getaMapLocation().getCity()+MyApplication.getaMapLocation().getDistrict());
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
    TimeCount mTimeCount;
    int count=60;
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setEnabled(false);
            tv_send.setBackgroundResource(R.drawable.gray_stroke_r4);
            tv_send.setTextColor(getResources().getColor(R.color.gray_99));
            tv_send.setText(millisUntilFinished / 1000 +"");
        }

        @Override
        public void onFinish() {
            count = 60;
            tv_send.setText("获取验证码");
            tv_send.setEnabled(true);
            tv_send.setBackgroundResource(R.color.pink_db);
            tv_send.setTextColor(getResources().getColor(R.color.red_39));

        }
    }
    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "商品填写");
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        tv_coin1.setText(mGift.getCoinCount() + "宝币");
        tv_coin2.setText(mGift.getCoinCount() + "宝币");
        tv_coin3.setText(mGift.getCoinCount() + "宝币");
        tv_coin4.setText(mGift.getCoinCount() + "宝币");
        tv_giftcoin.setText(mGift.getCoinCount()+"");
        tv_giftname.setText(mGift.getCommodityName());
        tv_gifttime.setText(mGift.getCreateTime().substring(0, 10));
        String images = mGift.getCommodityImage();
        if (!TextUtils.isEmpty(images)) {
            if (images.contains(","))
                Glide.with(this).load(images.split(",")[0]).into(gift_img);
            else{
                Glide.with(this).load(images).into(gift_img);
            }
        }
        mTimeCount=new TimeCount(60000,1000);

    }

    @OnClick({R.id.back, R.id.submitBtn, R.id.addressLay, R.id.tv_send})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.addressLay:
                startActivityForResult(new Intent(GiftOrderConfirmActivity.this, ManaAddressActivity.class), 1);
                break;
            case R.id.submitBtn:
                if(personAddress==null){
                    ToastUtil.showToast(this,"请先选择地址");
                    return;
                }
                String addressId = personAddress.getId();
                String code = et_code.getEditableText().toString();
                String commodityId = mGift.getId();
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showToast(this,"请选输入验证码");
                    return;
                }
                int commodityNumber = 1;
                Map<String, Object> params = new HashMap<>();
                params.put("addressId", addressId);
                params.put("code", code);
                params.put("commodityId", commodityId);
                params.put("commodityNumber", commodityNumber);
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showToast(this, "请输入验证码");
                    return;
                }
                String json = new Gson().toJson(params);
                takeOrder(json);
                break;
            case R.id.tv_send:
                if(personAddress==null){
                    ToastUtil.showToast(this,"请先去选择一个收货人信息");
                    return;
                }
                mTimeCount.start();
                getCodeByType(personAddress.getTelephone());
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
