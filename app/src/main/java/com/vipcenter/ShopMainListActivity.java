package com.vipcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.fragment.GoodsTypeFragment;
import com.maket.model.GoodsShop;
import com.nohttp.sample.NoHttpFragmentBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.xihubao.ShopInfoActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * Created by sdhcjhss on 2018/1/4.
 * 店铺主页
 */

public class ShopMainListActivity extends NoHttpFragmentBaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.shopIcon)
    ImageView shopIcon;
    @BindView(R.id.shopName)
    TextView shopName;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.iv_heart)
    ImageView iv_heart;
    String[] mTitles = {"全部", "热销", "新品"};
    ArrayList<Fragment> mFragments = new ArrayList<>();
    public static String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_home_indicator_vp_list);
        ButterKnife.bind(this);
        id = getIntent().getExtras().getString("id");
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "店铺");
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        findViewById(R.id.shopCartBtn).setVisibility(View.GONE);
        for (int i = 0; i < 3; i++) {
            //int j=i++;
            mFragments.add(GoodsTypeFragment.newInstance(i));
        }
        getShop();
        isCollect();
        tab_layout.setViewPager(viewPager, mTitles, this, mFragments);

    }

    GoodsShop mGoodsShop;

    private void getShop() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getBabyMerchantShopResultById, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mGoodsShop = new Gson().fromJson(data.toString(), GoodsShop.class);
                    shopName.setText(mGoodsShop.getShopName());
                    tv_grade.setText("" + mGoodsShop.getGrade());
                    Glide.with(ShopMainListActivity.this).load(mGoodsShop.getLogo()).into(shopIcon);
                    // tv_phone.setText(mRentCar.getServiceTelephone());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    int collect = 0;

    private void isCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", MyApplication.getUserInfo().getId());
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.GoodCollect.getBabyMerchantCollection, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    collect = jsonObject.getInt("data");
                    if (collect == 1) {
                        iv_heart.setImageResource(R.mipmap.orange_heart_icon);
                    }
                    // tv_phone.setText(mRentCar.getServiceTelephone());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void addCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().post(PlatformContans.GoodCollect.addBabyMerchantCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                collect = 1;
                iv_heart.setImageResource(R.mipmap.orange_heart_icon);
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void delCollect() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        HttpProxy.obtain().post(PlatformContans.GoodCollect.deleteCommodityCollection, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {


                collect = 0;
                iv_heart.setImageResource(R.mipmap.collect_66);
                // tv_phone.setText(mRentCar.getServiceTelephone());

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @OnClick({R.id.back, R.id.customerServiceBtn, R.id.collectBtn, R.id.ll_head})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head:
                Intent intent = new Intent(ShopMainListActivity.this, ShopInfoActivity.class);
                intent.putExtra("id", mGoodsShop.getId());
                startActivity(intent);
                break;
            case R.id.back:
                onBackPressed();
                break;
            case R.id.collectBtn:
                if(collect==1){
                    delCollect();
                }else{
                    addCollect();
                }
                break;
            case R.id.customerServiceBtn:
                RongIM.getInstance().startPrivateChat(ShopMainListActivity.this,mGoodsShop.getId(),mGoodsShop.getShopName());
                //ActivityAnimationUtils.commonTransition(ShopMainListActivity.this, OrderChatDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
