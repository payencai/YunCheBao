package com.http;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/29.
 */

public interface IHttpProcessor {
    void post(String url, Map<String, Object> bodyParams, ICallBack callBack);

    void post(String url, Map<String, Object> headParams, Map<String, Object> bodyParams, ICallBack callBack);

    void post(String url, String tokenMap, Map<String, Object> bodyParams, ICallBack callBack);

    void post(String url, String tokenMap, String jsonString, ICallBack callBack);

    void get(String url, Map<String, Object> headParams, ICallBack callBack);

    void get(String url, Map<String, Object> headParams, Map<String, Object> tokenMap, ICallBack callBack);

    void get(String url, String tokenValue, String jsonString, ICallBack callBack);

    void get(String url, Map<String, Object> headParams, String tokenMap, ICallBack callBack);

    void get(String url, String token, ICallBack callBack);
}
