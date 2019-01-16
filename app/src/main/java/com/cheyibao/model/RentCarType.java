package com.cheyibao.model;

import java.io.Serializable;

public class RentCarType implements Serializable{

    /**
     * brand : string
     * carNo : string
     * createTime : 2019-01-08T16:23:09.618Z
     * dayPrice : 0
     * id : string
     * isDel : 0
     * manualAutomatic : string
     * merchantId : string
     * model : string
     * photo : string
     * seat : string
     * state : 0
     */

    private String brand;
    private String carNo;
    private String createTime;
    private double dayPrice;
    private String id;
    private int isDel;
    private String manualAutomatic;
    private String merchantId;
    private String model;
    private String photo;
    private String seat;
    private int state;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(double dayPrice) {
        this.dayPrice = dayPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getManualAutomatic() {
        return manualAutomatic;
    }

    public void setManualAutomatic(String manualAutomatic) {
        this.manualAutomatic = manualAutomatic;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
