package com.yuedan.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.application.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.payencai.library.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class BookNewCarFragment extends BaseFragment {
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_brand)
    EditText et_brand;
    @BindView(R.id.et_color)
    EditText et_color;
    @BindView(R.id.et_type)
    EditText et_type;
    @BindView(R.id.et_des)
    EditText et_des;
    @BindView(R.id.tv_pub)
    SuperTextView tv_pub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.book_new_car_layout, container, false);
        commHiddenKeyboard(rootView);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    double price = 0;
    String carCategory;
    String brand;
    String color;
    String detail;

    private void initView() {
        tv_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price2 = et_price.getEditableText().toString();
                if (TextUtils.isEmpty(price2)) {
                    ToastUtil.showToast(getContext(), "请输入价格");
                    return;
                }
                price = Double.parseDouble(price2);
                carCategory = et_type.getEditableText().toString();
                brand = et_brand.getEditableText().toString();
                color = et_color.getEditableText().toString();
                detail = et_des.getEditableText().toString();
                if (TextUtils.isEmpty(carCategory)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(brand)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(color)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    ToastUtil.showToast(getContext(), "输入不能为空！");
                    return;
                }
                addOrder();
            }
        });
    }

    private void addOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("price", price);
        params.put("carCategory",carCategory);
        params.put("brand",brand);
        params.put("color",color);
        params.put("detail",detail);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude",MyApplication.getaMapLocation().getLatitude());
        params.put("province",MyApplication.getaMapLocation().getProvince());
        params.put("city",MyApplication.getaMapLocation().getCity());
        HttpProxy.obtain().post(PlatformContans.Appointment.addNewCarAppointment, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();
                Log.e("result",result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
