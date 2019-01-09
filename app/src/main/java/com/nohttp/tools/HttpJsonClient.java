package com.nohttp.tools;

import android.util.Log;

import com.costans.PlatformContans;

import java.util.Collections;
import java.util.List;

/**
 * Created by yyy on 2017/5/8.
 */
public class HttpJsonClient {
    private String url;
    private static JSONUtil jsonUtil = new JSONUtil();
    private String resp;
    private String error;//错误信息，直接反馈给使用者
    private List<String> errors;//数据库错误信息，参数异常，app内部处理
    private boolean isSuccess = false;
    private boolean remarkBool = false;
//    private boolean notLogin = false;
    public void setResp(String resp) {
        this.resp = resp;
        try{
            updateMsg();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = PlatformContans.root + url;
    }

    public void updateMsg(){
        String StatusCode = getObject(String.class,"StatusCode");
        if (StatusCode != null && StatusCode.equals("200")){
            isSuccess = true;
        }else{
            isSuccess = false;
            error = getObject(String.class,"Info");
        }
        if (error != null && !error.equals("")){
            setSuccess(false);
        }
    }

    public <T> T getObject(Class<T> clazz, String name) {
        try {
            T t = jsonUtil.parseObj(clazz, resp, name);
            return t;
        } catch (Exception e) {
            Log.w("json", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> getList(Class<T> clazz, String name) {
        try {
            List<T> list = jsonUtil.parseArray(clazz, resp, name);
            return list;
        } catch (Exception e) {
            Log.w("json", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public boolean isRemarkBool() {
        return remarkBool;
    }

    public String getError() {
        return error;
    }

}
