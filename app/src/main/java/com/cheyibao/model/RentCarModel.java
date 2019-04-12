package com.cheyibao.model;

public class RentCarModel {
    /**
     * agencyId : string
     * brand : string
     * carTategory : string
     * createTime : 2019-04-12T08:08:14.309Z
     * dayPrice : 0
     * id : string
     * image : string
     * number : 0
     * seat : string
     * shopId : string
     * state : 0
     * variableBox : string
     */

    private String agencyId;
    private String brand;
    private String carTategory;
    private String createTime;
    private int dayPrice;
    private String id;
    private String image;
    private int number;
    private String seat;
    private String shopId;
    private int state;
    private String variableBox;

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarTategory() {
        return carTategory;
    }

    public void setCarTategory(String carTategory) {
        this.carTategory = carTategory;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(int dayPrice) {
        this.dayPrice = dayPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getVariableBox() {
        return variableBox;
    }

    public void setVariableBox(String variableBox) {
        this.variableBox = variableBox;
    }
}
