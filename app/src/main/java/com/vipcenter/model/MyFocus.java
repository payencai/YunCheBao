package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/1/28 19:30
 * 邮箱：771548229@qq..com
 */
public class MyFocus {

    /**
     * createTime : 2019-01-28T11:21:50.971Z
     * friends : {"friendId":"string","id":"string","isNotice":0,"nickName":"string","userId":"string"}
     * headPortrait : string
     * id : string
     * isFocus : string
     * isFriend : string
     * name : string
     * userId : string
     * userType : 0
     */

    private String createTime;
    private FriendsBean friends;
    private String headPortrait;
    private String id;
    private String isFocus;
    private String isFriend;
    private String name;
    private String userId;
    private int userType;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(String isFocus) {
        this.isFocus = isFocus;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
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
}
