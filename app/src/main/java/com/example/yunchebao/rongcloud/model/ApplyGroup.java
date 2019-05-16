package com.example.yunchebao.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/23 15:59
 * 邮箱：771548229@qq..com
 */
public class ApplyGroup implements Serializable{

    /**
     * applyReason : string
     * createTime : 2018-12-23T07:58:30.990Z
     * crowdId : string
     * crowdName : string
     * headPortrait : string
     * id : string
     * name : string
     * rejectReason : string
     * state : 0
     * userId : string
     */

    private String applyReason;
    private String createTime;
    private String crowdId;
    private String crowdName;
    private String headPortrait;
    private String id;
    private String name;
    private String rejectReason;
    private int state;
    private String userId;

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(String crowdId) {
        this.crowdId = crowdId;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
