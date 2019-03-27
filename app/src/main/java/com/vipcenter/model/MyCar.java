package com.vipcenter.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/3/27 10:16
 * 邮箱：771548229@qq..com
 */
public class MyCar implements Serializable{

    /**
     * applyTime : 2019-03-27T02:16:39.170Z
     * carLogo : string
     * denyReason : string
     * denyTime : 2019-03-27T02:16:39.170Z
     * drivingBackImg : string
     * drivingPositiveImg : string
     * fileNumber : string
     * id : string
     * identityVerifications : [{"backImg":"string","createTime":"2019-03-27T02:16:39.170Z","id":"string","idNo":"string","name":"string","positiveImg":"string"}]
     * isDefault : 0
     * models : string
     * passTime : 2019-03-27T02:16:39.170Z
     * plateNumber : string
     * type : 0
     * userId : string
     * userName : string
     */

    private String applyTime;
    private String carLogo;
    private String denyReason;
    private String denyTime;
    private String drivingBackImg;
    private String drivingPositiveImg;
    private String fileNumber;
    private String id;
    private int isDefault;
    private String models;
    private String passTime;
    private String plateNumber;
    private int type;
    private String userId;
    private String userName;
    private List<IdentityVerificationsBean> identityVerifications;

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getCarLogo() {
        return carLogo;
    }

    public void setCarLogo(String carLogo) {
        this.carLogo = carLogo;
    }

    public String getDenyReason() {
        return denyReason;
    }

    public void setDenyReason(String denyReason) {
        this.denyReason = denyReason;
    }

    public String getDenyTime() {
        return denyTime;
    }

    public void setDenyTime(String denyTime) {
        this.denyTime = denyTime;
    }

    public String getDrivingBackImg() {
        return drivingBackImg;
    }

    public void setDrivingBackImg(String drivingBackImg) {
        this.drivingBackImg = drivingBackImg;
    }

    public String getDrivingPositiveImg() {
        return drivingPositiveImg;
    }

    public void setDrivingPositiveImg(String drivingPositiveImg) {
        this.drivingPositiveImg = drivingPositiveImg;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<IdentityVerificationsBean> getIdentityVerifications() {
        return identityVerifications;
    }

    public void setIdentityVerifications(List<IdentityVerificationsBean> identityVerifications) {
        this.identityVerifications = identityVerifications;
    }

    public static class IdentityVerificationsBean implements Serializable{
        /**
         * backImg : string
         * createTime : 2019-03-27T02:16:39.170Z
         * id : string
         * idNo : string
         * name : string
         * positiveImg : string
         */

        private String backImg;
        private String createTime;
        private String id;
        private String idNo;
        private String name;
        private String positiveImg;

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdNo() {
            return idNo;
        }

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPositiveImg() {
            return positiveImg;
        }

        public void setPositiveImg(String positiveImg) {
            this.positiveImg = positiveImg;
        }
    }
}
