package com.xihubao;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.entity.FourShop;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/13.
 */

public class Shop4SOrderActivity extends NoHttpBaseActivity {
    String maintainTime;
    String carType;
    String buyTime;
    String mileage;
    String city;
    FourShop mFourShop;
    @BindView(R.id.carNo)
    EditText carNo;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.callBtn)
    SuperTextView callBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_4s_order_submit);
        Bundle bundle=getIntent().getExtras();
        maintainTime=bundle.getString("maintainTime");
        carType=bundle.getString("carType");
        mileage=bundle.getString("mileage");
        buyTime=bundle.getString("buyTime");
        city=bundle.getString("city");
        mFourShop= (FourShop) bundle.getSerializable("fourshop");
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"4S保养预约");
        ButterKnife.bind(this);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(carNo.getEditableText().toString())){
                    Toast.makeText(Shop4SOrderActivity.this,"请输入车牌号！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(et_name.getEditableText().toString())){
                    Toast.makeText(Shop4SOrderActivity.this,"请输入联系人！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                    Toast.makeText(Shop4SOrderActivity.this,"请输入手机号！",Toast.LENGTH_SHORT).show();
                    return;
                }
                postData();
            }
        });
    }
    private void postData(){
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",mFourShop.getId());
        params.put("maintainTime",maintainTime);
        params.put("carType",carType);
        params.put("carNumber",carNo.getEditableText().toString());
        params.put("name",et_name.getEditableText().toString());
        params.put("telephone",et_phone.getEditableText().toString());
        params.put("city",city);
        params.put("buyTime",buyTime);
        params.put("mileage",mileage);
        UserInfo userInfo= MyApplication.getUserInfo();
        String token=MyApplication.token;
        Log.e("param",params.toString());
        HttpProxy.obtain().post(PlatformContans.FourShop.addFourShopOrder,token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String data=jsonObject.getString("data");
                    Toast.makeText(Shop4SOrderActivity.this,data,Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {

            }
        });

    }
    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
