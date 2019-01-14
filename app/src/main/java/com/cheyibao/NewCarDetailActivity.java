package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.caryibao.NewCar;
import com.cheyibao.adapter.ShopItemAdapter;
import com.cheyibao.model.Shop;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.WebviewActivity;
import com.tool.listview.PersonalListView;
import com.youth.banner.Banner;
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

public class NewCarDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.lv_shop)
    PersonalListView mListView;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_oldprice)
    TextView tv_oldprice;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.ll_shop)
    LinearLayout ll_shop;
    @BindView(R.id.lv_params)
    PersonalListView lv_params;
    List<String> images = new ArrayList<>();
    List<Shop> mShops = new ArrayList<>();
    ShopItemAdapter mShopItemAdapter;
    NewCar mNewCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_detail);
        mNewCar = (NewCar) getIntent().getExtras().getSerializable("data");
        ButterKnife.bind(this);
        initView();
    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(NewCarDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }


    private void initView() {
        tv_oldprice.setText("厂家指导价"+mNewCar.getAdvicePrice());
        tv_newprice.setText("￥"+mNewCar.getNakedCarPrice());
        String name=mNewCar.getFirstName();
        if (!TextUtils.isEmpty(mNewCar.getSecondName()) && !"null".equals(mNewCar.getSecondName())){
            name=name+mNewCar.getSecondName();
        }
        if (!TextUtils.isEmpty(mNewCar.getThirdName()) && !"null".equals(mNewCar.getThirdName())){
            name=name+mNewCar.getThirdName();
        }
        tv_name.setText(name);
        if (!TextUtils.isEmpty(mNewCar.getCarCategoryDetail().getBanner1()) && !"null".equals(mNewCar.getCarCategoryDetail().getBanner1()))
            images.add(mNewCar.getCarCategoryDetail().getBanner1());
        if (!TextUtils.isEmpty(mNewCar.getCarCategoryDetail().getBanner2()) && !"null".equals(mNewCar.getCarCategoryDetail().getBanner2()))
            images.add(mNewCar.getCarCategoryDetail().getBanner2());
        if (!TextUtils.isEmpty(mNewCar.getCarCategoryDetail().getBanner3()) && !"null".equals(mNewCar.getCarCategoryDetail().getBanner3()))
            images.add(mNewCar.getCarCategoryDetail().getBanner3());
        initBanner();
        mShopItemAdapter = new ShopItemAdapter(this, mShops);
        mListView.setAdapter(mShopItemAdapter);

        mShops.clear();
        getShop();
    }

    private void getShop() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        }
        Map<String, Object> params = new HashMap<>();
        //params.put("page", page);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        params.put("carCategoryDetailId", mNewCar.getCarCategoryDetailId());
        HttpProxy.obtain().get(PlatformContans.NewCar.getMerchantList, params, token, new ICallBack() {
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
                    if(data.length()==0){
                        ll_shop.setVisibility(View.GONE);
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
