package com.cheyibao;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cheyibao.model.Merchant;
import com.cheyibao.model.Shop;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.model.ShopInfo;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OldCarShopActivity extends AppCompatActivity {

    private ArrayList<String> mTitleList = new ArrayList<>(2);
    private ArrayList<Fragment> mFragments = new ArrayList<>(2);
    @BindView(R.id.vp_gank)
    ViewPager vpGank;
    @BindView(R.id.tab_gank)
    TabLayout tabGank;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    String id;
    int flag = 0;
    MyFragmentPagerAdapter mMyFragmentPagerAdapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_car_shop);
        ButterKnife.bind(this);
        flag = getIntent().getIntExtra("flag", 0);
        id = getIntent().getStringExtra("id");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initFragmentList();
        getShop();
    }

    Merchant merchant;

    private void getShop() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", id);

        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    merchant = new Gson().fromJson(data.toString(), Merchant.class);
                    tv_address.setText(merchant.getProvince() + merchant.getCity() + merchant.getDistrict() + merchant.getAddress());
                    tv_shopname.setText(merchant.getName());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initFragmentList() {
        mTitleList.add("在售车型");
        mFragments.add(new OldCarSaleFragment());
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        vpGank.setAdapter(mMyFragmentPagerAdapter);
        tabGank.setupWithViewPager(vpGank);

    }
}
