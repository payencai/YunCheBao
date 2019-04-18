package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.DateUtils;
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
import com.vipcenter.LoginByaccountActivity;
import com.vipcenter.RegisterActivity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LongRentAppliationActivity extends AppCompatActivity {

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
    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.select_car_model_view)
    TextView selectCarModelView;
    @BindView(R.id.select_shop_count)
    TextView selectShopCount;
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
    @BindView(R.id.submit_application_tv)
    TextView submitApplicationTv;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_rent_appliation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.select_car_model_view)
    public void onSelectCarModelViewClicked() {
    }

    @OnClick(R.id.select_shop_count)
    public void onSelectShopCountClicked() {
    }



    @OnClick(R.id.submit_application_tv)
    public void onSubmitApplicationTvClicked() {
        if (MyApplication.isLogin){
            loadDataType.initData();
        }else {
            AvoidOnResult avoidOnResult = new AvoidOnResult(this);
            avoidOnResult.startForResult(RegisterActivity.class, 1, (requestCode, resultCode, data) -> {
                loadDataType.initData();
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
            map.put("name",name);
            map.put("telephone",telephone);
            map.put("callName",callName);
            map.put("callTelephone",callTelephone);
            map.put("idNumber",idNumber);
            map.put("brand","");
            map.put("carTategory","");
            map.put("variableBox","");
            map.put("seat","");
            map.put("image","");
            map.put("remark",remarksEt.getText().toString());
            map.put("rentDay",rentCarTimeView.getDay());
            map.put("isTake",rentCarAddressView.isToHomeService());
            map.put("takeCarLongitude",rentCarAddressView.getTakeLongitude());
            map.put("takeCarLatitude",rentCarAddressView.getTakeLatitude());
            map.put("takeCarAddress",rentCarAddressView.getTakeCarDetailAddress());
            map.put("takeCarTime", DateUtils.formatDateTime(rentCarTimeView.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
            map.put("isReturn", rentCarAddressView.isToHomeService());
            map.put("returnCarLongitude", rentCarAddressView.getReturnLongitude());
            map.put("returnCarLatitude", rentCarAddressView.getReturnLatitude());
            map.put("returnCarTime", DateUtils.formatDateTime(rentCarTimeView.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
            map.put("longitude", MyApplication.getaMapLocation().getLongitude());
            map.put("latitude", MyApplication.getaMapLocation().getLatitude());
            map.put("shopNumber",1);
            return map;
        }

        @Override
        public void submitData() {
            Map<String,Object> map = initParam();
            if (map ==null)return;
            HttpProxy.obtain().post(PlatformContans.CarRent.addRentCarApply,MyApplication.token, map, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<String>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<String>() {
                        @Override
                        public void onFailed() {
                            ToastUtil.showToast(LongRentAppliationActivity.this,"长租申请提交失败！");
                        }

                        @Override
                        public void onSuccess(String s) {
                            ToastUtil.showToast(LongRentAppliationActivity.this,s);
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showToast(LongRentAppliationActivity.this,"长租申请提交失败！");
                }
            });
        }
    };
}
