package com.example.yunchebao.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/24 11:08
 * 邮箱：771548229@qq..com
 */
public class GroupUser implements Serializable{

    /**
     * headPortrait : string
     * hxAccount : string
     * id : 0
     * isNotice : 0
     * nickName : string
     * userId : string
     */
    private int flag=0;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private String headPortrait;
    private String hxAccount;
    private int id;
    private int isNotice;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
