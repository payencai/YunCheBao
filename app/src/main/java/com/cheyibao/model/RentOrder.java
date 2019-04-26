package com.cheyibao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RentOrder implements Parcelable {
    /**
     * agencyId : string
     * brand : string
     * callName : string
     * callTelephone : string
     * carTategory : string
     * createTime : 2019-04-25T01:12:02.940Z
     * dayPrice : 0
     * handleTime : 2019-04-25T01:12:02.940Z
     * id : string
     * idNumber : string
     * image : string
     * isComment : 0
     * isReturn : 0
     * isTake : 0
     * name : string
     * payMethod : 0
     * payTime : 2019-04-25T01:12:02.940Z
     * remark : string
     * rentDay : 0
     * returnCarAddress : string
     * returnCarLatitude : string
     * returnCarLongitude : string
     * returnCarTime : 2019-04-25T01:12:02.940Z
     * seat : string
     * shopId : string
     * state : 0
     * takeCarAddress : string
     * takeCarLatitude : string
     * takeCarLongitude : string
     * takeCarTime : 2019-04-25T01:12:02.940Z
     * telephone : string
     * total : 0
     * userId : string
     * variableBox : string
     */

    private String agencyId;
    private String brand;
    private String callName;
    private String callTelephone;
    private String carTategory;
    private String createTime;
    private double dayPrice;
    private String handleTime;
    private String id;
    private String idNumber;
    private String image;
    private int isComment;
    private int isReturn;
    private int isTake;
    private String name;
    private int payMethod;
    private String payTime;
    private String remark;
    private int rentDay;
    private String returnCarAddress;
    private String returnCarLatitude;
    private String returnCarLongitude;
    private String returnCarTime;
    private String seat;
    private String shopId;
    private int state;
    private String takeCarAddress;
    private String takeCarLatitude;
    private String takeCarLongitude;
    private String takeCarTime;
    private String telephone;
    private double total;
    private String userId;
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

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallTelephone() {
        return callTelephone;
    }

    public void setCallTelephone(String callTelephone) {
        this.callTelephone = callTelephone;
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

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public int getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(int isReturn) {
        this.isReturn = isReturn;
    }

    public int getIsTake() {
        return isTake;
    }

    public void setIsTake(int isTake) {
        this.isTake = isTake;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRentDay() {
        return rentDay;
    }

    public void setRentDay(int rentDay) {
        this.rentDay = rentDay;
    }

    public String getReturnCarAddress() {
        return returnCarAddress;
    }

    public void setReturnCarAddress(String returnCarAddress) {
        this.returnCarAddress = returnCarAddress;
    }

    public String getReturnCarLatitude() {
        return returnCarLatitude;
    }

    public void setReturnCarLatitude(String returnCarLatitude) {
        this.returnCarLatitude = returnCarLatitude;
    }

    public String getReturnCarLongitude() {
        return returnCarLongitude;
    }

    public void setReturnCarLongitude(String returnCarLongitude) {
        this.returnCarLongitude = returnCarLongitude;
    }

    public String getReturnCarTime() {
        return returnCarTime;
    }

    public void setReturnCarTime(String returnCarTime) {
        this.returnCarTime = returnCarTime;
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

    public String getTakeCarAddress() {
        return takeCarAddress;
    }

    public void setTakeCarAddress(String takeCarAddress) {
        this.takeCarAddress = takeCarAddress;
    }

    public String getTakeCarLatitude() {
        return takeCarLatitude;
    }

    public void setTakeCarLatitude(String takeCarLatitude) {
        this.takeCarLatitude = takeCarLatitude;
    }

    public String getTakeCarLongitude() {
        return takeCarLongitude;
    }

    public void setTakeCarLongitude(String takeCarLongitude) {
        this.takeCarLongitude = takeCarLongitude;
    }

    public String getTakeCarTime() {
        return takeCarTime;
    }

    public void setTakeCarTime(String takeCarTime) {
        this.takeCarTime = takeCarTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVariableBox() {
        return variableBox;
    }

    public void setVariableBox(String variableBox) {
        this.variableBox = variableBox;
    }

    public RentOrder() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.agencyId);
        dest.writeString(this.brand);
        dest.writeString(this.callName);
        dest.writeString(this.callTelephone);
        dest.writeString(this.carTategory);
        dest.writeString(this.createTime);
        dest.writeDouble(this.dayPrice);
        dest.writeString(this.handleTime);
        dest.writeString(this.id);
        dest.writeString(this.idNumber);
        dest.writeString(this.image);
        dest.writeInt(this.isComment);
        dest.writeInt(this.isReturn);
        dest.writeInt(this.isTake);
        dest.writeString(this.name);
        dest.writeInt(this.payMethod);
        dest.writeString(this.payTime);
        dest.writeString(this.remark);
        dest.writeInt(this.rentDay);
        dest.writeString(this.returnCarAddress);
        dest.writeString(this.returnCarLatitude);
        dest.writeString(this.returnCarLongitude);
        dest.writeString(this.returnCarTime);
        dest.writeString(this.seat);
        dest.writeString(this.shopId);
        dest.writeInt(this.state);
        dest.writeString(this.takeCarAddress);
        dest.writeString(this.takeCarLatitude);
        dest.writeString(this.takeCarLongitude);
        dest.writeString(this.takeCarTime);
        dest.writeString(this.telephone);
        dest.writeDouble(this.total);
        dest.writeString(this.userId);
        dest.writeString(this.variableBox);
    }

    protected RentOrder(Parcel in) {
        this.agencyId = in.readString();
        this.brand = in.readString();
        this.callName = in.readString();
        this.callTelephone = in.readString();
        this.carTategory = in.readString();
        this.createTime = in.readString();
        this.dayPrice = in.readDouble();
        this.handleTime = in.readString();
        this.id = in.readString();
        this.idNumber = in.readString();
        this.image = in.readString();
        this.isComment = in.readInt();
        this.isReturn = in.readInt();
        this.isTake = in.readInt();
        this.name = in.readString();
        this.payMethod = in.readInt();
        this.payTime = in.readString();
        this.remark = in.readString();
        this.rentDay = in.readInt();
        this.returnCarAddress = in.readString();
        this.returnCarLatitude = in.readString();
        this.returnCarLongitude = in.readString();
        this.returnCarTime = in.readString();
        this.seat = in.readString();
        this.shopId = in.readString();
        this.state = in.readInt();
        this.takeCarAddress = in.readString();
        this.takeCarLatitude = in.readString();
        this.takeCarLongitude = in.readString();
        this.takeCarTime = in.readString();
        this.telephone = in.readString();
        this.total = in.readDouble();
        this.userId = in.readString();
        this.variableBox = in.readString();
    }

    public static final Creator<RentOrder> CREATOR = new Creator<RentOrder>() {
        @Override
        public RentOrder createFromParcel(Parcel source) {
            return new RentOrder(source);
        }

        @Override
        public RentOrder[] newArray(int size) {
            return new RentOrder[size];
        }
    };
}
