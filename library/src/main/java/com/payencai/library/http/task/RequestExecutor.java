package com.payencai.library.http.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by payencai on 2018/5/20.
 */

public enum  RequestExecutor  {
    INSTANCE;
    private final ExecutorService mExecutorService;

    RequestExecutor(){
         mExecutorService=Executors.newSingleThreadExecutor();
    }
    public void execute(Request request,HttpListener httpListener){
            mExecutorService.execute(new RequestTask(request,httpListener));
    }
}
