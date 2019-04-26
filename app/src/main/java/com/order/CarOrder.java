package com.order;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/28 14:00
 * 邮箱：771548229@qq..com
 */
public class CarOrder implements Serializable{


    /**
     * createTime : 2019-04-24T07:53:32.603Z
     * id : string
     * logo : string
     * shopId : string
     * shopName : string
     * state : 0
     * total : 0.0
     * type : 0
     * userId : string
     */

    private String createTime;
    private String id;
    private String logo;
    private String shopId;
    private String shopName;
    private int state;
    private double total;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
