package com.example.yunchebao.maket.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bbcircle.view.NoScrollWebView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.adapter.ParamsInfoAdapter;
import com.example.yunchebao.maket.model.GoodDetail;
import com.example.yunchebao.maket.model.GoodInfoParams;
import com.nohttp.sample.BaseFragment;
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

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodDetailInfoFragment extends BaseFragment {
    private Context ctx;
    ParamsInfoAdapter mParamsInfoAdapter;
    List<GoodInfoParams> mGoodInfoParams=new ArrayList<>();
    @BindView(R.id.lv_params)
    PersonalListView lv_params;
    @BindView(R.id.webView)
    NoScrollWebView mWebView;
    GoodDetail mGoodDetail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.market_good_detail_info, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }
    private void initWebview() {
        WebSettings settings = mWebView.getSettings();
        mWebView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);  //支持js
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setBlockNetworkImage(false);
        } else {
            settings.setBlockNetworkImage(true);//图片最后加载，
        }
        mWebView.setWebChromeClient(new WebChromeClient() {

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }
    private void init() {
        ctx = getActivity();
        mParamsInfoAdapter=new ParamsInfoAdapter(getContext(),mGoodInfoParams);
        lv_params.setAdapter(mParamsInfoAdapter);
        initWebview();
        getDetail();
    }
    public void getDetail() {

        GoodDetailActivity goodDetailActivity = (GoodDetailActivity) getActivity();
        Map<String, Object> params = new HashMap<>();
        params.put("babyMerchantCommodityId", goodDetailActivity.getGoodList().getId());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getGoodDetail, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mGoodDetail = new Gson().fromJson(data.toString(), GoodDetail.class);
                    mWebView.loadUrl(mGoodDetail.getCommodityDetail());
                    getParams();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getParams(){
        GoodDetailActivity activity= (GoodDetailActivity) getActivity();
        Map<String,Object> params=new HashMap<>();
        params.put("babyMerchantCommodityId",activity.getGoodList().getId());
        HttpProxy.obtain().get(PlatformContans.GoodInfo.getGoodParams, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGoodParams", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodInfoParams baikeItem = new Gson().fromJson(item.toString(), GoodInfoParams.class);
                        mGoodInfoParams.add(baikeItem);
                    }
                    mParamsInfoAdapter.notifyDataSetChanged();
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
