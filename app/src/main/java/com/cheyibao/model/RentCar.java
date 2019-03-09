package com.cheyibao.model;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/8 17:40
 * 邮箱：771548229@qq..com
 */
public class RentCar implements Serializable{


    /**
     * address : string
     * agencyId : string
     * amEnd : string
     * amStart : string
     * banner : string
     * brand : string
     * city : string
     * createTime : 0
     * distance : 0
     * district : string
     * geoHash : string
     * grade : 0
     * id : string
     * latitude : string
     * location : {"lat":0,"lon":0}
     * logo : string
     * longitude : string
     * merchantNo : string
     * name : string
     * number : 0
     * orderNum : 0
     * pmEnd : string
     * pmSatrt : string
     * province : string
     * score : 0
     * serviceTelephone : string
     * type : 0
     */

    private String address;
    private String agencyId;
    private String amEnd;
    private String amStart;
    private String banner;
    private String brand;
    private String city;
    private String createTime;
    private double distance;
    private String district;
    private String geoHash;
    private int grade;
    private String id;
    private String latitude;
    private LocationBean location;
    private String logo;
    private String longitude;
    private String merchantNo;
    private String name;
    private int number;
    private int orderNum;
    private String pmEnd;
    private String pmSatrt;
    private String province;
    private float score;
    private String serviceTelephone;
    private int type;
    private List<String> images=new ArrayList<>();
    public String getAddress() {
        return address;
    }
    public List<String> getImages(){
        if(!TextUtils.isEmpty(banner)){
            String[]imgs=banner.split(",");
            for (int i = 0; i <imgs.length ; i++) {
                images.add(imgs[i]);
            }
        }
        return images;
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

    public String getAmEnd() {
        return amEnd;
    }

    public void setAmEnd(String amEnd) {
        this.amEnd = amEnd;
    }

    public String getAmStart() {
        return amStart;
    }

    public void setAmStart(String amStart) {
        this.amStart = amStart;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
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

    public String getPmEnd() {
        return pmEnd;
    }

    public void setPmEnd(String pmEnd) {
        this.pmEnd = pmEnd;
    }

    public String getPmSatrt() {
        return pmSatrt;
    }

    public void setPmSatrt(String pmSatrt) {
        this.pmSatrt = pmSatrt;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getServiceTelephone() {
        return serviceTelephone;
    }

    public void setServiceTelephone(String serviceTelephone) {
        this.serviceTelephone = serviceTelephone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
