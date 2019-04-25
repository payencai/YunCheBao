package com.cheyibao.model;

public class LongRentShop extends RentShop {
    private String carId;
    private String dayPrice;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(String dayPrice) {
        this.dayPrice = dayPrice;
    }
}
