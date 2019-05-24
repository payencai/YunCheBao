package com.example.yunchebao.road.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/4/29 17:23
 * 邮箱：771548229@qq..com
 */
public class RoadDetail implements Serializable {

    /**
     * address : string
     * area : string
     * banner : string
     * city : string
     * createTime : 2019-04-29T09:23:24.130Z
     * distance : 0
     * grade : 0
     * id : string
     * latitude : string
     * logo : string
     * longitude : string
     * number : 0
     * orderNum : 0
     * province : string
     * remark : string
     * saleTelephone : string
     * score : 0
     * serve : string
     * shopName : string
     * videos : string
     * vimgs : string
     */
    private String pmStart;
    private String pmStop;
    private String amStart;

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

    private String amStop;
    private String address;
    private String area;
    private String banner;
    private String city;
    private String createTime;
    private double distance;
    private int grade;
    private String id;
    private String latitude;
    private String logo;
    private String longitude;
    private int number;
    private int orderNum;
    private String province;
    private String remark;
    private String saleTelephone;
    private float score;
    private String serve;
    private String shopName;
    private String videos;
    private String vimgs;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getVimgs() {
        return vimgs;
    }

    public void setVimgs(String vimgs) {
        this.vimgs = vimgs;
    }
}
