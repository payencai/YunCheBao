package com.payencai.library.http.retrofitAndrxjava;



import android.util.Log;

import com.google.gson.Gson;
import com.payencai.library.http.retrofitAndrxjava.converter.GsonConverterFactory;
import com.payencai.library.http.retrofitAndrxjava.request.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by Zaifeng on 2018/2/28.
 * API初始化类
 */

public class NetWorkManager {

    private static NetWorkManager mInstance;
    private static Retrofit retrofit;
    private static Retrofit mRetrofit;

    public static Class<?> analysusClazInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
    public static  NetWorkManager getInstance() {

        if (mInstance == null) {
            synchronized (NetWorkManager.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init(String base) {
        //Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(base)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public static <T>  T getRequest(Class <T> cls) {
        T t=null;
        if (t== null) {
            synchronized (cls) {
                t = (T)retrofit.create(cls);
            }
        }
        return t;
    }

    public static <T>  T getReq(Class <T> cls) {
        T t=null;
        if (t== null) {
            synchronized (cls) {
                t = (T)mRetrofit.create(cls);
            }
        }
        return t;
    }
    public void initUrl(String base) {
        //Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        // 初始化Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(base)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }
}