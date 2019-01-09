package com.http.processor;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.http.ICallBack;
import com.http.IHttpProcessor;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/9/29.
 */

public class VolleyProcessor implements IHttpProcessor {

    private RequestQueue mQueue;

    public VolleyProcessor(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public enum VolleyRequestType {
        String, JsonObjectRequest, ImageRequest, ImageLoader
    }

    @Override
    public void post(String url, Map<String, Object> bodyParams, ICallBack callBack) {
//        this.post(url, null, bodyParams, callBack);
    }

    @Override
    public void post(String url, Map<String, Object> headParams, Map<String, Object> bodyParams, final ICallBack callBack) {
        final Map<String, Object> headMap = headParams;
        final Map<String, Object> bodyMap = bodyParams;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callBack.OnSuccess(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headMap != null) {
                    return buildMap(headMap);
                }
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (bodyMap != null) {
                    return buildMap(bodyMap);
                }
                return super.getParams();
            }
        };
        mQueue.add(stringRequest);
    }

    @Override
    public void post(String url, String tokenMap, Map<String, Object> bodyParams, ICallBack callBack) {

    }

    @Override
    public void post(String url, String tokenMap, String jsonString, ICallBack callBack) {

    }

    @Override
    public void get(String url, Map<String, Object> params, ICallBack callBack) {

    }

    @Override
    public void get(String url, Map<String, Object> headParams, Map<String, Object> tokenMap, ICallBack callBack) {

    }

    @Override
    public void get(String url, String tokenValue, String jsonString, ICallBack callBack) {

    }

    @Override
    public void get(String url, Map<String, Object> headParams, String tokenMap, ICallBack callBack) {

    }

    @Override
    public void get(String url, String token, ICallBack callBack) {

    }

    private Map<String, String> buildMap(Map<String, Object> sourceMap) {
        Map<String, String> hasMap = new HashMap<>();
        for (String key : sourceMap.keySet()) {
            hasMap.put(key, (String) sourceMap.get(key));
        }
        return hasMap;
    }
}
