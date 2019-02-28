package com.system;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yunchebao.R;
import com.vipcenter.RegisterActivity;

public class WebviewActivity extends AppCompatActivity {
    String url;
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra("url");
        Log.e("url",url);
        mWebView = (WebView) findViewById(R.id.webView);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.addJavascriptInterface(new Pay(),"No");
        initWebview();
    }
    public class Pay {

        // 有返回结果
        @JavascriptInterface
        public void  Login(String paramFromJS) {
            startActivity(new Intent(WebviewActivity.this, RegisterActivity.class));
        }

        // 返回结果
        @JavascriptInterface
        public void back() {
            finish();
        }
    }
    private void initWebview() {
        WebSettings settings = mWebView.getSettings();
        //mWebView.requestFocusFromTouch();
        settings.setJavaScriptEnabled(true);  //支持js
//        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);  //提高渲染的优先级
//        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
//        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。

        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setDomStorageEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.supportMultipleWindows();  //多窗口
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
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
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();//后退
        } else {
            finish();
        }
    }
}
