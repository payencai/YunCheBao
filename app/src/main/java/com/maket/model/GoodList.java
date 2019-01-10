package com.maket.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/10 14:42
 * 邮箱：771548229@qq..com
 */
public class GoodList implements Serializable{

    /**
     * id : 4d00db2c-b098-4120-a636-a772c47a3bf4
     * name : 测试商品
     * commodityImage : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2018122910424210
     * firstId : ef801451-d93f-4f02-909b-f826791f95ac
     * secondId : 31e058ec-3c3d-4ec9-952c-51a479b925a5
     * firstName : null
     * secondName : null
     * originalPrice : 2
     * discountPrice : 1
     * babyMerchantId : e4120ac6-7891-46da-85fc-976f4c758413
     * commodityDetail : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/text/98905ad7-75c8-45d6-a807-b630bf04f72d
     * state : 1
     * audit : 2
     * isDel : 1
     * createTime : 2018-12-23 15:48:22
     * rejectReason : null
     */

    private String id;
    private String name;
    private String commodityImage;
    private String firstId;
    private String secondId;
    private String firstName;
    private String secondName;
    private int originalPrice;
    private int discountPrice;
    private String babyMerchantId;
    private String commodityDetail;
    private int state;
    private int audit;
    private int isDel;
    private String createTime;
    private String rejectReason;

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

    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getBabyMerchantId() {
        return babyMerchantId;
    }

    public void setBabyMerchantId(String babyMerchantId) {
        this.babyMerchantId = babyMerchantId;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
