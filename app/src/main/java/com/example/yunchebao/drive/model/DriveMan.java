package com.example.yunchebao.drive.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/4/12 16:35
 * 邮箱：771548229@qq..com
 */
public class DriveMan implements Serializable{

    /**
     * birthday : string
     * createTime : 2019-04-12T08:35:42.052Z
     * drivingAge : string
     * grade : 0
     * headPortrait : string
     * id : string
     * imgs : string
     * isDelete : 0
     * name : string
     * nativePlace : string
     * number : 0
     * score : 0.0
     * sex : string
     * shopId : string
     * telephone : string
     */

    private String birthday;
    private String createTime;
    private String drivingAge;
    private int grade;
    private String headPortrait;
    private String id;
    private String imgs;
    private int isDelete;
    private String name;
    private String nativePlace;
    private int number;
    private float score;
    private String sex;
    private String shopId;
    private String telephone;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDrivingAge() {
        return drivingAge;
    }

    public void setDrivingAge(String drivingAge) {
        this.drivingAge = drivingAge;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
