package com.example.yunchebao.fourshop.bean;

/**
 * 作者：凌涛 on 2019/4/17 17:03
 * 邮箱：771548229@qq..com
 */
public class FourShopData {


    /**
     * address : string
     * amStart : string
     * amStop : string
     * area : string
     * banner : string
     * brand : string
     * city : string
     * createTime : 2019-04-18T08:10:36.026Z
     * distance : 0.0
     * geoHash : string
     * grade : 0
     * id : string
     * latitude : string
     * location : {"lat":0,"lon":0}
     * logo : string
     * longitude : string
     * number : 0
     * orderNum : 0
     * pmStart : string
     * pmStop : string
     * price : 0.0
     * province : string
     * saleTelephone : string
     * score : 0.0
     * shopName : string
     * videos : string
     * vimgs : string
     */

    private String address;

    private String area;
    private String banner;
    private String brand;
    private String city;
    private String createTime;
    private double distance;
    private String geoHash;
    private int grade;
    private String id;
    private String latitude;
    private LocationBean location;
    private String logo;
    private String longitude;
    private int number;
    private int orderNum;
    private String pmStart;
    private String pmStop;
    private String amStart;
    private String amStop;
    private double price;
    private String province;
    private String saleTelephone;
    private float score;
    private String shopName;
    private String videos;
    private String vimgs;

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public static class LocationBean {
        /**
         * lat : 0.0
         * lon : 0.0
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
