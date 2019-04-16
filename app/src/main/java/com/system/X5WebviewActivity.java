package com.system;

import android.content.Intent;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



import com.example.yunchebao.R;


import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.model.AddressBean;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tool.X5WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class X5WebviewActivity extends AppCompatActivity {

    X5WebView mX5WebView;
   private void getCity(double latitude,double longitude){
       String url="https://restapi.amap.com/v3/geocode/regeo?output=json&location="+longitude+","+latitude+"&key=bbe8f89a8956d407bc8dec2b26a18ab9&extensions=all&batch=false";
       //String url="https://restapi.amap.com/v3/geocode/regeo?output=json&location=113.39679,23.04551&key=b3e064ba536662c892cd0c81169b5bfb&extensions=all&batch=false";
       HttpProxy.obtain().get(url, "", new ICallBack() {
           @Override
           public void OnSuccess(String result) {
               try {
                   JSONObject jsonObject=new JSONObject(result);
                   JSONObject regeocode=jsonObject.getJSONObject("regeocode");
                   JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
                   String province=addressComponent.getString("province");
                   String district=addressComponent.getString("district");
                   addressBean.setDistrict(district);
                   addressBean.setProvince(province);
                   //Log.e("gaode",result);
                   Intent intent = new Intent();
                   intent.putExtra("address", addressBean);
                   setResult(1, intent);
                   finish();

               } catch (JSONException e) {
                   e.printStackTrace();
               }

           }

           @Override
           public void onFailure(String error) {

           }
       });
    }
    AddressBean addressBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5_webview);

        mX5WebView = (X5WebView) findViewById(R.id.webview);

        mX5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http://www.test.com/?loc=")) {
                    String[] split = url.split("loc=");
                    String s = split[1];
                    String strUTF8 = null;
                    try {
                        strUTF8 = URLDecoder.decode(s, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e("address", strUTF8);
                    try {
                         JSONObject jsonObject=new JSONObject(strUTF8);
                         addressBean=new Gson().fromJson(jsonObject.toString(),AddressBean.class);
                         getCity(addressBean.getLatlng().getLat(),addressBean.getLatlng().getLng());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        mX5WebView.setWebChromeClient(new WebChromeClient() {

        });
        mX5WebView.loadUrl("http://120.79.176.228:8080/gaote-web/map/index.html");
        //mX5WebView.loadUrl("https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp");

    }
}
