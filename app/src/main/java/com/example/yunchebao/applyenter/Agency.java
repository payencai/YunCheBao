package com.example.yunchebao.applyenter;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/4/30 11:06
 * 邮箱：771548229@qq..com
 */
public class Agency implements Serializable {

    /**
     * address : string
     * age : 0
     * area : string
     * cashImg : string
     * city : string
     * doorImg : string
     * grade : 0
     * headPortrait : string
     * id : string
     * imgs : string
     * latitude : string
     * longitude : string
     * name : string
     * province : string
     * roomImg : string
     * sex : string
     * shopNumber : 0
     * telephone : string
     */

    private String address;
    private int age;
    private String area;
    private String cashImg;
    private String city;
    private String doorImg;
    private int grade;
    private String headPortrait;
    private String id;
    private String imgs;
    private String latitude;
    private String longitude;
    private String name;
    private String province;
    private String roomImg;
    private String sex;
    private int shopNumber;
    private String telephone;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCashImg() {
        return cashImg;
    }

    public void setCashImg(String cashImg) {
        this.cashImg = cashImg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDoorImg() {
        return doorImg;
    }

    public void setDoorImg(String doorImg) {
        this.doorImg = doorImg;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
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

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRoomImg() {
        return roomImg;
    }

    public void setRoomImg(String roomImg) {
        this.roomImg = roomImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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
