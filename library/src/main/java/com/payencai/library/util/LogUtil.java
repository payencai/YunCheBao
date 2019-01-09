package com.payencai.library.util;

import android.util.Log;

/**
 * @author payencai
 * @time 2018/6/16
 * @dec Log工具类
 */
public class LogUtil {
    public static final Boolean isDug=true;
    public static void e(String tag,String value){
        if(isDug)
          Log.e(tag,value);
    }
    public static void v(String tag,String value){
        if(isDug)
            Log.v(tag,value);
    }
    public static void i(String tag,String value){
        if(isDug)
            Log.i(tag,value);
    }
    public static  void w(String tag,String value){
        if(isDug)
            Log.w(tag,value);
    }
    public static void d(String tag,String value){
        if(isDug)
            Log.w(tag,value);
    }
}
