package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.cheyibao.model.CarMeal;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentShop;
import com.cheyibao.view.RentCarAddressView;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.IdentityCardVerify;
import com.common.LoadDataType;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.vipcenter.RegisterActivity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookCarActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;
    @BindView(R.id.rent_Car_address_view)
    RentCarAddressView rentCarAddressView;
    @BindView(R.id.is_support_to_home_service_view)
    TextView isSupportToHomeServiceView;
    @BindView(R.id.rent_duration_tv)
    TextView rentDurationTv;
    @BindView(R.id.day_price_view)
    TextView dayPriceView;
    @BindView(R.id.original_price_view)
    TextView originalPriceView;
    @BindView(R.id.cut_down_view)
    TextView cutDownView;
    @BindView(R.id.total_price_view)
    TextView totalPriceView;
    @BindView(R.id.car_banner_view)
    ImageView carBannerView;
    @BindView(R.id.car_brand_view)
    TextView carBrandView;
    @BindView(R.id.car_models_view)
    TextView carModelsView;
    @BindView(R.id.seat_view)
    TextView seatView;
    @BindView(R.id.contact_person_name_et)
    EditText contactPersonNameEt;
    @BindView(R.id.contact_person_phone_et)
    EditText contactPersonPhoneEt;
    @BindView(R.id.identify_card_et)
    EditText identifyCardEt;
    @BindView(R.id.remarks_et)
    EditText remarksEt;
    @BindView(R.id.mergency_contact_person_name_et)
    EditText mergencyContactPersonNameEt;
    @BindView(R.id.emergency_contact_phone_et)
    EditText emergencyContactPhoneEt;
    @BindView(R.id.submit_booking_tv)
    TextView submitBookingTv;

    private CarMeal carMeal;
    private RentShop longRentShop;
    private RentCarModel rentCarModel;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_book_car);
        ButterKnife.bind(this);
        title.setText("预定");
        Intent intent = getIntent();
        carMeal = intent.getParcelableExtra("car_meal");
        longRentShop = intent.getParcelableExtra("rent_shop");
        rentCarModel = intent.getParcelableExtra("rent_car_model");

        bindView();
    }

    private void bindView(){
        rentCarAddressView.setIsEnabled(false);
        rentCarAddressView.init(longRentShop);
        rentCarAddressView.setIsEnabled(longRentShop.getIsOnlineServe()==1);
        String text;
        switch (longRentShop.getIsOnlineServe()){
            case 0:
                text = "未知";
                break;
            case 1:
                text = "支持";
                break;
            case 2:
                text = "不支持";
                break;
                default:text= "";
        }
        isSupportToHomeServiceView.setText(text);

        rentDurationTv.setText(String.format("%s天",carMeal.getRentDay()));
        dayPriceView.setText(String.format("%s元",carMeal.getDayPrice()));
        originalPriceView.setText(String.format("原价%s元",carMeal.getOriginalPrice()));
        originalPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        totalPriceView.setText(String.format("￥%s",carMeal.getAllPrice()));
        cutDownView.setText(String.format("省%s",carMeal.getDiscount()));

        Glide.with(this).load(rentCarModel.getImage()).into(carBannerView);
        carBrandView.setText(rentCarModel.getBrand());
        carModelsView.setText(rentCarModel.getCarTategory());
        if (!TextUtils.isEmpty(rentCarModel.getSeat())){
            seatView.setText(String.format("%s/%s",rentCarModel.getVariableBox(),rentCarModel.getSeat().contains("座")?rentCarModel.getSeat():String.format("%s座",rentCarModel.getSeat())));
        }
    }

    @OnClick(R.id.back)
    public void onBackView(){
        onBackPressed();
    }

    @OnClick(R.id.submit_booking_tv)
    public void onViewClicked() {
        if (MyApplication.isLogin){
            loadDataType.submitData();
        }else {
            AvoidOnResult avoidOnResult = new AvoidOnResult(this);
            avoidOnResult.startForResult(RegisterActivity.class, 1, (requestCode, resultCode, data) -> {
                loadDataType.submitData();
            });
        }
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            String name = contactPersonNameEt.getText().toString();
            if (TextUtils.isEmpty(name)){
                ToastUtil.showToast(context,"联系人姓名不能为空");
                contactPersonNameEt.requestFocus();
                return null;
            }
            String telephone = contactPersonPhoneEt.getText().toString();
            if (TextUtils.isEmpty(telephone)){
                ToastUtil.showToast(context,"联系人电话不能为空");
                return null;
            }
            if (telephone.length()<11){
                ToastUtil.showToast(context,"请输入11位电话号码");
                return null;
            }
            String callName = mergencyContactPersonNameEt.getText().toString();
            if (TextUtils.isEmpty(callName)){
                ToastUtil.showToast(context,"紧急联系人姓名不能为空");
                return null;
            }
            String callTelephone = emergencyContactPhoneEt.getText().toString();
            if (TextUtils.isEmpty(callTelephone)){
                ToastUtil.showToast(context,"紧急联系人电话不能为空");
                return null;
            }
            if (callTelephone.length()<11){
                ToastUtil.showToast(context,"请输入11位手机号");
                return null;
            }
            String idNumber = identifyCardEt.getText().toString();
            if (TextUtils.isEmpty(idNumber)){
                ToastUtil.showToast(context,"联系人身份证号不能为空");
                return null;
            }
            if (idNumber.length()<11){
                ToastUtil.showToast(context,"请输入18位身份证号");
                return null;
            }

            if (!IdentityCardVerify.cardCodeVerify(idNumber)){
                ToastUtil.showToast(context,"请输入合法的身份证号码！");
                return null;
            }

            map.put("longCarId",carMeal.getLongCarId());
            map.put("mealId",carMeal.getId());

            map.put("name",name);
            map.put("telephone",telephone);
            map.put("callName",callName);
            map.put("callTelephone",callTelephone);
            map.put("idNumber",idNumber);
            map.put("remark",remarksEt.getText().toString());
            map.put("isTake",rentCarAddressView.isToHomeService()?1:2);
            map.put("takeCarLongitude",rentCarAddressView.getTakeLongitude());
            map.put("takeCarLatitude",rentCarAddressView.getTakeLatitude());
            map.put("takeCarAddress",rentCarAddressView.getTakeCarDetailAddress());
            map.put("isReturn", rentCarAddressView.isToHomeService()?1:2);
            map.put("returnCarLongitude", rentCarAddressView.getReturnLongitude());
            map.put("returnCarLatitude", rentCarAddressView.getReturnLatitude());
            map.put("returnCarAddress", rentCarAddressView.getReturnCarDetailAddress());
            return map;
        }

        @Override
        public void submitData() {
            Map<String,Object> map = initParam();
            if (map ==null)return;
            HttpProxy.obtain().post(PlatformContans.CarRent.addRentCarAppointment,MyApplication.token, map, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<String>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<String>() {
                        @Override
                        public void onFailed() {
                            ToastUtil.showToast(context,"长租申请提交失败！");
                        }

                        @Override
                        public void onSuccess(String s) {
                            ToastUtil.showToast(context,s);
                            finish();
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            super.onSuccessBaseModel(baseModel);
                            if (baseModel!=null){
                                ToastUtil.showToast(context,baseModel.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showToast(context,"长租申请提交失败！");
                }
            });
        }
    };
}
