package com.example.yunchebao.rongcloud.model;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/27 10:16
 * 邮箱：771548229@qq..com
 */
public class MyGroup extends LitePalSupport implements Serializable{

    /**
     * createTime : 2018-12-27T02:16:45.022Z
     * crowdName : string
     * crowdUserId : string
     * description : string
     * hxCrowdId : string
     * id : 0
     * image : string
     * qrCodeCard : string
     */

    private String createTime;
    private String crowdName;
    private String crowdUserId;
    private String description;
    private String hxCrowdId;
    private String image;
    @SerializedName(value="id")
    public int myid;

    public int getMyid() {
        return myid;
    }

    public void setMyid(int myid) {
        this.myid = myid;
    }

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
