package com.cheyibao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarMeal implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.allPrice);
        dest.writeString(this.createTime);
        dest.writeInt(this.dayPrice);
        dest.writeInt(this.discount);
        dest.writeString(this.id);
        dest.writeString(this.longCarId);
        dest.writeInt(this.originalPrice);
        dest.writeInt(this.rentDay);
    }

    public CarMeal() {
    }

    protected CarMeal(Parcel in) {
        this.allPrice = in.readInt();
        this.createTime = in.readString();
        this.dayPrice = in.readInt();
        this.discount = in.readInt();
        this.id = in.readString();
        this.longCarId = in.readString();
        this.originalPrice = in.readInt();
        this.rentDay = in.readInt();
    }

    public static final Parcelable.Creator<CarMeal> CREATOR = new Parcelable.Creator<CarMeal>() {
        @Override
        public CarMeal createFromParcel(Parcel source) {
            return new CarMeal(source);
        }

        @Override
        public CarMeal[] newArray(int size) {
            return new CarMeal[size];
        }
    };
}
