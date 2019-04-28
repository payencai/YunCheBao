package com.cheyibao.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.ShopListActivity;
import com.cheyibao.model.RentCarModel;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.RentCarUtils;
import com.common.AvoidOnResult;
import com.common.ResourceUtils;
import com.example.yunchebao.R;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RentCarAddressView extends LinearLayout {
    private static final int REQUEST_CODE_ADDRESS_FOR_MAP_SEND = 1;
    private static final int REQUEST_CODE_ADDRESS_FOR_MAP_TAKE = 2;
    private static final int REQUEST_CODE_ADDRESS_FOR_STORE_SEND = 3;
    private static final int TYPE_NONE = 1;
    private static final int TYPE_BY_CAR = 2;

    @BindView(R.id.take_car_city_text_view)
    TextView takeCarCityTextView;
    @BindView(R.id.take_car_address_text_view)
    TextView takeCarAddressTextView;
    @BindView(R.id.is_to_home_service_view)
    CheckBox isToHomeServiceView;
    @BindView(R.id.return_the_car_city_text_view)
    TextView returnTheCarCityTextView;
    @BindView(R.id.return_the_car_address_text_view)
    TextView returnTheCarAddressTextView;

    private AddressBean takeCarAddress;
    private AddressBean returnCarAddress;
    private RentShop rentShop;
    private boolean isEnabled;
    private int type = TYPE_NONE;
    private RentCarModel rentCarModel;

    public RentCarAddressView(Context context) {
        this(context, null);
    }

    public RentCarAddressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RentCarAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RentCarAddressView);
        isEnabled = ta.getBoolean(R.styleable.RentCarAddressView_enabled,true);
        ta.recycle();  //注意回收
        LayoutInflater.from(context).inflate(R.layout.view_rent_car_address_view, this);
        ButterKnife.bind(this);

        isToHomeServiceView.setEnabled(isEnabled);
        takeCarAddressTextView.setEnabled(isEnabled);
        returnTheCarAddressTextView.setEnabled(isEnabled);

        isToHomeServiceView.setButtonDrawable(drawables());


        isToHomeServiceView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                bindViewData(takeCarAddress);
            }else {
                bindViewData(rentShop);
            }
        });


    }

    public void setIsEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        isToHomeServiceView.setEnabled(isEnabled);
        if (!isEnabled){
            isToHomeServiceView.setVisibility(GONE);
        }else {
            isToHomeServiceView.setVisibility(VISIBLE);
        }
        takeCarAddressTextView.setEnabled(isEnabled);
        returnTheCarAddressTextView.setEnabled(isEnabled);
    }

    private StateListDrawable drawables() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, ResourceUtils.getDrawableByResource(getContext(), R.mipmap.carrental_btn_checkthe_selected));
        stateListDrawable.addState(new int[]{}, ResourceUtils.getDrawableByResource(getContext(), R.mipmap.carrental_btn_checkthe_normal));
        return stateListDrawable;
    }

    @OnClick(R.id.take_car_address_text_view)
    public void onTakeCarAddressTextViewClicked() {
        AvoidOnResult avoidOnResult = new AvoidOnResult((FragmentActivity)getContext());
        if (isToHomeServiceView.isChecked()){
            avoidOnResult.startForResult(X5WebviewActivity.class, REQUEST_CODE_ADDRESS_FOR_MAP_SEND, (requestCode, resultCode, data) -> {
                if (data!=null) {
                    takeCarAddress = (AddressBean) data.getSerializableExtra("address");
                    if (returnCarAddress==null) {
                        returnCarAddress = takeCarAddress;
                    }
                   bindViewData(takeCarAddress);
                }
            });
        }else {
            Boolean isOnlyAddress;
            if (RentCarUtils.rentCarInfo==null){
                isOnlyAddress = null;
            }else {
                isOnlyAddress = RentCarUtils.rentCarInfo.get(RentCarUtils.IS_EDIT_ORDER_ONLY_ADDRESS)==null?null: (Boolean) RentCarUtils.rentCarInfo.get(RentCarUtils.IS_EDIT_ORDER_ONLY_ADDRESS);
            }
            if (isOnlyAddress==null || !isOnlyAddress){
                Intent intent = new Intent(getContext(),ShopListActivity.class);
                if (type == TYPE_BY_CAR){
                    intent.putExtra("rent_car_model",rentCarModel);
                }
                avoidOnResult.startForResult(intent, REQUEST_CODE_ADDRESS_FOR_STORE_SEND, (requestCode, resultCode, data) -> {
                    if (data!=null) {
                        rentShop = data.getParcelableExtra("rent_shop");
                        bindViewData(rentShop);
                    }
                });
            }
        }
    }

    @OnClick(R.id.return_the_car_address_text_view)
    public void onReturnTheCarAddressTextViewClicked() {
        AvoidOnResult avoidOnResult = new AvoidOnResult((FragmentActivity)getContext());
        if (isToHomeServiceView.isChecked()){
            avoidOnResult.startForResult(X5WebviewActivity.class, REQUEST_CODE_ADDRESS_FOR_MAP_TAKE, (requestCode, resultCode, data) -> {
                if (data!=null) {
                    returnCarAddress = (AddressBean) data.getSerializableExtra("address");
                    if (takeCarAddress==null){
                        takeCarAddress = returnCarAddress;
                    }
                    bindViewData(takeCarAddress);
                }
            });
        }else {
            Boolean isOnlyAddress;
            if (RentCarUtils.rentCarInfo==null){
                isOnlyAddress = null;
            }else {
                isOnlyAddress = RentCarUtils.rentCarInfo.get(RentCarUtils.IS_EDIT_ORDER_ONLY_ADDRESS)==null?null: (Boolean) RentCarUtils.rentCarInfo.get(RentCarUtils.IS_EDIT_ORDER_ONLY_ADDRESS);
            }
            if (isOnlyAddress==null || !isOnlyAddress){
                Intent intent = new Intent(getContext(),ShopListActivity.class);
                if (type == TYPE_BY_CAR){
                    intent.putExtra("rent_car_model",rentCarModel);
                }
                avoidOnResult.startForResult(intent, REQUEST_CODE_ADDRESS_FOR_STORE_SEND, (requestCode, resultCode, data) -> {
                    if (data!=null) {
                        rentShop = data.getParcelableExtra("rent_shop");
                        bindViewData(rentShop);
                    }
                });
            }
        }
    }

    public void init(AddressBean takeCarAddress,AddressBean returnCarAddress,RentShop rentShop,Boolean isToHomeService){
        this.takeCarAddress = takeCarAddress;
        this.returnCarAddress = returnCarAddress;
        this.rentShop = rentShop;
        if (isToHomeService==null){
            isToHomeServiceView.setVisibility(GONE);
            isToHomeServiceView.setChecked(false);
            bindViewData(rentShop);
        }else {
            if (isEnabled){
                isToHomeServiceView.setVisibility(VISIBLE);
            }else {
                isToHomeServiceView.setVisibility(GONE);
            }
            isToHomeServiceView.setChecked(isToHomeService);
            if (isToHomeService){
                bindViewData(takeCarAddress);
            }else {
                bindViewData(rentShop);
            }
        }

    }

    public void init(RentShop rentShop){
        this.rentShop = rentShop;
        if (!isEnabled){
            isToHomeServiceView.setChecked(rentShop.getIsOnlineServe()==1);
        }
//        setIsEnabled(false);
        bindViewData(rentShop);
    }

    public void init(RentCarModel rentCarModel,boolean isToHomeEnable, boolean isToHomeService){
        this.rentCarModel = rentCarModel;
        if (this.rentCarModel!=null){
            type = TYPE_BY_CAR;
        }
        isToHomeServiceView.setChecked(isToHomeService);
        isToHomeServiceView.setEnabled(false);
        if (isToHomeEnable){
            isToHomeServiceView.setVisibility(VISIBLE);
        }else {
            isToHomeServiceView.setVisibility(GONE);
        }
    }

    private void bindViewData(Object object){
        if (object==null){
            takeCarCityTextView.setText("");
            takeCarAddressTextView.setText("");
            returnTheCarCityTextView.setText("");
            returnTheCarAddressTextView.setText("");
            return;
        }
        if (object instanceof RentShop){
            takeCarCityTextView.setText(rentShop.getCity());
            takeCarAddressTextView.setText(rentShop.getName());
            returnTheCarCityTextView.setText(rentShop.getCity());
            returnTheCarAddressTextView.setText(rentShop.getName());
            isToHomeServiceView.setChecked(false);
        }else {
            takeCarCityTextView.setText(takeCarAddress.getCityname());
            takeCarAddressTextView.setText(takeCarAddress.getPoiaddress());
            returnTheCarCityTextView.setText(returnCarAddress.getCityname());
            returnTheCarAddressTextView.setText(returnCarAddress.getPoiaddress());
            isToHomeServiceView.setChecked(true);
        }
    }

    public AddressBean getReturnCarAddress() {
        return returnCarAddress;
    }

    public AddressBean getTakeCarAddress() {
        return takeCarAddress;
    }

    public RentShop getRentShop() {
        return rentShop;
    }

    public void setIsToHomeServiceView(boolean isToHomeService) {
        this.isToHomeServiceView.setChecked(isToHomeService);
    }

    public boolean isToHomeService() {
        return isToHomeServiceView.isChecked();
    }
    public double getTakeLongitude(){
        if (isToHomeService()){
            if (takeCarAddress==null){
                ToastUtil.showToast(getContext(),"请选择取车地址");
                return 0;
            }else {
                return takeCarAddress.getLatlng().getLng();
            }
        }else {
            return Double.parseDouble(rentShop.getLongitude());
        }
    }

    public double getTakeLatitude(){
        if (isToHomeService()) {
            if (takeCarAddress == null) {
                ToastUtil.showToast(getContext(), "请选择取车地址");
                return 0;
            } else{
                return takeCarAddress.getLatlng().getLat();
            }
        }else {
            return Double.parseDouble(rentShop.getLatitude());
        }
    }

    public String getTakeCarDetailAddress(){
        if (isToHomeService()){
            if (takeCarAddress == null) {
                ToastUtil.showToast(getContext(), "请选择取车地址");
                return "";
            }else {
                return takeCarAddress.getPoiaddress();
            }

        }else {
            return rentShop.getAddress();
        }
    }

    public double getReturnLongitude(){
        if (isToHomeService()){
            if (returnCarAddress == null) {
                ToastUtil.showToast(getContext(), "请选择还车地址");
                return 0;
            }else {
                return returnCarAddress.getLatlng().getLng();
            }

        }else {
            return Double.parseDouble(rentShop.getLongitude());
        }
    }

    public double getReturnLatitude(){
        if (isToHomeService()){
            if (returnCarAddress == null) {
                ToastUtil.showToast(getContext(), "请选择还车地址");
                return 0;
            }else {
                return returnCarAddress.getLatlng().getLat();
            }

        }else {
            return Double.parseDouble(rentShop.getLatitude());
        }
    }

    public String getReturnCarDetailAddress(){
        if (isToHomeService()){
            if (returnCarAddress == null) {
                ToastUtil.showToast(getContext(), "请选择还车地址");
                return "";
            }else {
                return returnCarAddress.getPoiaddress();
            }

        }else {
            return rentShop.getAddress();
        }
    }

}
