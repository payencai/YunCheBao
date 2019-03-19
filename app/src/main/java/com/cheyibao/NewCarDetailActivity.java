package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.bumptech.glide.Glide;
import com.caryibao.NewCar;
import com.cheyibao.adapter.NewCarParamsAdapter;
import com.cheyibao.adapter.ShopItemAdapter;
import com.cheyibao.model.Merchant;
import com.cheyibao.model.NewCarParams;
import com.cheyibao.model.Shop;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.system.WebviewActivity;
import com.tool.listview.PersonalListView;
import com.vipcenter.RegisterActivity;
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
import io.rong.imkit.RongIM;

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
    @BindView(R.id.tv_param)
    TextView tv_param;
    @BindView(R.id.tv_car)
    TextView tv_buy;
    @BindView(R.id.tv_color)
    TextView tv_color;
    @BindView(R.id.rl_phone)
    RelativeLayout rl_phone;
    NewCarParamsAdapter mNewCarParamsAdapter;
    List<String> images = new ArrayList<>();
    List<Shop> mShops = new ArrayList<>();
    ShopItemAdapter mShopItemAdapter;
    NewCar mNewCar;
    NewCarParams mNewCarParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car_detail);
        mNewCar = (NewCar) getIntent().getExtras().getSerializable("data");
        ButterKnife.bind(this);
        initView();
        getMerchat();
        getParams();
    }
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    private void getParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("carCategoryDetailId", mNewCar.getCarCategoryDetailId());
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
                            mNewCarParamsAdapter = new NewCarParamsAdapter(NewCarDetailActivity.this, paramBeans);
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

    Merchant merchant;

    private void getMerchat() {
        Map<String, Object> params = new HashMap<>();
        params.put("merchantId", mNewCar.getMerchantId());
        HttpProxy.obtain().get(PlatformContans.Shop.getMerchantById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    merchant = new Gson().fromJson(data.toString(), Merchant.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void postOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", mNewCar.getId());
        params.put("shopId", merchant.getId());
        params.put("shopName", merchant.getName());
        params.put("title", merchant.getName());
        params.put("image", merchant.getLogo());
        params.put("number", 1);
        params.put("type", 1);
        // params.put("telephone",mNewCar.getCarCategoryDetail().get);
        params.put("seat", mNewCar.getCarCategoryDetail().getSeat());
        params.put("carCategory", mNewCar.getCarCategoryDetail().getModels());
        HttpProxy.obtain().post(PlatformContans.CarOrder.addCarOrder, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        String orderId = jsonObject.getString("data");
                        Intent intent = new Intent(NewCarDetailActivity.this, CarPayActivity.class);
                        intent.putExtra("money", mNewCar.getMinPrice() + "");
                        intent.putExtra("orderid", orderId);
                        startActivity(intent);
                    }else if(code==9999){
                        ToastUtils.showLongToast(NewCarDetailActivity.this,"请先去实名认证");
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

    private void initView() {
        tv_param.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCarDetailActivity.this, NewCarParamsActivity.class);
                intent.putExtra("param", mNewCar.getCarCategoryDetailId());
                startActivity(intent);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyApplication.isLogin){
                    RongIM.getInstance().startPrivateChat(NewCarDetailActivity.this, merchant.getId(), merchant.getName());
                }
                   // postOrder();
                else{
                    startActivity(new Intent(NewCarDetailActivity.this, RegisterActivity.class));
                }
            }
        });
        rl_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              callPhone(merchant.getServiceTelephone());

            }
        });
        tv_oldprice.setText("厂家指导价" + mNewCar.getAdvicePrice());
        tv_newprice.setText("￥" + mNewCar.getNakedCarPrice());
        String name = mNewCar.getFirstName();
        if (!TextUtils.isEmpty(mNewCar.getSecondName()) && !"null".equals(mNewCar.getSecondName())) {
            name = name + mNewCar.getSecondName();
        }
        if (!TextUtils.isEmpty(mNewCar.getThirdName()) && !"null".equals(mNewCar.getThirdName())) {
            name = name + mNewCar.getThirdName();
        }
        tv_color.setText(mNewCar.getColor());
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
            token = MyApplication.token;
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
                    if (data.length() == 0) {
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
