package com.xihubao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/7 16:25
 * 邮箱：771548229@qq..com
 */
public class RoadItem implements Serializable{

    /**
     * id : 0d02d7ed-889b-46eb-82ca-906a4d30ebee
     * shopName : 昆明普利司通轮胎电瓶经营部
     * createTime : 2018-12-11 00:00:00
     * logo : http://xiaoshian.oss-cn-shenzhen.aliyuncs.com/image/2018091421015425
     * saleTelephone : 400-2345678
     * remark : 全昆明24小时小车外出道路救援服务，上门现场补胎、加气、换轮胎。
     * province : 云南省
     * city : 昆明市
     * area : 西山区
     * address : 世纪半岛机械谷
     * longitude : 102.691694
     * latitude : 24.993148
     * distance : 1109.57
     * roadRescueServeList : [{"id":"9426a052-7073-4582-8aaf-4d26a44c1bb9","title":"电瓶搭电","createTime":"2018-12-11 16:31:34","shopId":"0d02d7ed-889b-46eb-82ca-906a4d30ebee","content":"电瓶漏电"}]
     */

    private String id;
    private String shopName;
    private String createTime;
    private String logo;
    private String saleTelephone;
    private String remark;
    private String province;
    private String city;
    private String area;
    private String address;
    private String longitude;
    private String latitude;
    private double distance;
    private List<RoadRescueServeListBean> roadRescueServeList;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<RoadRescueServeListBean> getRoadRescueServeList() {
        return roadRescueServeList;
    }

    public void setRoadRescueServeList(List<RoadRescueServeListBean> roadRescueServeList) {
        this.roadRescueServeList = roadRescueServeList;
    }

    public static class RoadRescueServeListBean implements Serializable{
        /**
         * id : 9426a052-7073-4582-8aaf-4d26a44c1bb9
         * title : 电瓶搭电
         * createTime : 2018-12-11 16:31:34
         * shopId : 0d02d7ed-889b-46eb-82ca-906a4d30ebee
         * content : 电瓶漏电
         */

        private String id;
        private String title;
        private String createTime;
        private String shopId;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
