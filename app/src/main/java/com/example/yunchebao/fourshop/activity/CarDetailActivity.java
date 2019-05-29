package com.example.yunchebao.fourshop.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.NewCarParamsActivity;
import com.cheyibao.adapter.NewCarParamsAdapter;
import com.cheyibao.model.NewCarParams;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.bean.FourShopCar;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.listview.PersonalListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDetailActivity extends AppCompatActivity {
    FourShopCar mFourShopCar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_oldprice)
    TextView tv_oldprice;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;
    @BindView(R.id.tv_color)
    TextView tv_color;
    @BindView(R.id.lv_params)
    PersonalListView lv_params;
    @BindView(R.id.banner)
    Banner banner;
    NewCarParamsAdapter mNewCarParamsAdapter;
    NewCarParams mNewCarParams;
    List<String> images = new ArrayList<>();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
       getDetail();
       findViewById(R.id.tv_param).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarDetailActivity.this, NewCarParamsActivity.class);
                intent.putExtra("param", mFourShopCar.getCarCategoryDetailId());
                startActivity(intent);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    private void setData(){
        tv_oldprice.setText("厂家指导价" + mFourShopCar.getAdvicePrice());
        tv_newprice.setText("￥" + mFourShopCar.getNakedCarPrice());
        String name = mFourShopCar.getFirstName();
        if (!TextUtils.isEmpty(mFourShopCar.getSecondName()) && !"null".equals(mFourShopCar.getSecondName())) {
            name = name + mFourShopCar.getSecondName();
        }
        if (!TextUtils.isEmpty(mFourShopCar.getThirdName()) && !"null".equals(mFourShopCar.getThirdName())) {
            name = name + mFourShopCar.getThirdName();
        }
        tv_color.setText(mFourShopCar.getColor());
        tv_name.setText(name);
        initBanner();
        getParams();
    }
    private void getDetail(){
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        HttpProxy.obtain().get(PlatformContans.FourShop.getFourCarDetailsById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("params", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mFourShopCar=new Gson().fromJson(data.toString(), FourShopCar.class);
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
    private void initBanner() {
        String banner1=mFourShopCar.getCarCategoryDetail().getBanner1();
        String banner2=mFourShopCar.getCarCategoryDetail().getBanner2();
        String banner3=mFourShopCar.getCarCategoryDetail().getBanner3();
        if(!TextUtils.isEmpty(banner1)){
            if(banner1.contains(",")){
                String []pics=banner1.split(",");
                for (int i = 0; i <pics.length; i++) {
                    images.add(pics[i]);
                }
            }else{
                images.add(banner1);
            }
        }

        if(!TextUtils.isEmpty(banner2)){
            if(banner2.contains(",")){
                String []pics=banner1.split(",");
                for (int i = 0; i <pics.length; i++) {
                    images.add(pics[i]);
                }
            }else{
                images.add(banner1);
            }
        }
        if(!TextUtils.isEmpty(banner3)){
            if(banner3.contains(",")){
                String []pics=banner1.split(",");
                for (int i = 0; i <pics.length; i++) {
                    images.add(pics[i]);
                }
            }else{
                images.add(banner1);
            }
        }
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(CarDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }
    private void getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("carCategoryDetailId", mFourShopCar.getCarCategoryDetailId());
        HttpProxy.obtain().get(PlatformContans.NewCar.getDetailParams, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("params", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray param = data.getJSONArray("param");
                    if (param.length() > 0) {
                        JSONObject item = param.getJSONObject(0);
                        mNewCarParams = new Gson().fromJson(item.toString(), NewCarParams.class);
                        if (mNewCarParams.getParam().size() > 0) {
                            NewCarParams.ParamBean paramBean = mNewCarParams.getParam().get(0);
                            paramBean.setParent(mNewCarParams.getParamValue());
                            List<NewCarParams.ParamBean> paramBeans = mNewCarParams.getParam();
                            paramBeans.remove(0);
                            paramBeans.add(0, paramBean);
                            mNewCarParamsAdapter = new NewCarParamsAdapter(CarDetailActivity.this, paramBeans);
                            lv_params.setAdapter(mNewCarParamsAdapter);
                        }

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
}
