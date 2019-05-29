package com.entity;

import java.io.Serializable;

/**
 * Created by sdhcjhss on 2017/11/27.
 */

public class PhoneGoodEntity implements Serializable{
    private String id;
    private String shopId;
    private String shopName;
    private String productId;
    private String productName;
    private double price;
    private String defaultPic;
    private String isExists;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    private String firstSpecificationId;
    private double originalPrice;
    private int flag=0;
    private double totalPrice=0;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public boolean isShopSelect() {
        return isShopSelect;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    private int count;
    private boolean isSelect = true;
    private int isFirst = 2;
    private boolean isShopSelect = true;

    public String getFirstSpecificationId() {
        return firstSpecificationId;
    }

    public void setFirstSpecificationId(String firstSpecificationId) {
        this.firstSpecificationId = firstSpecificationId;
    }

    public String getFirstSpecificationName() {
        return firstSpecificationName;
    }

    public void setFirstSpecificationName(String firstSpecificationName) {
        this.firstSpecificationName = firstSpecificationName;
    }

    public String getFirstSpecificationValue() {
        return firstSpecificationValue;
    }

    public void setFirstSpecificationValue(String firstSpecificationValue) {
        this.firstSpecificationValue = firstSpecificationValue;
    }

    public String getSecondSpecificationId() {
        return secondSpecificationId;
    }

    public void setSecondSpecificationId(String secondSpecificationId) {
        this.secondSpecificationId = secondSpecificationId;
    }

    public String getSecondSpecificationName() {
        return secondSpecificationName;
    }

    public void setSecondSpecificationName(String secondSpecificationName) {
        this.secondSpecificationName = secondSpecificationName;
    }

    public String getSecondSpecificationValue() {
        return secondSpecificationValue;
    }

    public void setSecondSpecificationValue(String secondSpecificationValue) {
        this.secondSpecificationValue = secondSpecificationValue;
    }

    private String firstSpecificationName;
    private String firstSpecificationValue;
    private String secondSpecificationId;
    private String secondSpecificationName;
    private String secondSpecificationValue;

    public String getIsExists() {
        return isExists;
    }

    public void setIsExists(String isExists) {
        this.isExists = isExists;
    }


    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    public boolean getIsShopSelect() {
        return isShopSelect;
    }

    public void setShopSelect(boolean shopSelect) {
        isShopSelect = shopSelect;
    }

    public int getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(int isFirst) {
        this.isFirst = isFirst;
    }

    public boolean getIsSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
