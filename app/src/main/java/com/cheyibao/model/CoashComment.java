package com.cheyibao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/4 11:03
 * 邮箱：771548229@qq..com
 */
public class CoashComment implements Serializable{

    /**
     * coachId : string
     * content : string
     * createTime : 2019-01-04T03:08:52.504Z
     * headPortrait : string
     * id : string
     * isAnonymous : 0
     * isDel : 0
     * merchantContent : string
     * merchantId : string
     * name : string
     * photo : string
     * photos : ["string"]
     * replyTime : 2019-01-04T03:08:52.504Z
     * score : 0
     * state : 0
     * userId : string
     */

    private String coachId;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private int isAnonymous;
    private int isDel;
    private String merchantContent;
    private String merchantId;
    private String name;
    private String photo;
    private String replyTime;
    private float score;
    private int state;
    private String userId;
    private List<String> photos;

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getMerchantContent() {
        return merchantContent;
    }

    public void setMerchantContent(String merchantContent) {
        this.merchantContent = merchantContent;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
