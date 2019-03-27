package com.vipcenter.model;

/**
 * 代理商信息
 */
public class AgencyInfo {
    /**
     *      "address": "string",
     *       "area": "string",
     *       "city": "string",
     *       "headPortrait": "string",
     *       "id": "string",
     *       "name": "string",
     *       "province": "string",
     *       "shopNumber": 0,
     *       "telephone": "string"
     */
    private String address;
    private String area;
    private String city;
    private String province;
    private String headPortrait;
    private String id;
    private String name;
    private int shopNumber;
    private String telephone;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShopNumber() {
        return shopNumber;
    }

    public void setShopNumber(int shopNumber) {
        this.shopNumber = shopNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
