package com.entity;

import java.io.Serializable;

/**
 * Created by sdhcjhss on 2017/11/16.
 */

public class PhoneAddressEntity implements Serializable{
     private String id;     //      "id": "1001",
    private String user_id;     //          "user_id": "1001",
    private String name;     //           "name": "杨己乐",
    private String mobile;     //          "mobile": "18826136974",
    private String province;     //          "province": "广东省",
    private String city;     //         "city": "广州",
    private String area;     //         "area": "番禺",
    private String town;     //          "town": "小谷围",
    private String detail;     //          "detail": "广东工业大学生活西区"
    private int is_def;     //           "is_def": 1//是否是默认收货地址

    public int getIs_def() {
        return is_def;
    }

    public void setIs_def(int is_def) {
        this.is_def = is_def;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
