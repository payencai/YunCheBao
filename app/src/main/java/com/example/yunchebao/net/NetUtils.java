package com.example.yunchebao.net;

import android.app.Application;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * @ProjectName: UnderPan
 * @Package: com.example.tomsonpan.underpan.utils
 * @ClassName: NetWorkUtils
 * @Description: 网络获取数据工具类
 * @Author: Tomson.pan
 * @CreateDate: 2019/4/22 16:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/22 16:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NetUtils {
    private static boolean isAsk=false;

    public static boolean isIsAsk() {
        return isAsk;
    }

    public static void setIsAsk(boolean isAsk) {
        NetUtils.isAsk = isAsk;
    }

    private static NetUtils instance;
    private HttpHeaders headers;

    public static final String HOME_BASE_URL = "http://ofof22.com";


    public static NetUtils getInstance() {
        if (instance == null) {
            instance = new NetUtils();
        }
        return instance;
    }

    public void initNetWorkUtils(Application context){

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

        //超时时间设置，默认60秒
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());
        HttpParams params = new HttpParams();
        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
        params.put("commonParamsKey2", "这里支持中文参数");

        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init(context)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers)                      //全局公共头
                .addCommonParams(params);                       //全局公共参数

    }

    public void upLoadImage(String url, File file,final OnMessageReceived messageReceived){
        OkGo.<String>post(url)//
                 .params("image",file)
                 .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e("upload", result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        messageReceived.onError("");
                    }
                });
    }
    public void upLoadVideo(String url, File file,final OnMessageReceived messageReceived){
        OkGo.<String>post(url)//
                .params("file",file)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e("upload", result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        messageReceived.onError("");
                    }
                });
    }
    public void get(final String token, final String url, HttpParams params, final OnMessageReceived messageReceived){
        if(isIsAsk()){
            return;
        }
        OkGo.<String>get(url)//
                .headers("token", token)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        isAsk=false;
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        messageReceived.onError(response.body().toString());
                        Log.e(url, "");
                        messageReceived.onError("");
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        isAsk=true;
                    }
                });
    }
    public void get(final String token,final String url, final OnMessageReceived messageReceived){
        OkGo.<String>get(url)//
                .headers("token", token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
//                        messageReceived.onError(response.body().toString());
                        Log.e(url, "");
                        messageReceived.onError("");
                    }
                });
    }
    public void get(final String url, HttpParams httpParams, final OnMessageReceived messageReceived){
        OkGo.<String>get(url)//
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(url, "");

                        messageReceived.onError("");
                    }
                });
    }
    public void post(HttpParams httpParams, final String url, String token, final OnMessageReceived messageReceived){

        OkGo.<String>post(url)//
                .headers("token", token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(url, "error");
                        messageReceived.onError("");
                    }
                });
    }
    public void post(final String url, String json, String token, final OnMessageReceived messageReceived){

        OkGo.<String>post(url)//
                .headers("token", token)
                .upString(json, MediaType.parse("application/json;charset=UTF-8"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(url, "error");
                        messageReceived.onError("");
                    }
                });
    }
    public void post(final String url, String json, final OnMessageReceived messageReceived){


        OkGo.<String>post(url)//
                .upString(json, MediaType.parse("application/json;charset=UTF-8"))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        Log.e(url, result);
                        messageReceived.onSuccess(result);
                    }
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(url, "error");
                        messageReceived.onError("");
                    }
                });
    }
    public void post(String url, HttpParams httpParams, final OnMessageReceived messageReceived){

        OkGo.<String>post(url)//
//                .params(params)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        messageReceived.onError("");
                    }
                });
    }
    public void post(String url, String token, HttpParams httpParams, final OnMessageReceived messageReceived){

        OkGo.<String>post(url)//
//                .params(params)
                .headers("token",token)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        messageReceived.onError("");
                    }
                });
    }

    public void postByToken(String url, String token, final OnMessageReceived messageReceived){

        OkGo.<String>post(url)//
//                .params(params)
                .headers("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String result = response.body().toString();
                        messageReceived.onSuccess(result);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        messageReceived.onError("");
                    }
                });
    }
}
