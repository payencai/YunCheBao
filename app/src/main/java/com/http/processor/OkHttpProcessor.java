package com.http.processor;

import android.os.Handler;


import com.http.ICallBack;
import com.http.IHttpProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by LT on 2017/12/12.
 */

public class OkHttpProcessor implements IHttpProcessor {

    public Handler mHandler = new Handler();
    //{"resultCode":6666,"message":"登录信息已过期,请重新登录"}

    @Override
    public void post(String url, Map<String, Object> bodyParams, final ICallBack callBack) {

        OkHttpClient client = new OkHttpClient();
        FormBody body = buildBody(bodyParams);
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(result);
                    }
                });
            }
        });

    }

    @Override
    public void post(String url, Map<String, Object> headParams, Map<String, Object> bodyParams, final ICallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        FormBody body = buildBody(bodyParams);
        Request request = addHeaders(headParams).post(body).url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(result);
                    }
                });
            }
        });
    }

    @Override
    public void post(String url, String token, Map<String, Object> bodyParams, ICallBack callBack) {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        this.post(url, tokenMap, bodyParams, callBack);
    }

    @Override
    public void post(String url, String tokenvalue, String jsonString, final ICallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        MediaType jsonType = MediaType.parse("application/json;charset=UTF-8");
        RequestBody body = RequestBody.create(jsonType, jsonString);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenvalue);
        Request request = addHeaders(tokenMap).url(url).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(result);
                    }
                });
            }
        });
    }


    @Override
    public void get(String url, Map<String, Object> headParams, final ICallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = addHeaders(headParams).url(url).build();
        request.url();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String request = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(request);
                    }
                });
            }
        });

    }

    @Override
    public void get(String url, Map<String, Object> headParams, Map<String, Object> tokenMap, final ICallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = addHeaders(tokenMap).url(url).build();
        request.url();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String request = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(request);
                    }
                });
            }
        });
    }

    @Override
    public void get(String url, String tokenValue, String jsonString, final ICallBack callBack) {

        MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(jsonType, jsonString);
        OkHttpClient okHttpClient = new OkHttpClient();
        Map<String, Object> map = new HashMap<>();
        map.put("token", tokenValue);
        Request request = addHeaders(map).url(url).build();
        request.url();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String request = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(request);
                    }
                });
            }
        });

    }

    @Override
    public void get(String url, Map<String, Object> headParams, String token, ICallBack callBack) {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        this.get(url, headParams, tokenMap, callBack);
    }

    @Override
    public void get(String url, String token, final ICallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = addTokenHeader(token).url(url).build();
        request.url();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFailure(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String request = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.OnSuccess(request);
                    }
                });
            }
        });

    }

    private FormBody buildBody(Map<String, Object> bodyMap) {
        FormBody.Builder builder = new FormBody.Builder();
        if (bodyMap == null) {
            return builder.build();
        }

        for (String key : bodyMap.keySet()) {
            builder.add(key, bodyMap.get(key) + "");
        }
        return builder.build();
    }

    private Map<String, String> buildMap(Map<String, Object> sourceMap) {
        Map<String, String> hasMap = new HashMap<>();
        for (String key : sourceMap.keySet()) {
            hasMap.put(key, sourceMap.get(key) + "");
        }
        return hasMap;
    }

    private String appendParamss(String url, Map<String, Object> headMap) {
        StringBuffer requst = new StringBuffer();
        requst.append(url);
        if (headMap.size() == 0 || headMap == null) {
            return requst.toString();
        }
        requst.append("?");
        int size = headMap.size();
        int count = 0;
        for (String key : headMap.keySet()) {
            count++;
            requst.append(key);
            requst.append("=" + headMap.get(key));
            if (count != size) {
                requst.append("&");
            }
        }
        String newUrl = requst.toString();
        return newUrl;
    }

    private Request.Builder addHeaders(Map<String, Object> headMap) {
        Request.Builder builder = new Request.Builder();
        if (headMap == null) {
            return builder;
        }
        for (String key : headMap.keySet()) {
            builder.addHeader(key, headMap.get(key) + "");
        }
        return builder;
    }
    private Request.Builder addTokenHeader(String token) {
        Request.Builder builder = new Request.Builder();
        if (token == null) {
            return builder;
        }
        builder.addHeader("token",token);
        return builder;
    }
}
