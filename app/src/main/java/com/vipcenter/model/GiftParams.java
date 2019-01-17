package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/1/17 19:06
 * 邮箱：771548229@qq..com
 */
public class GiftParams {

    /**
     * createTime : 2019-01-17T10:19:28.575Z
     * giftCommodityId : string
     * id : string
     * paramName : string
     * paramValue : string
     */

    private String createTime;
    private String giftCommodityId;
    private String id;
    private String paramName;
    private String paramValue;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGiftCommodityId() {
        return giftCommodityId;
    }

    public void setGiftCommodityId(String giftCommodityId) {
        this.giftCommodityId = giftCommodityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
