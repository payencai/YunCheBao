package com.payencai.library.util;

import android.text.TextUtils;

public class StringUtil {
    //是否非空
    public  static  boolean isNotNull(String value){
        if(!TextUtils.isEmpty(value)||!TextUtils.equals("null",value))
            return true;
        return false;
    }
}
