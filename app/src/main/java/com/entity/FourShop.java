package com.entity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/20 18:14
 * 邮箱：771548229@qq..com
 */
public class FourShop implements Serializable{


    /**
     * id : 29eb72cd-78ea-4555-89cb-cb9c1f534083
     * shopName : 云南联迪汽车服务有限公司
     * createTime : 2018-12-11 00:00:00
     * logo : http://xiaoshian.oss-cn-shenzhen.aliyuncs.com/image/2018091421015425
     * brand : 奥迪
     * saleTelephone : 020-22131578
     * price : 50
     * amStart : 1970-01-01 08:00:00
     * amStop : 1970-01-01 12:00:00
     * pmStart : 1970-01-01 14:00:00
     * pmStop : 1970-01-01 18:00:00
     * city : 昆明市
     * address : 昆明市白龙路522号
     * longitude : 123.454647
     * latitude : 23.536472
     * distance : 1033.63
     */

    private String id;
    private String shopName;
    private String createTime;
    private String logo;
    private String brand;
    private String saleTelephone;
    private int price;
    private String amStart;
    private String amStop;
    private String pmStart;
    private String pmStop;
    private String city;
    private String address;
    private String longitude;
    private String latitude;
    private double distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSaleTelephone() {
        return saleTelephone;
    }

    public void setSaleTelephone(String saleTelephone) {
        this.saleTelephone = saleTelephone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
