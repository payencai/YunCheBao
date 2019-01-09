package com.payencai.library.http.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by payencai on 2018/5/20.
 */

public class Request {
    private Object header;

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    private String reqUrl;
    @RequestMethod.HttpMethod private String reqMethod;
    private List<KeyValue> keyValues;

    public Request(String url) {
        //this(url,RequestMethod.GET,header);
    }

    public Request(String url, @RequestMethod.HttpMethod String method, Object header, List<KeyValue> keyValues) {
        this.reqUrl = url;
        this.reqMethod = method;
        this.keyValues=keyValues;
        this.header=header;
    }
    public void add(String key,String value){
        keyValues.add(new KeyValue(key,value));
    }
    public void add(String key,File value){
        keyValues.add(new KeyValue(key,value));
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public @RequestMethod.HttpMethod String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(@RequestMethod.HttpMethod String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }
}
