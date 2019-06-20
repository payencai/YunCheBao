package com.example.yunchebao.maket.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/11 11:09
 * 邮箱：771548229@qq..com
 */
public class GoodDetail implements Serializable{

    /**
     * audit : 0
     * babyMerchantId : string
     * commentData : {"number":0,"score":0}
     * commodityDetail : string
     * commodityImage : string
     * createTime : 2019-01-11T03:08:41.794Z
     * discountPrice : 0
     * firstId : string
     * firstName : string
     * id : string
     * isDel : 0
     * name : string
     * originalPrice : 0
     * rejectReason : string
     * secondId : string
     * secondName : string
     * state : 0
     */

    private int audit;
    private String babyMerchantId;
    private CommentDataBean commentData;
    private String commodityDetail;
    private String commodityImage;
    private String createTime;
    private double discountPrice;
    private String firstId;
    private String firstName;
    private String id;
    private int isDel;
    private String name;
    private double originalPrice;
    private String rejectReason;
    private String secondId;
    private String secondName;
    private int state;

    public String getBabyMerchantName() {
        return babyMerchantName;
    }

    public void setBabyMerchantName(String babyMerchantName) {
        this.babyMerchantName = babyMerchantName;
    }

    private String babyMerchantName;
    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getBabyMerchantId() {
        return babyMerchantId;
    }

    public void setBabyMerchantId(String babyMerchantId) {
        this.babyMerchantId = babyMerchantId;
    }

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static class CommentDataBean implements Serializable{
        /**
         * number : 0
         * score : 0
         */

        private int number;
        private double score;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
