package com.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/18 17:06
 * 邮箱：771548229@qq..com
 */
public class CarShop implements Serializable{


    /**
     * id : 4d391fef-93fd-4b12-bd96-b9e15369d88d
     * shopName : 一蝉洗车/修车店
     * createTime : 2018-12-29 00:00:00
     * logo : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011415401137
     * saleTelephone : 17688947788
     * price : 20.0
     * amStart : 00:00
     * amStop : 15:28
     * pmStart : 00:00
     * pmStop : 15:28
     * province : 广东省
     * city : 广州市
     * area : 番禺区
     * address : 撒打算打算的
     * longitude : 113.264434
     * latitude : 23.129162
     * remark : 法人信息或营业执照更换了，需要重新审核！
     * banner : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011415281914,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011415281973
     * grade : 1
     * distance : 16.46529237134518
     * score : 0.0
     * number : 0
     * orderNum : 0
     * videos :
     * vimgs :
     * kind : 3
     */

    private String id;
    private String shopName;
    private String createTime;
    private String logo;
    private String saleTelephone;
    private double price;
    private String amStart;
    private String amStop;
    private String pmStart;
    private String pmStop;
    private String province;
    private String city;
    private String area;
    private String address;
    private String longitude;
    private String latitude;
    private String remark;
    private String banner;
    private int grade;
    private double distance;
    private double score;
    private int number;
    private int orderNum;
    private String videos;
    private String vimgs;
    private int kind;

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

    public String getSaleTelephone() {
        return saleTelephone;
    }

    public void setSaleTelephone(String saleTelephone) {
        this.saleTelephone = saleTelephone;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
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

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }
}
