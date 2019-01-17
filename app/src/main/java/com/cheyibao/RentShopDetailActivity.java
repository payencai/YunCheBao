package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.adapter.RentCarImageAdapter;
import com.cheyibao.adapter.RentShopListAdapter;
import com.cheyibao.fragment.RentComentFragment;
import com.cheyibao.fragment.RentShopFragment;
import com.cheyibao.model.RentCar;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.adapter.GoodsCommentImageAdapter;
import com.maket.adapter.GoodsOrderImageAdapter;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.adapter.MyFragmentPagerAdapter;
import com.tool.listview.PersonalScrollView;
import com.tool.listview.PersonalViewPager;
import com.tool.view.GridViewForScrollView;
import com.tool.view.HorizontalListView;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/24.
 */

public class RentShopDetailActivity extends AppCompatActivity {
    private Context ctx;

    TabLayout tab_shop;
    ViewPager vp_shop;
    @BindView(R.id.tv_shopname)
    TextView tv_shopname;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.hlv_photo)
    HorizontalListView hlv_photo;
    MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private List<String> mTabTitles=new ArrayList<>();
    private List<Fragment> mFragments=new ArrayList<>();
    GoodsOrderImageAdapter mRentCarImageAdapter;
    List<String> images=new ArrayList<>();
    RentCar mRentCar;

    public RentCar getRentCar() {
        return mRentCar;
    }

    public void setRentCar(RentCar rentCar) {
        mRentCar = rentCar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rent_shop_detail);
        mRentCar= (RentCar) getIntent().getSerializableExtra("data");
        ButterKnife.bind(this);
        initView();
        setUI();
    }
    private void setUI(){
        tv_shopname.setText(mRentCar.getName());
        tv_address.setText(mRentCar.getAddress());
        tv_phone.setText(mRentCar.getTelephone());
        getPhoto();
    }


    private void initView() {
        vp_shop= (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
        tab_shop= (TabLayout)findViewById(R.id.id_stickynavlayout_indicator);
        mTabTitles.add("租借车型");
        mTabTitles.add("查看评论");
        mFragments.add(new RentShopFragment());
        mFragments.add(new RentComentFragment());
        mMyFragmentPagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragments,mTabTitles);
        vp_shop.setAdapter(mMyFragmentPagerAdapter);
        vp_shop.setOffscreenPageLimit(1);
        tab_shop.setupWithViewPager(vp_shop);
//        tab_shop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                vp_shop.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    @OnClick({R.id.back})
    public void Onclick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }


    private void getPhoto(){
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId",mRentCar.getId());
        HttpProxy.obtain().get(PlatformContans.CarRent.getRentCarPhoto, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getBannerList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        String url = data.getString(i);
                        images.add(url);
                    }
                    mRentCarImageAdapter=new GoodsOrderImageAdapter(RentShopDetailActivity.this,images);
                    hlv_photo.setAdapter(mRentCarImageAdapter);

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
