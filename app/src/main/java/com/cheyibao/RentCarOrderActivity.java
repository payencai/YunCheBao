package com.cheyibao;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentCarType;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.DateUtils;
import com.common.IdentityCardVerify;
import com.common.OrderPay;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.wxapi.WechatRes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.OrderConfirmActivity;
import com.maket.model.GoodsSelect;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.vipcenter.LoginByaccountActivity;
import com.vipcenter.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RentCarOrderActivity extends AppCompatActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.car_model_banner_view)
    ImageView carModelBannerView;
    @BindView(R.id.car_model_name_view)
    TextView carModelNameView;
    @BindView(R.id.car_model_car_category_view)
    TextView carModelCarCategoryView;
    @BindView(R.id.car_model_is_auto_driver_view)
    TextView carModelIsAutoDriverView;
    @BindView(R.id.day_price_view)
    TextView dayPriceView;
    @BindView(R.id.total_price_view)
    TextView totalPriceView;
    @BindView(R.id.rent_car_person_name_view)
    EditText rentCarPersonNameView;
    @BindView(R.id.rent_car_person_phone_view)
    EditText rentCarPersonPhoneView;
    @BindView(R.id.rent_car_person_identify_view)
    EditText rentCarPersonIdentifyView;
    @BindView(R.id.rent_car_person_contact_person_name_view)
    EditText rentCarPersonContactPersonNameView;
    @BindView(R.id.rent_car_person_contact_person_phone_view)
    EditText rentCarPersonContactPersonPhoneView;
    @BindView(R.id.submit_order_view)
    TextView submitOrderView;
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;

    private RentCarModel rentCarModel;
    private long startTime;
    private long endTime;
    private long duration;

    private AddressBean takeTheCarAddress;
    private AddressBean returnTheCarAddress;
    private RentShop rentShop;
    private boolean isToHomeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_car_order);
        ButterKnife.bind(this);
        if (RentCarUtils.rentCarInfo != null) {
            rentCarModel = (RentCarModel) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_CAR_MODEL);
            startTime = (long) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_START_TIME);
            endTime = (long) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_END_TIME);
            duration = endTime - startTime;
            takeTheCarAddress = (AddressBean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_AREA_1);
            returnTheCarAddress = (AddressBean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_AREA_2);
            rentShop = (RentShop) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_SHOP);
            isToHomeService = (boolean) RentCarUtils.rentCarInfo.get(RentCarUtils.RENT_CAR_INFO_IS_TO_HOME_SERVICE);
        }
        initView();
    }

    private void initView() {
        Glide.with(this).load(rentCarModel.getImage()).into(carModelBannerView);
        carModelNameView.setText(rentCarModel.getBrand());
        carModelCarCategoryView.setText(rentCarModel.getCarTategory());
        carModelIsAutoDriverView.setText(String.format("%s/%s座", rentCarModel.getVariableBox(), rentCarModel.getSeat().replace("座", "")));
        dayPriceView.setText(String.format("￥%s", rentCarModel.getDayPrice()));
        totalPriceView.setText(String.format("￥%s", RentCarUtils.day(duration) * rentCarModel.getDayPrice()));
        rentCarTimeView.initTime(startTime, endTime);
        rentCarTimeView.setOnDayChangedListener(new RentCarTimeView.OnDayChangedListener(){
            @Override
            public void onDayChanger(int day) {
                super.onDayChanger(day);
                totalPriceView.setText(String.format("￥%s", day * rentCarModel.getDayPrice()));
            }
        });
        rentCarAddressView.init(takeTheCarAddress,returnTheCarAddress,rentShop,isToHomeService);


    }

    private void postOrder() {
        String name = rentCarPersonNameView.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.showToast(this,"租车人姓名不能为空，请输入！");
            rentCarPersonNameView.requestFocus();
            return;
        }

        String phoneNumber = rentCarPersonPhoneView.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)){
            ToastUtil.showToast(this,"租车人联系电话不能为空，请输入！");
            rentCarPersonPhoneView.requestFocus();
            return;
        }

        if (phoneNumber.length()<11){
            if (TextUtils.isEmpty(phoneNumber)){
                ToastUtil.showToast(this,"请输入11位手机号");
                rentCarPersonPhoneView.requestFocus();
                return;
            }
        }

        String callName = rentCarPersonContactPersonNameView.getText().toString();
        if (TextUtils.isEmpty(callName)){
            ToastUtil.showToast(this,"紧急联系人姓名不能为空，请输入！");
            rentCarPersonContactPersonNameView.requestFocus();
            return;
        }

        String callTelephone = rentCarPersonContactPersonPhoneView.getText().toString();
        if (TextUtils.isEmpty(callTelephone)){
            ToastUtil.showToast(this,"紧急联系人电话不能为空，请输入！");
            rentCarPersonContactPersonPhoneView.requestFocus();
            return;
        }

        if (callTelephone.length()<11){
            ToastUtil.showToast(this,"请输入11位手机号！");
            rentCarPersonContactPersonPhoneView.requestFocus();
            return;
        }

        String idNumber = rentCarPersonIdentifyView.getText().toString();
        if (TextUtils.isEmpty(idNumber)){
            ToastUtil.showToast(this,"租车人身份证不能为空，请输入！");
            rentCarPersonIdentifyView.requestFocus();
            return;
        }

        if (idNumber.length()<18){
            ToastUtil.showToast(this,"请输入合法的身份证号码！");
            rentCarPersonIdentifyView.requestFocus();
            return;
        }

        if (!IdentityCardVerify.cardCodeVerify(idNumber)){
            ToastUtil.showToast(this,"您输入的身份证不符合国家规范，请重新输入！");
            rentCarPersonIdentifyView.requestFocus();
            return;
        }

        isToHomeService = rentCarAddressView.isToHomeService();
        rentShop = rentCarAddressView.getRentShop();
        takeTheCarAddress = rentCarAddressView.getTakeCarAddress();
        returnTheCarAddress = rentCarAddressView.getReturnCarAddress();
        Map<String, Object> params = new HashMap<>();
        params.put("name",name);
        params.put("telephone",phoneNumber);
        params.put("callName",callName);
        params.put("callTelephone",callTelephone);
        params.put("idNumber",idNumber);
        params.put("rentCarId",rentCarModel.getId());
        params.put("rentDay",rentCarTimeView.getDay());
        params.put("takeCarLongitude",isToHomeService?takeTheCarAddress.getLatlng().getLng():rentShop.getLongitude());
        params.put("takeCarLatitude",isToHomeService?takeTheCarAddress.getLatlng().getLat():rentShop.getLatitude());
        params.put("takeCarAddress",isToHomeService?takeTheCarAddress.getPoiaddress():rentShop.getAddress());
        params.put("takeCarTime",DateUtils.formatDateTime(rentCarTimeView.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
        params.put("returnCarLongitude",isToHomeService?returnTheCarAddress.getLatlng().getLng():rentShop.getLongitude());
        params.put("returnCarLatitude",isToHomeService?returnTheCarAddress.getLatlng().getLat():rentShop.getLatitude());
        params.put("returnCarAddress",isToHomeService?returnTheCarAddress.getPoiaddress():rentShop.getAddress());
        params.put("returnCarTime",DateUtils.formatDateTime(rentCarTimeView.getEndTime(),"yyyy-MM-dd HH:mm:ss"));

        if (MyApplication.getaMapLocation().getLongitude()==0){
            params.put("longitude", Objects.requireNonNull(params.get("takeCarLongitude")));
        }else {
            params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        }
        if (MyApplication.getaMapLocation().getLatitude()==0){
            params.put("latitude", Objects.requireNonNull(params.get("takeCarLatitude")));
        }else {
            params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        }

        HttpProxy.obtain().post(PlatformContans.CarRent.addRentCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                BaseModel<String> baseModel = new Gson().fromJson(result, new TypeToken<BaseModel<String>>(){}.getType());
                if (baseModel!=null){
                    if (baseModel.getResultCode()==0){
                        String data = baseModel.getData();
                        OrderPay orderPay = new OrderPay(RentCarOrderActivity.this);
                        orderPay.showPayDialog(data,PlatformContans.Pay.rentCarOrderPay);
                    }else {
                        ToastUtil.showToast(RentCarOrderActivity.this,String.format("%s:%s",baseModel.getResultCode(),baseModel.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.submit_order_view)
    public void onSubmitOrderViewClicked() {
        if (MyApplication.isIsLogin()){
            postOrder();
        }else {
            AvoidOnResult avoidOnResult = new AvoidOnResult(this);
            avoidOnResult.startForResult(RegisterActivity.class, 1, (requestCode, resultCode, data) -> {
                if (requestCode==1 && resultCode==5){
                    postOrder();
                }
            });
        }
    }
}
