package com.cheyibao.model;

public class CarMeal {
    /**
     * allPrice : 0
     * createTime : 2019-04-23T09:30:00.080Z
     * dayPrice : 0
     * discount : 0
     * id : string
     * longCarId : string
     * originalPrice : 0
     * rentDay : 0
     */

    private int allPrice;
    private String createTime;
    private int dayPrice;
    private int discount;
    private String id;
    private String longCarId;
    private int originalPrice;
    private int rentDay;

    public int getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(int allPrice) {
        this.allPrice = allPrice;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongCarId() {
        return longCarId;
    }

    public void setLongCarId(String longCarId) {
        this.longCarId = longCarId;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getRentDay() {
        return rentDay;
    }

    public void setRentDay(int rentDay) {
        this.rentDay = rentDay;
    }
}
