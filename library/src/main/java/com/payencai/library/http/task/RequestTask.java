package com.payencai.library.http.task;


import android.util.Log;

import com.payencai.library.util.LogUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by payencai on 2018/5/20.
 */

public class RequestTask implements Runnable {
    private Response mResponse=null;
    private Request mRequest;
    private HttpListener httpListener;
    public Request getRequest() {
        return mRequest;
    }

    public RequestTask(Request request,HttpListener httpListener) {
        this.mRequest = request;
        this.httpListener=httpListener;
    }

    public void setRequest(Request request) {
        this.mRequest = request;
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    private static String appendParams(String url, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return url;
        }
        List<String> keys = new ArrayList<String>(params.keySet());
        StringBuffer result = new StringBuffer();
        result.append(url + "?");
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = params.get(key) + "";
            result.append(buildKeyValue(key, value, false));
            result.append("&");
        }
        String tailKey = keys.get(keys.size() - 1);
        String tailValue = params.get(tailKey) + "";
        result.append(buildKeyValue(tailKey, tailValue, true));
        return result.toString();
    }
    private void doGet(String url,Object header,HashMap<String,Object> params){
        OkHttpClient okHttpClient=new OkHttpClient();
        String newUrl=appendParams(url,params);
        final okhttp3.Request request=addHeaders(header).url(newUrl).get().build();
        Call newCall=okHttpClient.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Map<String,List<String>> resHeader=null;
                mResponse=new Response();
                mResponse.setRequest(mRequest);
                mResponse.setResException(e);
                mResponse.setRespoonseHeaders(resHeader);
                mResponse.setResCode("1");
                mResponse.setResData("没有数据");
                Message message=new Message(mResponse,httpListener);
                PosterHandler.getInstance().post(message);

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String data=response.body().string();
                Exception exception=null;
                Map<String,List<String>> resHeader=null;
                mResponse=new Response();
                mResponse.setResData(data);
                mResponse.setRequest(mRequest);
                mResponse.setResCode("0");
                mResponse.setResException(exception);
                mResponse.setRespoonseHeaders(resHeader);
                Message message=new Message(mResponse,httpListener);
                PosterHandler.getInstance().post(message);

            }
        });
    }
    private okhttp3.Request.Builder addHeaders(Object header){
        okhttp3.Request.Builder  builder=new okhttp3.Request.Builder();
        if(header==null)
            return builder;
        if(header instanceof  String)
             builder.addHeader(header.toString(),header.toString());
        else if(header instanceof StringMap){
            HashMap<String,Object> headers=(HashMap<String,Object>)header;
            for (String key:headers.keySet()) {
                builder.addHeader(key,headers.get(key).toString());
            }
        }
        return  builder;
    }
    public class StringMap extends HashMap<String,Object>{

    }
    @Override
    public void run() {
        String method=mRequest.getReqMethod();
        if(method==RequestMethod.GET) {
             doGet(mRequest.getReqUrl(),mRequest.getHeader(),listToMap(mRequest.getKeyValues()));
        }else if(method==RequestMethod.POST){

        }

    }
    private HashMap<String,Object> listToMap(List<KeyValue> keyValues){
        HashMap<String,Object> params=new HashMap<>();
        for(KeyValue keyValue:keyValues){
            params.put(keyValue.getKey(),keyValue.getValue());
        }
        return params;
    }
}
