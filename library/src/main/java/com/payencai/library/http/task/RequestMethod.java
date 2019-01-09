package com.payencai.library.http.task;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by payencai on 2018/5/20.
 */
public class RequestMethod {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String HEAD = "HEAD";
    public static final String DELETE = "DELETE";
    private String value;

    RequestMethod(@HttpMethod String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
    /**
     *  作用范围为编译期的注解，限定输入HTTP请求方法为以上4种方法
     *  取代枚举的方式
     */

    @StringDef({GET, POST, HEAD, DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HttpMethod {

    }
}
