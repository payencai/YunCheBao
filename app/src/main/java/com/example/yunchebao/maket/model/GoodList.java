package com.example.yunchebao.maket.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/10 14:42
 * 邮箱：771548229@qq..com
 */
public class GoodList implements Serializable{


    /**
     * babyMerchantId : string
     * commodityDetail : string
     * commodityImage : string
     * createTime : 0
     * discountPrice : 0
     * distance : 0
     * firstId : string
     * geoHash : string
     * id : string
     * latitude : string
     * location : {"lat":0,"lon":0}
     * longitude : string
     * name : string
     * number : 0
     * orderNum : 0
     * originalPrice : 0
     * score : 0
     * secondId : string
     */

    private String babyMerchantId;
    private String commodityDetail;
    private String commodityImage;

    private double discountPrice;
    private double distance;
    private String firstId;
    private String geoHash;
    private String id;
    private String latitude;
    private LocationBean location;
    private String longitude;
    private String name;
    private int number;
    private int orderNum;
    private double originalPrice;
    private float score;
    private String secondId;

    public String getBabyMerchantId() {
        return babyMerchantId;
    }

    public void setBabyMerchantId(String babyMerchantId) {
        this.babyMerchantId = babyMerchantId;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }


    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
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

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
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

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public static class LocationBean implements Serializable{
        /**
         * lat : 0
         * lon : 0
         */

        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }
    }
}
