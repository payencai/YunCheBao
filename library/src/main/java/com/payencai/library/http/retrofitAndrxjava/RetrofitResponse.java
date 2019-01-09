package com.payencai.library.http.retrofitAndrxjava;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RetrofitResponse<T> implements Serializable{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    private String message;
    private int resultCode;
}
