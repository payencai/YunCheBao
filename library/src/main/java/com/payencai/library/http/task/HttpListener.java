package com.payencai.library.http.task;

/**
 * Created by payencai on 2018/5/20.
 */

public interface HttpListener {
    void onSuccess(Response response);
    void onFail(Exception e);
}
