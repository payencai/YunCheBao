package com.yuedan.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.RegisterActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookRoadFragment extends Fragment {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_detail)
    EditText et_detail;
    @BindView(R.id.et_type)
    EditText et_type;
    @BindView(R.id.tv_public)
    TextView tv_public;

    public BookRoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_road, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tv_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.isLogin) {
                    if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
                        return;
                    }
                    if (TextUtils.isEmpty(et_address.getEditableText().toString())) {
                        return;
                    }
                    if (TextUtils.isEmpty(et_type.getEditableText().toString())) {
                        return;
                    }
                    if (TextUtils.isEmpty(et_detail.getEditableText().toString())) {
                        return;
                    }
                    addService();
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
    }

    private void addService() {
        Map<String, Object> params = new HashMap<>();
        params.put("telephone", et_phone.getEditableText().toString());
        params.put("detail", et_detail.getEditableText().toString());
        params.put("address", et_address.getEditableText().toString());
        params.put("category", et_type.getText().toString());
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        params.put("province", MyApplication.getaMapLocation().getProvince() + "");
        params.put("city", MyApplication.getaMapLocation().getCity() + "");
        params.put("area", MyApplication.getaMapLocation().getDistrict() + "");
        Log.e("result", params.toString());
        HttpProxy.obtain().post(PlatformContans.Commom.addRoadRescueAppointment, MyApplication.getUserInfo().getToken(), params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Toast.makeText(getContext(), "发布成功", Toast.LENGTH_LONG).show();
                Log.e("result", result);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
