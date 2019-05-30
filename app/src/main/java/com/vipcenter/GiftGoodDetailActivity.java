package com.vipcenter;

import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bbcircle.view.NoScrollWebView;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;

import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;


import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.listview.PersonalListView;

import com.vipcenter.adapter.GiftParamsItemAdapter;
import com.vipcenter.model.Gift;
import com.vipcenter.model.GiftParams;
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
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/16.
 */

public class GiftGoodDetailActivity extends NoHttpBaseActivity {
    //轮播图片

    @BindView(R.id.slideshowView)
    Banner banner;

    @BindView(R.id.menuTabText1)
    TextView tabText1;
    @BindView(R.id.menuTabText2)
    TextView tabText2;
    @BindView(R.id.menuTabLine1)
    View tabLine1;
    @BindView(R.id.menuTabLine2)
    View tabLine2;
    @BindView(R.id.lv_params)
    PersonalListView lv_params;
    @BindView(R.id.webView)
    NoScrollWebView webView;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.price)
    TextView tv_price;
    @BindView(R.id.tv_total)
    TextView tv_total;
    GiftParamsItemAdapter mGiftParamsItemAdapter;
    List<GiftParams> mGiftParams = new ArrayList<>();
    Gift mGift;
    private Context ctx;
    List<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_good_detail_layout);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }
        //UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "商品详情页");
        initView();

    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(GiftGoodDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }

    private void initWebview() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebChromeClient(new WebChromeClient() {

        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(mGift.getCommodityDetail());

    }
    private void initView() {


        ctx = this;
        mGift = (Gift) getIntent().getSerializableExtra("data");
        if(mGift!=null){
            tv_name.setText(mGift.getCommodityName()+"");
            tv_price.setText(mGift.getCoinCount()+"");
            //网络地址获取轮播图
            String[] imgs = mGift.getCommodityImage().split(",");
            for (int i = 0; i < imgs.length; i++) {
                images.add(imgs[i]);
            }
            mGiftParamsItemAdapter=new GiftParamsItemAdapter(GiftGoodDetailActivity.this,mGiftParams);
            lv_params.setAdapter(mGiftParamsItemAdapter);
            tv_total.setText("合计:"+mGift.getCoinCount()+"宝币");
            getDetail();
        }

    }
    private void getDetail(){
        Map<String,Object> params=new HashMap<>();
        params.put("id",mGift.getId());
        HttpProxy.obtain().post(PlatformContans.Gift.getGiftCommodity, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                    JSONArray array=data.getJSONArray("params");
                    for (int i = 0; i <array.length() ; i++) {
                        JSONObject item=array.getJSONObject(i);
                        GiftParams giftParams=new Gson().fromJson(item.toString(),GiftParams.class);
                        mGiftParams.add(giftParams);
                    }
                    mGiftParamsItemAdapter.notifyDataSetChanged();
                    initBanner();
                    initWebview();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    @OnClick({R.id.back, R.id.menuTabText1, R.id.menuTabText2, R.id.submitBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menuTabText1:
                findViewById(R.id.view1).setVisibility(View.VISIBLE);
                findViewById(R.id.view2).setVisibility(View.GONE);
                tabText1.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                tabText2.setTextColor(ContextCompat.getColor(ctx, R.color.gray_99));
                tabLine1.setVisibility(View.VISIBLE);
                tabLine2.setVisibility(View.GONE);
                break;
            case R.id.menuTabText2:
                findViewById(R.id.view2).setVisibility(View.VISIBLE);
                findViewById(R.id.view1).setVisibility(View.GONE);
                tabText2.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                tabText1.setTextColor(ContextCompat.getColor(ctx, R.color.gray_99));
                tabLine2.setVisibility(View.VISIBLE);
                tabLine1.setVisibility(View.GONE);
                break;
            case R.id.submitBtn:
                Bundle bundle=new Bundle();
                bundle.putSerializable("gift",mGift);
                ActivityAnimationUtils.commonTransition(GiftGoodDetailActivity.this, GiftOrderConfirmActivity.class, ActivityConstans.Animation.FADE,bundle);
                break;
        }
    }
}
