package com.example.yunchebao.maket.model;

/**
 * 作者：凌涛 on 2019/3/9 16:14
 * 邮箱：771548229@qq..com
 */
public class GoodsShop {

    /**
     * id : 55af0e4a-b21b-456f-aed8-4939e343571a
     * shopNo : BBSC11484237
     * shopName : 守车待胎汽车轮胎
     * province : 广东省
     * city : 广州市
     * area : 番禺区
     * address : 大学城信息枢纽楼
     * createTime : 2019-01-10 00:00:00
     * saleTelephone : 400-123456
     * banner : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011220165591
     * logo : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011414354787
     * grade : 2
     * videos :
     * vimgs :
     */
    private double distance;
    private String latitude;
    private String longitude;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private String id;
    private String shopNo;
    private String shopName;
    private String province;
    private String city;
    private String area;
    private String address;
    private String createTime;
    private String saleTelephone;
    private String banner;
    private String logo;
    private int grade;
    private String videos;
    private String vimgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSaleTelephone() {
        return saleTelephone;
    }

    public void setSaleTelephone(String saleTelephone) {
        this.saleTelephone = saleTelephone;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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
