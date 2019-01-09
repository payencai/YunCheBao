package com.payencai.library.http.task;

public interface ICallBack {
    public void success(String result);
    public void failed(Exception error);
}
