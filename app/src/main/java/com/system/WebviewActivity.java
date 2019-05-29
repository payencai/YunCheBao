package com.system;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;


import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.payencai.library.util.ToastUtil;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tool.X5WebView;
import com.vipcenter.RegisterActivity;

public class WebviewActivity extends AppCompatActivity {
    String url;
    X5WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra("url");
        Log.e("url",url);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        mWebView = (X5WebView) findViewById(R.id.webView);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mWebView.addJavascriptInterface(new Pay(),"Go");
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
        @JavascriptInterface
        public void noLogin() {
            ToastUtil.showToast(WebviewActivity.this,"请先去登录");
        }
    }
    private void initWebview() {

        mWebView.setWebChromeClient(new WebChromeClient() {

        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel();// Android默认的处理方式，WebView变成空白页
                handler.proceed();  // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
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
