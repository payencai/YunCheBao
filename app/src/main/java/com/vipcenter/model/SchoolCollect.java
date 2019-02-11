package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/11 14:18
 * 邮箱：771548229@qq..com
 */
public class SchoolCollect {

    /**
     * address : string
     * city : string
     * district : string
     * grade : 0
     * id : string
     * logo : string
     * merchantId : string
     * name : string
     * province : string
     * score : 0
     * updateTime : 2019-02-11T06:17:13.157Z
     * userId : string
     */

    private String address;
    private String city;
    private String district;
    private int grade;
    private String id;
    private String logo;
    private String merchantId;
    private String name;
    private String province;
    private float score;
    private String updateTime;
    private String userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
