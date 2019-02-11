package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/11 11:12
 * 邮箱：771548229@qq..com
 */
public class GoodsCollect {

    /**
     * commodityId : string
     * commodityImage : string
     * commodityName : string
     * createTime : 2019-02-11T03:02:28.826Z
     * discountPrice : 0
     * id : string
     * originalPrice : 0
     * userId : string
     */

    private String commodityId;
    private String commodityImage;
    private String commodityName;
    private String createTime;
    private double discountPrice;
    private String id;
    private double originalPrice;
    private String userId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(String commodityImage) {
        this.commodityImage = commodityImage;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
