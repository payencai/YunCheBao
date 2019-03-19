package com.vipcenter.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/12/12 17:29
 * 邮箱：771548229@qq..com
 */
public class UserInfo implements Serializable{


    /**
     * age : 0
     * background : string
     * birthday : string
     * carList : [{"fileNumber":"string","id":"string","models":"string","passTime":"2019-02-27T02:25:25.031Z","plateNumber":"string","userId":"string","userName":"string"}]
     * carShowState : 0
     * headPortrait : string
     * hxAccount : string
     * hxPassword : string
     * id : string
     * isIdentityVerification : string
     * name : string
     * sex : string
     * token : string
     * username : string
     */

    private int age;
    private String background;
    private String birthday;
    private int carShowState;//1展示2不
    private String headPortrait;
    private String hxAccount;
    private String hxPassword;
    private String id;
    private String isIdentityVerification;
    private String name;
    private String sex;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String username;
    private List<CarListBean> carList;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getCarShowState() {
        return carShowState;
    }

    public void setCarShowState(int carShowState) {
        this.carShowState = carShowState;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(String hxAccount) {
        this.hxAccount = hxAccount;
    }

    public String getHxPassword() {
        return hxPassword;
    }

    public void setHxPassword(String hxPassword) {
        this.hxPassword = hxPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsIdentityVerification() {
        return isIdentityVerification;
    }

    public void setIsIdentityVerification(String isIdentityVerification) {
        this.isIdentityVerification = isIdentityVerification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CarListBean> getCarList() {
        return carList;
    }

    public void setCarList(List<CarListBean> carList) {
        this.carList = carList;
    }

    public static class CarListBean implements Serializable{
        /**
         * fileNumber : string
         * id : string
         * models : string
         * passTime : 2019-02-27T02:25:25.031Z
         * plateNumber : string
         * userId : string
         * userName : string
         */

        private String fileNumber;
        private String id;
        private String models;
        private String passTime;
        private String plateNumber;
        private String userId;
        private String userName;

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
    }
}
