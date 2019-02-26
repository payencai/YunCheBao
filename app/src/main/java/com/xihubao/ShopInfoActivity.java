package com.xihubao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.Banner;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.model.ShopInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopInfoActivity extends AppCompatActivity {
    String id;
    ShopInfo mShopInfo;
    @BindView(R.id.iv_logo)
    ImageView iv_logo;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_account)
    TextView tv_account;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        getData();
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("shopId",id);
        HttpProxy.obtain().get(PlatformContans.MerchAdmin.getMerchInformationByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("heart",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mShopInfo=new Gson().fromJson(data.toString(),ShopInfo.class);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void setData(){
        Glide.with(this).load(mShopInfo.getImgs()).into(iv_logo);
        tv_account.setText(mShopInfo.getYcbAccount());
        tv_nick.setText(mShopInfo.getName());
        tv_content.setText(mShopInfo.getPersonSign());
    }
}
