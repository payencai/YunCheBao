package com.vipcenter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.nohttp.sample.NoHttpBaseActivity;
import com.payencai.library.util.ToastUtil;
import com.vipcenter.model.PersonAddress;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fanzh on 2017/11/16.
 */

public class AddressAddActivity extends NoHttpBaseActivity {
    @BindView(R.id.address)
    TextView addrText;
    @BindView(R.id.name)
    EditText nameEdit;
    @BindView(R.id.mobile)
    EditText mobileEdit;
    @BindView(R.id.detailAddress)
    EditText detailEdit;
    @BindView(R.id.de_box)
    CheckBox de_box;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String mProvince;
    String mCity;
    String mArea;
    PersonAddress mPersonAddress;
    //申明对象
    CityPickerView mPicker = new CityPickerView();
    private PhoneAddressEntity entity = null;
    int flag = 1;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_add_layout);
        mPersonAddress = (PersonAddress) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        mPicker.init(this);
        initView();
    }

    private void showSelectDialog() {
//添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                mProvince = province.getName();
                mCity = city.getName();
                mArea = district.getName();
                addrText.setText(province.getName() + city.getName() + district.getName());
                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(AddressAddActivity.this, "已取消");
            }
        });

        //显示
        mPicker.showCityPicker();
    }

    private void initView() {

        if (mPersonAddress != null) {
            flag = 2;
            id = mPersonAddress.getId();
            addrText.setText(mPersonAddress.getProvince() + mPersonAddress.getCity() + mPersonAddress.getDistrict());
            detailEdit.setText(mPersonAddress.getAddress());
            nameEdit.setText(mPersonAddress.getNickname());
            mobileEdit.setText(mPersonAddress.getTelephone());
            if (mPersonAddress.getIsDefault() == 1) {
                de_box.setChecked(true);
            } else {
                de_box.setChecked(false);
            }
            mProvince = mPersonAddress.getProvince();
            mCity = mPersonAddress.getCity();
            mArea = mPersonAddress.getDistrict();
        }
        if (flag ==2){
            tv_title.setText("修改地址");
        }

    }

    private void addAddress() {
        int isDefult = 0;
        if (de_box.isChecked()) {
            isDefult = 1;
        } else {
            isDefult = 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("address", detailEdit.getEditableText().toString());
        params.put("city", mCity);
        params.put("district", mArea);
        params.put("isDefault", isDefult);
        params.put("nickname", nameEdit.getEditableText().toString());
        params.put("province", mProvince);
        params.put("telephone", mobileEdit.getEditableText().toString());
        String json = new Gson().toJson(params);
        HttpProxy.obtain().post(PlatformContans.AddressManage.addUserAddress, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ToastUtil.showToast(AddressAddActivity.this, "添加成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    private boolean checkForm() {
        if (entity != null) {
            if (entity.getProvince() != null && !entity.getProvince().equals("")) {
                if (nameEdit.getText() != null && !nameEdit.getText().toString().equals("")) {
                    entity.setName(nameEdit.getText().toString());
                    if (mobileEdit.getText() != null && !mobileEdit.getText().toString().equals("")) {
                        entity.setMobile(mobileEdit.getText().toString());
                        if (detailEdit.getText() != null && !detailEdit.getText().toString().equals("")) {
                            entity.setDetail(detailEdit.getText().toString());
                        } else {
                            setToast("请输入详细地址");
                            return false;
                        }
                    } else {
                        setToast("请输入联系人电话");
                        return false;
                    }
                } else {
                    setToast("请输入联系人姓名");
                    return false;
                }
            } else {
                setToast("请选择地址");
                return false;
            }
        }
        return true;
    }


    @OnClick({R.id.back, R.id.addressLay, R.id.textBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.addressLay:
                showSelectDialog();
                /// setToast("组件待定");
                break;
            case R.id.textBtn:
                if (checkForm()) {
                    if (flag == 1)
                        addAddress();
                    else {
                        update();
                    }
                }
                break;
        }
    }

    private void update() {
        int isDefult = 0;
        if (de_box.isChecked()) {
            isDefult = 1;
        } else {
            isDefult = 0;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("address", detailEdit.getEditableText().toString());
        params.put("city", mCity);
        params.put("id", id);
        params.put("district", mArea);
        params.put("isDefault", isDefult);
        params.put("nickname", nameEdit.getEditableText().toString());
        params.put("province", mProvince);
        params.put("telephone", mobileEdit.getEditableText().toString());
        String json = new Gson().toJson(params);
        Log.e("json", json);
        HttpProxy.obtain().post(PlatformContans.AddressManage.updateUserAddress, MyApplication.token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                ToastUtil.showToast(AddressAddActivity.this, "修改成功");
                finish();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    public void setForm(PhoneAddressEntity editEntity) {
        nameEdit.setText(editEntity.getName());
        mobileEdit.setText(editEntity.getMobile());
        addrText.setText(editEntity.getProvince() + editEntity.getCity() + "市" + editEntity.getArea());
        detailEdit.setText(editEntity.getDetail());
    }
}
