package com.maket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.AddressListActivity;
import com.vipcenter.OrderConfirmActivity;
import com.vipcenter.model.PersonAddress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglePayActivity extends AppCompatActivity {
    PersonAddress personAddress;
    String money="";
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_default)
    TextView tv_default;
    @BindView(R.id.tv_totalmoney)
    TextView tv_totalmoney;
    @BindView(R.id.tv_addrname)
    TextView tv_addrname;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.addressLay)
    LinearLayout addressLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pay);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SinglePayActivity.this, AddressListActivity.class), 1);
            }
        });
    }
    private void getAddress() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("isDefault", 1);
        HttpProxy.obtain().get(PlatformContans.AddressManage.getUserAddress, params, token, new ICallBack() {
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
                            personAddress = new Gson().fromJson(item.toString(), PersonAddress.class);

                        }
                        tv_contact.setText(personAddress.getNickname() + "    " + personAddress.getTelephone());
                        if (personAddress.getIsDefault() == 2) {
                            tv_default.setVisibility(View.GONE);
                        }
                        tv_addrname.setText(personAddress.getProvince() + personAddress.getCity() + personAddress.getDistrict());
                        tv_detail.setText(personAddress.getAddress());

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

    private void takeOrder(String json){
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            return;
            // tv.setText(MyApplication.getaMapLocation().getProvince() + MyApplication.getaMapLocation().getCity() + MyApplication.getaMapLocation().getDistrict());
        }
        //  Map<String,Object> params=new HashMap<>();
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addOrder, token, json, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("rsult",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String orderId=jsonObject.getString("data");

                        Intent intent=new Intent(SinglePayActivity.this,GoodsPayActivity.class);
                        intent.putExtra("orderid",orderId);
                        intent.putExtra("money",money);
                        startActivity(intent);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
