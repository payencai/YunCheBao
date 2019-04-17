package com.common;

public abstract class EndLoadDataType<T> {
    public void onNoNetwork(){}
    public abstract void onFailed();
    public abstract void onSuccess(T t);
}
