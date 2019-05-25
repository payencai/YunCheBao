package com.example.yunchebao.road.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/4/22 17:47
 * 邮箱：771548229@qq..com
 */
public class RoadOrder implements Serializable {

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * address : string
     * addressDetail : string
     * agencyId : string
     * area : string
     * carCategory : string
     * city : string
     * color : string
     * createTime : 2019-04-23T01:49:59.123Z
     * detail : string
     * handleTime : 2019-04-23T01:49:59.123Z
     * id : string
     * imgs : string
     * isComment : 0
     * latitude : string
     * longitude : string
     * name : string
     * payMethod : 0
     * payTime : 2019-04-23T01:49:59.123Z
     * price : 0.0
     * province : string
     * shopDelete : 0
     * shopId : string
     * state : 0
     * telephone : string
     * userDelete : 0
     * userId : string
     * video : string
     * vimg : string
     */
    private String shopName;
    private String address;
    private String addressDetail;
    private String agencyId;
    private String area;
    private String carCategory;
    private String city;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    private String color;
    private String createTime;
    private String detail;
    private String handleTime;
    private String id;
    private String imgs;
    private int isComment;
    private String latitude;
    private String longitude;
    private String name;
    private String logo;
    private int payMethod;
    private String payTime;
    private double price;
    private String province;
    private int shopDelete;
    private String shopId;
    private int state;
    private String telephone;
    private int userDelete;
    private String userId;
    private String video;
    private String vimg;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
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

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getShopDelete() {
        return shopDelete;
    }

    public void setShopDelete(int shopDelete) {
        this.shopDelete = shopDelete;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getUserDelete() {
        return userDelete;
    }

    public void setUserDelete(int userDelete) {
        this.userDelete = userDelete;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVimg() {
        return vimg;
    }

    public void setVimg(String vimg) {
        this.vimg = vimg;
    }
}
