package com.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/18 17:06
 * 邮箱：771548229@qq..com
 */
public class CarShop implements Serializable{

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    /**
     * address : string
     * amStart : 2018-12-18T09:05:23.739Z
     * amStop : 2018-12-18T09:05:23.739Z
     * banner : string
     * city : string
     * createTime : 2018-12-18T09:05:23.739Z
     * distance : 0
     * grade : 0
     * id : string
     * latitude : string
     * logo : string
     * longitude : string
     * pmStart : 2018-12-18T09:05:23.739Z
     * pmStop : 2018-12-18T09:05:23.739Z
     * price : 0
     * remark : string
     * saleTelephone : string
     * shopName : string
     */

    private int  orderNum;
    private int number;
    private double score;
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

    private String province;
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private String address;
    private String amStart;
    private String amStop;
    private String banner;
    private String city;
    private String createTime;
    private double distance;
    private double grade;
    private String id;
    private String latitude;
    private String logo;
    private String longitude;
    private String pmStart;
    private String pmStop;
    private double price;
    private String remark;
    private String saleTelephone;
    private String shopName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSaleTelephone() {
        return saleTelephone;
    }

    public void setSaleTelephone(String saleTelephone) {
        this.saleTelephone = saleTelephone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
