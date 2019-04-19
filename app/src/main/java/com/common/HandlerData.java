package com.common;

import android.util.Log;

import com.cheyibao.model.RentShop;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class HandlerData {
    public static <T> void handlerData(String result, Type typeOfT,EndLoadDataType<T> endLoadDataType){
        Log.e("getdata", result);
        if (result.startsWith("<!DOCTYPE html>")) {
            endLoadDataType.onFailed();
            return;
        }
        BaseModel<T> baseModel = new Gson().fromJson(result, typeOfT);
        if (baseModel==null || baseModel.data==null ){
            endLoadDataType.onSuccess(null);
            endLoadDataType.onSuccessBaseModel(baseModel);
        }else {
            T t = baseModel.getData();
            if (t instanceof List && ((List)t).size()<=0){
                endLoadDataType.onSuccess(null);
                endLoadDataType.onSuccessBaseModel(baseModel);
            }else {
                endLoadDataType.onSuccess(baseModel.getData());
            }
        }
    }
}
