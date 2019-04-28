package com.cheyibao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RentCarModel implements Parcelable {
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
    private double dayPrice;
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

    public RentCarModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.agencyId);
        dest.writeString(this.brand);
        dest.writeString(this.carTategory);
        dest.writeString(this.createTime);
        dest.writeDouble(this.dayPrice);
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeInt(this.number);
        dest.writeString(this.seat);
        dest.writeString(this.shopId);
        dest.writeInt(this.state);
        dest.writeString(this.variableBox);
    }

    protected RentCarModel(Parcel in) {
        this.agencyId = in.readString();
        this.brand = in.readString();
        this.carTategory = in.readString();
        this.createTime = in.readString();
        this.dayPrice = in.readDouble();
        this.id = in.readString();
        this.image = in.readString();
        this.number = in.readInt();
        this.seat = in.readString();
        this.shopId = in.readString();
        this.state = in.readInt();
        this.variableBox = in.readString();
    }

    public static final Creator<RentCarModel> CREATOR = new Creator<RentCarModel>() {
        @Override
        public RentCarModel createFromParcel(Parcel source) {
            return new RentCarModel(source);
        }

        @Override
        public RentCarModel[] newArray(int size) {
            return new RentCarModel[size];
        }
    };
}
