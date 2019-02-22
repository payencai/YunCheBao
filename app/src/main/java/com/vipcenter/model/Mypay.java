package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/20 11:37
 * 邮箱：771548229@qq..com
 */
public class Mypay {

    /**
     * createTime : 2019-02-20T03:37:31.329Z
     * id : string
     * orderId : string
     * payMethod : string
     * price : 0
     * type : 0
     * userId : string
     */

    private String createTime;
    private String id;
    private String orderId;
    private String payMethod;
    private double price;
    private int type;
    private String userId;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
