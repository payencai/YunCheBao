package com.maket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.model.GoodDetail;
import com.maket.model.GoodParam;
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
    GoodParam mGoodParam;
    GoodParam.SecondSpecificationsBean mGoddsParamChild;
    PersonAddress personAddress;
    GoodDetail mGoodDetail;
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
    @BindView(R.id.tv_shop)
    TextView tv_shop;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_param)
    TextView tv_param;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.et_remark)
    TextView et_remark;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pay);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mGoodParam= (GoodParam) bundle.getSerializable("param");
            mGoddsParamChild= (GoodParam.SecondSpecificationsBean) bundle.getSerializable("child");
            mGoodDetail= (GoodDetail) bundle.getSerializable("detail");
            count=bundle.getInt("count");
        }

        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        tv_name.setText(mGoodDetail.getName());
        tv_shop.setText(mGoodDetail.getBabyMerchantName());
        tv_param.setText(mGoodParam.getSpecificationsValue()+"*"+mGoddsParamChild.getSpecificationsValue());
        tv_count.setText("x"+count);
        tv_total.setText("￥"+(count*mGoddsParamChild.getPrice()));
        tv_totalmoney.setText("￥"+(count*mGoddsParamChild.getPrice()));
        tv_newprice.setText("￥"+mGoddsParamChild.getPrice());
        Glide.with(this).load(mGoodDetail.getCommodityImage()).into(iv_logo);
        addressLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SinglePayActivity.this, AddressListActivity.class), 1);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeOrder();
            }
        });
        getAddress();
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

    private void takeOrder(){
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        } else {
            return;
        }
        String commodityId=mGoodParam.getBabyCommodityId();
        int number=count;
        String name=personAddress.getNickname();
        String telephone=personAddress.getTelephone();
        String address=personAddress.getProvince()+personAddress.getCity()+personAddress.getDistrict()+personAddress.getAddress();
        String remark=et_remark.getEditableText().toString();
        String firstSpecificationId=mGoodParam.getId();
        String secondSpecificationId=mGoddsParamChild.getId();
         Map<String,Object> params=new HashMap<>();
        params.put("commodityId",commodityId);
        params.put("number",number);
        params.put("name",name);
        params.put("telephone",telephone);
        params.put("address",address);
        params.put("remark",remark);
        params.put("firstSpecificationId",firstSpecificationId);
        params.put("secondSpecificationId",secondSpecificationId);
        HttpProxy.obtain().post(PlatformContans.GoodsOrder.addOrder, token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("addOrder",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        String orderId=jsonObject.getString("data");
                        Intent intent=new Intent(SinglePayActivity.this,GoodsPayActivity.class);
                        intent.putExtra("orderid",orderId);
                        money=count*mGoddsParamChild.getPrice()+"";
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
