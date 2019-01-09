package com.payencai.library.http.task;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/9/29.
 */

public abstract class HttpCallback<T> implements ICallBack {
    public abstract void onSuccess(T result);
    public abstract void onError(Exception error);

    public static Class<?> analysusClazInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    @Override
    public void success(String result) {
        Gson gson = new Gson();
        //String data="{\"cip\": \"61.144.146.41\", \"cid\": \"440106\", \"cname\": \"广东省广州市天河区\"}";
        Class<?> cls=analysusClazInfo(this);
        //Log.e("c",c.getName());
        //Log.e("c",result);
        T objectResult = (T) gson.fromJson(result,cls);
        onSuccess(objectResult);
    }

    @Override
    public void failed(Exception error) {
        onError(error);
    }
    //    @Override
//    public void onFailure(String error) {
//        onFailure2(error);
//    }
}
