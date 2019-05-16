package com.example.yunchebao.rongcloud.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/17 14:19
 * 邮箱：771548229@qq..com
 */
public class Group implements Serializable {


    /**
     * createTime : 2018-12-23T07:17:25.662Z
     * crowdName : string
     * crowdUserId : string
     * description : string
     * hxCrowdId : string
     * id : 0
     * image : string
     * qrCodeCard : string
     */
    private boolean isMyGroup;

    public boolean isMyGroup() {
        return isMyGroup;
    }

    public void setMyGroup(boolean myGroup) {
        isMyGroup = myGroup;
    }

    private String createTime;
    private String crowdName;
    private String crowdUserId;
    private String description;
    private String hxCrowdId;
    private int id;
    private String image;
    private String qrCodeCard;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCrowdName() {
        return crowdName;
    }

    public void setCrowdName(String crowdName) {
        this.crowdName = crowdName;
    }

    public String getCrowdUserId() {
        return crowdUserId;
    }

    public void setCrowdUserId(String crowdUserId) {
        this.crowdUserId = crowdUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHxCrowdId() {
        return hxCrowdId;
    }

    public void setHxCrowdId(String hxCrowdId) {
        this.hxCrowdId = hxCrowdId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQrCodeCard() {
        return qrCodeCard;
    }

    public void setQrCodeCard(String qrCodeCard) {
        this.qrCodeCard = qrCodeCard;
    }
}
