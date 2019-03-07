package com.cheyibao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 17:26
 * 邮箱：771548229@qq..com
 */
public class DrvingSchool implements Serializable{

    /**
     * address : string
     * alipay : string
     * amEnd : string
     * amStart : string
     * audit : 0
     * bankKey : string
     * bankName : string
     * brand : string
     * businessImage : string
     * city : string
     * closeTime : 2019-01-03T09:24:24.809Z
     * createTime : 2019-01-03T09:24:24.809Z
     * distance : 0
     * district : string
     * geoHash : string
     * grade : 0
     * header : string
     * id : string
     * idKey : string
     * latitude : string
     * location : {"lat":0,"lon":0}
     * logo : string
     * longitude : string
     * merchantNo : string
     * name : string
     * pmEnd : string
     * pmSatrt : string
     * province : string
     * rejectReason : string
     * score : 0
     * serviceTelephone : string
     * state : 0
     * telephone : string
     * timeLimit : 2019-01-03T09:24:24.809Z
     * timeLimitDay : 0
     * type : 0
     * user : [{"createTime":"2019-01-03T09:24:24.809Z","headPortrait":"string","id":"string","lastLoginIp":"string","lastLoginTime":"2019-01-03T09:24:24.809Z","name":"string","password":"string","role":"string","shopId":"string","shopType":0,"token":"string","username":"string"}]
     * wechat : string
     */

    private String address;
    private String alipay;
    private String amEnd;
    private String banner;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    private String amStart;
    private int audit;
    private String bankKey;
    private String bankName;
    private String brand;
    private String businessImage;
    private String city;
    private String closeTime;
    private String createTime;
    private double distance;
    private String district;
    private String geoHash;
    private int grade;
    private String header;
    private String id;
    private String idKey;
    private String latitude;
    private LocationBean location;
    private String logo;
    private String longitude;
    private String merchantNo;
    private String name;
    private String pmEnd;
    private String pmSatrt;
    private String province;
    private String rejectReason;
    private double score;
    private String serviceTelephone;
    private int state;
    private String telephone;
    private String timeLimit;
    private int timeLimitDay;
    private int type;
    private String wechat;
    private List<UserBean> user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
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

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getBankKey() {
        return bankKey;
    }

    public void setBankKey(String bankKey) {
        this.bankKey = bankKey;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBusinessImage() {
        return businessImage;
    }

    public void setBusinessImage(String businessImage) {
        this.businessImage = businessImage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
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

    public void setGrade(int  grade) {
        this.grade = grade;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getServiceTelephone() {
        return serviceTelephone;
    }

    public void setServiceTelephone(String serviceTelephone) {
        this.serviceTelephone = serviceTelephone;
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

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getTimeLimitDay() {
        return timeLimitDay;
    }

    public void setTimeLimitDay(int timeLimitDay) {
        this.timeLimitDay = timeLimitDay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
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

    public static class UserBean implements Serializable{
        /**
         * createTime : 2019-01-03T09:24:24.809Z
         * headPortrait : string
         * id : string
         * lastLoginIp : string
         * lastLoginTime : 2019-01-03T09:24:24.809Z
         * name : string
         * password : string
         * role : string
         * shopId : string
         * shopType : 0
         * token : string
         * username : string
         */

        private String createTime;
        private String headPortrait;
        private String id;
        private String lastLoginIp;
        private String lastLoginTime;
        private String name;
        private String password;
        private String role;
        private String shopId;
        private int shopType;
        private String token;
        private String username;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastLoginIp() {
            return lastLoginIp;
        }

        public void setLastLoginIp(String lastLoginIp) {
            this.lastLoginIp = lastLoginIp;
        }

        public String getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(String lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getShopType() {
            return shopType;
        }

        public void setShopType(int shopType) {
            this.shopType = shopType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
