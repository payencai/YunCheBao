package com.vipcenter.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/9 10:58
 * 邮箱：771548229@qq..com
 */
public class Gift implements Serializable{

    /**
     * coinCount : 0
     * commodityDetail : string
     * commodityImage : string
     * commodityName : string
     * commodityNum : string
     * createTime : 2019-01-09T02:57:45.660Z
     * id : string
     * isDel : 0
     * params : [{"createTime":"2019-01-09T02:57:45.660Z","giftCommodityId":"string","id":"string","paramName":"string","paramValue":"string"}]
     * state : 0
     */
    private int height;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int coinCount;
    private String commodityDetail;
    private String commodityImage;
    private String commodityName;
    private String commodityNum;
    private String createTime;
    private String id;
    private int isDel;
    private int state;
    private List<ParamsBean> params;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
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

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(String commodityNum) {
        this.commodityNum = commodityNum;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ParamsBean> getParams() {
        return params;
    }

    public void setParams(List<ParamsBean> params) {
        this.params = params;
    }

    public static class ParamsBean implements Serializable{
        /**
         * createTime : 2019-01-09T02:57:45.660Z
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
}
