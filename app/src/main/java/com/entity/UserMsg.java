package com.entity;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 09:58
 * 邮箱：771548229@qq..com
 */
public class UserMsg {

    /**
     * age : 0
     * background : string
     * birthday : string
     * carList : [{"carLogo":"string","drivingBackImg":"string","drivingPositiveImg":"string","fileNumber":"string","id":"string","isDefault":0,"models":"string","passTime":"2019-03-06T01:55:29.943Z","plateNumber":"string","userId":"string","userName":"string"}]
     * carShowState : 0
     * friends : {"friendId":"string","id":"string","isNotice":0,"nickName":"string","userId":"string"}
     * headPortrait : string
     * hxAccount : string
     * id : string
     * isFriend : string
     * name : string
     * sex : string
     * username : string
     */

    private int age;
    private String background;
    private String birthday;
    private int carShowState;
    private FriendsBean friends;
    private String headPortrait;
    private String hxAccount;
    private String id;
    private String isFriend;
    private String name;
    private String sex;
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

    public FriendsBean getFriends() {
        return friends;
    }

    public void setFriends(FriendsBean friends) {
        this.friends = friends;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
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

    public static class FriendsBean {
        /**
         * friendId : string
         * id : string
         * isNotice : 0
         * nickName : string
         * userId : string
         */

        private String friendId;
        private String id;
        private int isNotice;
        private String nickName;
        private String userId;

        public String getFriendId() {
            return friendId;
        }

        public void setFriendId(String friendId) {
            this.friendId = friendId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsNotice() {
            return isNotice;
        }

        public void setIsNotice(int isNotice) {
            this.isNotice = isNotice;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class CarListBean {
        /**
         * carLogo : string
         * drivingBackImg : string
         * drivingPositiveImg : string
         * fileNumber : string
         * id : string
         * isDefault : 0
         * models : string
         * passTime : 2019-03-06T01:55:29.943Z
         * plateNumber : string
         * userId : string
         * userName : string
         */

        private String carLogo;
        private String drivingBackImg;
        private String drivingPositiveImg;
        private String fileNumber;
        private String id;
        private int isDefault;
        private String models;
        private String passTime;
        private String plateNumber;
        private String userId;
        private String userName;

        public String getCarLogo() {
            return carLogo;
        }

        public void setCarLogo(String carLogo) {
            this.carLogo = carLogo;
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
