package com.cheyibao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.application.MyApplication;
import com.caryibao.NewCar;
import com.cheyibao.adapter.ShopItemAdapter;
import com.cheyibao.model.Shop;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.listview.PersonalListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCarDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.lv_shop)
    PersonalListView mListView;
    List<Shop> mShops=new ArrayList<>();
    ShopItemAdapter mShopItemAdapter;
    NewCar mNewCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_detail);
        mNewCar= (NewCar) getIntent().getExtras().getSerializable("data");
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        mShopItemAdapter=new ShopItemAdapter(this,mShops);
        mListView.setAdapter(mShopItemAdapter);
        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewCarDetailActivity.this,NewCarShopActivity.class);
                intent.putExtra("flag",1);
                startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewCarDetailActivity.this,NewCarShopActivity.class);
                intent.putExtra("flag",2);
                startActivity(intent);
            }
        });
        getShop();
    }
    private void getShop(){
        Map<String, Object> params = new HashMap<>();
        //params.put("page", page);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude()+"") ;
        params.put("latitude",MyApplication.getaMapLocation().getLatitude()+"");
        params.put("carCategoryDetailId",mNewCar.getId());
        HttpProxy.obtain().get(PlatformContans.NewCar.getMerchantList, params, MyApplication.getUserInfo().getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getMerchantList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Shop baikeItem = new Gson().fromJson(item.toString(), Shop.class);
                        mShops.add(baikeItem);
                    }
                    mShopItemAdapter.notifyDataSetChanged();
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
