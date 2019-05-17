package com.cheyibao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RentShop implements Parcelable {
    /**
     * address : string
     * agencyId : string
     * area : string
     * banner : string
     * city : string
     * createTime : 2019-04-11T08:50:25.385Z
     * distance : 0
     * geoHash : string
     * grade : 0
     * id : string
     * isOnlineServe : 0
     * latitude : string
     * logo : string
     * longitude : string
     * name : string
     * number : 0
     * orderNum : 0
     * province : string
     * saleTelephone : string
     * score : 0
     * shopNo : string
     */

    private String address;
    private String agencyId;
    private String area;
    private String banner;
    private String city;
    private String createTime;
    private double distance;
    private String geoHash;
    private int grade;
    private String id;
    private int isOnlineServe;
    private String latitude;
    private String logo;
    private String longitude;
    private String name;
    private int number;
    private int orderNum;
    private String province;
    private String saleTelephone;
    private double score;
    private String shopNo;
    private String amStart;
    private String amStop;
    private String pmStart;
    private String pmStop;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsOnlineServe() {
        return isOnlineServe;
    }

    public void setIsOnlineServe(int isOnlineServe) {
        this.isOnlineServe = isOnlineServe;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSaleTelephone() {
        return saleTelephone;
    }

    public void setSaleTelephone(String saleTelephone) {
        this.saleTelephone = saleTelephone;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getAmStart() {
        return amStart;
    }

    public void setAmStart(String amStart) {
        this.amStart = amStart;
    }

    public String getAmStop() {
        return amStop;
    }

    public void setAmStop(String amStop) {
        this.amStop = amStop;
    }

    public String getPmStart() {
        return pmStart;
    }

    public void setPmStart(String pmStart) {
        this.pmStart = pmStart;
    }

    public String getPmStop() {
        return pmStop;
    }

    public void setPmStop(String pmStop) {
        this.pmStop = pmStop;
    }

    public RentShop() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.agencyId);
        dest.writeString(this.area);
        dest.writeString(this.banner);
        dest.writeString(this.city);
        dest.writeString(this.createTime);
        dest.writeDouble(this.distance);
        dest.writeString(this.geoHash);
        dest.writeInt(this.grade);
        dest.writeString(this.id);
        dest.writeInt(this.isOnlineServe);
        dest.writeString(this.latitude);
        dest.writeString(this.logo);
        dest.writeString(this.longitude);
        dest.writeString(this.name);
        dest.writeInt(this.number);
        dest.writeInt(this.orderNum);
        dest.writeString(this.province);
        dest.writeString(this.saleTelephone);
        dest.writeDouble(this.score);
        dest.writeString(this.shopNo);
        dest.writeString(this.amStart);
        dest.writeString(this.amStop);
        dest.writeString(this.pmStart);
        dest.writeString(this.pmStop);
    }

    protected RentShop(Parcel in) {
        this.address = in.readString();
        this.agencyId = in.readString();
        this.area = in.readString();
        this.banner = in.readString();
        this.city = in.readString();
        this.createTime = in.readString();
        this.distance = in.readDouble();
        this.geoHash = in.readString();
        this.grade = in.readInt();
        this.id = in.readString();
        this.isOnlineServe = in.readInt();
        this.latitude = in.readString();
        this.logo = in.readString();
        this.longitude = in.readString();
        this.name = in.readString();
        this.number = in.readInt();
        this.orderNum = in.readInt();
        this.province = in.readString();
        this.saleTelephone = in.readString();
        this.score = in.readInt();
        this.shopNo = in.readString();
        this.amStart = in.readString();
        this.amStop = in.readString();
        this.pmStart = in.readString();
        this.pmStop = in.readString();
    }

    public static final Creator<RentShop> CREATOR = new Creator<RentShop>() {
        @Override
        public RentShop createFromParcel(Parcel source) {
            return new RentShop(source);
        }

        @Override
        public RentShop[] newArray(int size) {
            return new RentShop[size];
        }
    };
}
