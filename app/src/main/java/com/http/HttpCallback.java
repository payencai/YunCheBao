package com.http;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/9/29.
 */

public abstract class HttpCallback<Result> implements ICallBack {
    @Override
    public void OnSuccess(String result) {
        Gson gson = new Gson();
        Class<?> cls = analysusClazInfo(this);
        Result objectResult = (Result) gson.fromJson(result, cls);
        onSuccess(objectResult);
    }

    public abstract void onSuccess(Result result);
    public abstract void onFailure2(String error);

    public static Class<?> analysusClazInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

    @Override
    public void onFailure(String error) {
        onFailure2(error);
    }
}
