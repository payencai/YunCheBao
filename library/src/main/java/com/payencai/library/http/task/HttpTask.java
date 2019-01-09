package com.payencai.library.http.task;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class HttpTask implements IHttpMethod {
    private HttpTask(){

    }
    private static HttpTask instance;
    public static HttpTask getInstance(){
        if(instance==null){
             synchronized (HttpTask.class){
                 if(instance==null){
                     instance=new HttpTask();
                 }
             }
        }
        return  instance;
    }


    @Override
    public void doget(String url, Object header, List<KeyValue> keyValues, final ICallBack iCallBack) {
        final Request request=new Request(url, RequestMethod.GET,header,keyValues);
        RequestExecutor.INSTANCE.execute(request, new HttpListener() {
            @Override
            public void onSuccess(Response response) {
                String data=(String)response.getResData();
                iCallBack.success(data);

            }

            @Override
            public void onFail(Exception e) {
                iCallBack.failed(e);
            }
        });
    }
}
