package com.vipcenter.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/10 18:58
 * 邮箱：771548229@qq..com
 */
public class PersonAddress implements Serializable{

    /**
     * id : 1baa3f88-2f5d-459f-840f-5736c8dccfa7
     * userId : 90039d05-4b5e-4381-92a0-8346c6233afc
     * nickname : 哈哈哈
     * telephone : 13202908144
     * province : 福建省
     * city : 漳州市
     * district : 龙文区
     * address : 非凡哥呵呵红红火火
     * updateTime : 2019-01-10 18:30:36
     * isDefault : 1
     * isDel : 1
     */

    private String id;
    private String userId;
    private String nickname;
    private String telephone;
    private String province;
    private String city;
    private String district;
    private String address;
    private String updateTime;
    private int isDefault;
    private int isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
