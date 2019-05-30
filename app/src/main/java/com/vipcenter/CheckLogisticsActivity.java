package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.entity.PhoneTraceEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.adapter.LogisticsTraceListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class CheckLogisticsActivity extends NoHttpBaseActivity {
    private Context ctx;
    @BindView(R.id.listView)
    ListViewForScrollView listView;
    private LogisticsTraceListAdapter adapter;
    private List<PhoneTraceEntity.ListBean> list;
    PhoneOrderEntity mPhoneOrderEntity;
    PhoneTraceEntity mPhoneTraceEntity;
    @BindView(R.id.tv_no)
    TextView tv_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_logistics_layout);
        mPhoneOrderEntity= (PhoneOrderEntity) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "查看物流");
        ButterKnife.bind(this);
        ctx = this;
        list = new ArrayList<>();
        adapter = new LogisticsTraceListAdapter(ctx, list);
        listView.setAdapter(adapter);
        tv_no.setText("订单编号："+mPhoneOrderEntity.getOrderNo());
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("companyNo",mPhoneOrderEntity.getExpressCompanyNo());
        params.put("expressNo",mPhoneOrderEntity.getExpressNo());
        HttpProxy.obtain().get(PlatformContans.Commom.getExpressResult, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getExpressResult",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        jsonObject=jsonObject.getJSONObject("data");
                        mPhoneTraceEntity=new Gson().fromJson(jsonObject.toString(),PhoneTraceEntity.class);
                        list.addAll(mPhoneTraceEntity.getList());
                        adapter.notifyDataSetChanged();
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

    private void alertSweet() {
        new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("您的订单目前尚在正常生产中，\n" +
                        "请您耐心等候。感谢您的理解与\n" +
                        "支持。")
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick({R.id.back, R.id.btn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn:
                alertSweet();
                break;
        }
    }
}
