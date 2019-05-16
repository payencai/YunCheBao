package com.example.yunchebao.rongcloud.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/27 10:01
 * 邮箱：771548229@qq..com
 */
public class MyFriend extends LitePalSupport implements Serializable{
    /**
     * id : 217e6ced-aed9-418a-8b64-8954cc5785c9
     * userId : 7087b41a-99f5-4da2-ac59-04ccab8835c8
     * name : 你是个好人
     * headPortrait : http://thirdqq.qlogo.cn/qqapp/101536200/53C8A7B53D5F3D03F110746F59A64C61/100
     * hxAccount : RY-7788
     * nickName :
     * isNotice : 0
     */
    @SerializedName(value="id")
    public String myid;
    private String userId;
    private String name;
    private String headPortrait;
    private String hxAccount;
    private String nickName;
    private int isNotice;

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
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

    public String getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(String hxAccount) {
        this.hxAccount = hxAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIsNotice() {
        return isNotice;
    }

    public void setIsNotice(int isNotice) {
        this.isNotice = isNotice;
    }

    /**
     * headPortrait : string
     * hxAccount : string
     * id : string
     * isNotice : 0
     * name : string
     * nickName : string
     * userId : string
     */


}
