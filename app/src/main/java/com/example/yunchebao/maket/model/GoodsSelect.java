package com.example.yunchebao.maket.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/12 15:54
 * 邮箱：771548229@qq..com
 */
public class GoodsSelect implements Serializable{

    /**
     * address : string
     * name : string
     * remark : string
     * shopId : string
     * shopName : string
     * shoppingCarIds : string
     * telephone : string
     */

    private String address;
    private String name;
    private String remark;
    private String shopId;
    private String shopName;
    private String shoppingCarIds;
    private String telephone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getShoppingCarIds() {
        return shoppingCarIds;
    }

    public void setShoppingCarIds(String shoppingCarIds) {
        this.shoppingCarIds = shoppingCarIds;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
