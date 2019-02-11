package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/11 15:51
 * 邮箱：771548229@qq..com
 */
public class OldCarCollect {

    /**
     * carCategoryDetailId : string
     * carImage : string
     * carPrice : 0
     * firstName : string
     * id : string
     * merchantid : string
     * oldCarMerchantCarId : string
     * secondName : string
     * thirdName : string
     * type : 0
     * updateTime : 2019-02-11T07:51:12.090Z
     * userId : string
     */

    private String carCategoryDetailId;
    private String carImage;
    private double carPrice;
    private String firstName;
    private String id;
    private String merchantid;
    private String oldCarMerchantCarId;
    private String secondName;
    private String thirdName;
    private int type;
    private String updateTime;
    private String userId;

    public String getCarCategoryDetailId() {
        return carCategoryDetailId;
    }

    public void setCarCategoryDetailId(String carCategoryDetailId) {
        this.carCategoryDetailId = carCategoryDetailId;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(String merchantid) {
        this.merchantid = merchantid;
    }

    public String getOldCarMerchantCarId() {
        return oldCarMerchantCarId;
    }

    public void setOldCarMerchantCarId(String oldCarMerchantCarId) {
        this.oldCarMerchantCarId = oldCarMerchantCarId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
