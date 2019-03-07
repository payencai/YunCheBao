package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/1/28 19:30
 * 邮箱：771548229@qq..com
 */
public class MyFocus {


    /**
     * id : 7a04b4db-0e11-4413-a55b-0db50c7f8ce4
     * userId : 5ed5fe0f-8708-4974-ac84-6122f402fb65
     * name : hwxkNI6BzF
     * headPortrait : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019021318193652
     * createTime : 2019-03-07 17:50:38
     * userType : 1
     * friends : null
     * isFriend : 0
     * isFocus : null
     */

    private String id;
    private String userId;
    private String name;
    private String headPortrait;
    private String createTime;
    private int userType;
    private Object friends;
    private String isFriend;
    private Object isFocus;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Object getFriends() {
        return friends;
    }

    public void setFriends(Object friends) {
        this.friends = friends;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public Object getIsFocus() {
        return isFocus;
    }

    public void setIsFocus(Object isFocus) {
        this.isFocus = isFocus;
    }
}
