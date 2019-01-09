package com.payencai.library.http.task;

import java.util.concurrent.RunnableFuture;

/**
 * Created by payencai on 2018/5/20.
 */

public class Message implements Runnable{
    private Response response;
    private HttpListener httpListener;
    public Message(Response response,HttpListener httpListener) {
        this.response = response;
        this.httpListener=httpListener;
    }

    @Override
    public void run() {
        Exception e=response.getResException();
        if(e!=null)
            httpListener.onFail(e);
        else
            httpListener.onSuccess(response);
    }
}
