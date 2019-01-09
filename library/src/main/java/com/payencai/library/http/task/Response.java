package com.payencai.library.http.task;

import java.util.List;
import java.util.Map;

/**
 * Created by payencai on 2018/5/20.
 */

public class Response {
    private Request request;
    private Map<String,List<String>> resHeaders;
    private String resCode;
    private Object resData;

    public Map<String, List<String>> getRespoonseHeaders() {
        return resHeaders;
    }

    public void setRespoonseHeaders(Map<String, List<String>> respoonseHeaders) {
        this.resHeaders = respoonseHeaders;
    }

    private Exception resException;
    public Response() {

    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    public Exception getResException() {
        return resException;
    }

    public void setResException(Exception resException) {
        this.resException = resException;
    }
}
