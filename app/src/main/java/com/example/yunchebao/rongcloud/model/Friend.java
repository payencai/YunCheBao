package com.example.yunchebao.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/17 14:18
 * 邮箱：771548229@qq..com
 */
public class Friend implements Serializable{

    /**
     * headPortrait : string
     * hxAccount : string
     * isFriend : string
     * name : string
     * nickName : string
     * userId : string
     */

    private String headPortrait;
    private String hxAccount;
    private String isFriend;
    private String name;
    private String nickName;
    private String userId;

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
