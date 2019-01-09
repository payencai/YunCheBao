package com.cheyibao.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/3 20:17
 * 邮箱：771548229@qq..com
 */
public class CoachItem implements Serializable{

    /**
     * coachHead : string
     * coachName : string
     * coachNo : string
     * createTime : 2019-01-03T12:16:52.362Z
     * id : string
     * isDel : 0
     * merchantId : string
     * score : 0
     */

    private String coachHead;
    private String coachName;
    private String coachNo;
    private String createTime;
    private String id;
    private int isDel;
    private String merchantId;
    private float score;

    public String getCoachHead() {
        return coachHead;
    }

    public void setCoachHead(String coachHead) {
        this.coachHead = coachHead;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
