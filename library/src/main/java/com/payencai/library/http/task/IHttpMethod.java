package com.payencai.library.http.task;

import java.util.HashMap;
import java.util.List;

public interface IHttpMethod {
    public void doget(String url, Object header,List<KeyValue> keyValues,ICallBack iCallBack);
}
