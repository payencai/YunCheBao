package com.example.yunchebao.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/23 09:53
 * 邮箱：771548229@qq..com
 */
public class ApplyFriend implements Serializable{
    /**
     * id : 051e4cc1-31a2-45a2-9e2d-571ebce0b306
     * userId : 90039d05-4b5e-4381-92a0-8346c6233afc
     * state : 0
     * name : 13202908144
     * headPortrait : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2018121711102617
     * applyReason : 滚滚滚
     * rejectReason : null
     * createTime : 2018-12-22 18:36:15
     */
    private String hxAccount;

    public String getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(String hxAccount) {
        this.hxAccount = hxAccount;
    }

    private String id;
    private String userId;
    private int state;
    private String name;
    private String headPortrait;
    private String applyReason;
    private String rejectReason;
    private String createTime;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * applyReason : string
     * createTime : 2018-12-23T01:52:31.650Z
     * headPortrait : string
     * id : string
     * name : string
     * rejectReason : string
     * state : 0
     * userId : string
     */

}
