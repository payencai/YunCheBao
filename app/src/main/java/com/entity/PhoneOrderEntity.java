package com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class PhoneOrderEntity implements Serializable{
    /**
     * address : string
     * createTime : 2019-01-09T16:38:09.921Z
     * deliveryTime : 2019-01-09T16:38:09.921Z
     * expressCompany : string
     * expressCompanyNo : string
     * expressNo : string
     * finishTime : 2019-01-09T16:38:09.921Z
     * freight : 0
     * id : string
     * isComment : 0
     * itemList : [{"commodityId":"string","commodityImage":"string","commodityName":"string","firstSpecificationName":"string","firstSpecificationValue":"string","handleTime":"2019-01-09T16:38:09.921Z","id":"string","number":0,"orderId":"string","price":0,"refuseCause":"string","refuseImg":"string","refusePrice":0,"refuseReason":"string","refuseState":0,"refuseTime":"2019-01-09T16:38:09.921Z","secondSpecificationName":"string","secondSpecificationValue":"string"}]
     * merchTelephone : string
     * name : string
     * number : 0
     * orderNo : string
     * payMethod : 0
     * payTime : 2019-01-09T16:38:09.921Z
     * remark : string
     * shopId : string
     * shopName : string
     * state : 0
     * telephone : string
     * total : 0
     * userId : string
     */

    private String address;
    private String createTime;
    private String deliveryTime;
    private String expressCompany;
    private String expressCompanyNo;
    private String expressNo;
    private String finishTime;
    private int freight;
    private String id;
    private int isComment;
    private String merchTelephone;
    private String name;
    private int number;
    private String orderNo;
    private int payMethod;
    private String payTime;
    private String remark;
    private String shopId;
    private String shopName;
    private int state;
    private String telephone;
    private double total;
    private String userId;
    private List<ItemListBean> itemList;

//    public PhoneOrderEntity(int type, String status) {
//        this.type = type;
//        this.status = status;
//    }

    private int type;
    private String status;
    public int getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressCompanyNo() {
        return expressCompanyNo;
    }

    public void setExpressCompanyNo(String expressCompanyNo) {
        this.expressCompanyNo = expressCompanyNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public String getMerchTelephone() {
        return merchTelephone;
    }

    public void setMerchTelephone(String merchTelephone) {
        this.merchTelephone = merchTelephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ItemListBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemListBean> itemList) {
        this.itemList = itemList;
    }

    public static class ItemListBean implements Serializable{
        /**
         * commodityId : string
         * commodityImage : string
         * commodityName : string
         * firstSpecificationName : string
         * firstSpecificationValue : string
         * handleTime : 2019-01-09T16:38:09.921Z
         * id : string
         * number : 0
         * orderId : string
         * price : 0
         * refuseCause : string
         * refuseImg : string
         * refusePrice : 0
         * refuseReason : string
         * refuseState : 0
         * refuseTime : 2019-01-09T16:38:09.921Z
         * secondSpecificationName : string
         * secondSpecificationValue : string
         */
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        private String commodityId;
        private String commodityImage;
        private String commodityName;
        private String firstSpecificationName;
        private String firstSpecificationValue;
        private String handleTime;
        private String id;
        private int number;
        private String orderId;
        private double price;
        private String refuseCause;
        private String refuseImg;
        private double refusePrice;
        private String refuseReason;
        private int refuseState;
        private String refuseTime;
        private String secondSpecificationName;
        private String secondSpecificationValue;
        private String imgs;
        private String content;
        private double score;
        private int isRealName=1;

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getIsRealName() {
            return isRealName;
        }

        public void setIsRealName(int isRealName) {
            this.isRealName = isRealName;
        }

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

        public String getFirstSpecificationName() {
            return firstSpecificationName;
        }

        public void setFirstSpecificationName(String firstSpecificationName) {
            this.firstSpecificationName = firstSpecificationName;
        }

        public String getFirstSpecificationValue() {
            return firstSpecificationValue;
        }

        public void setFirstSpecificationValue(String firstSpecificationValue) {
            this.firstSpecificationValue = firstSpecificationValue;
        }

        public String getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(String handleTime) {
            this.handleTime = handleTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getRefuseCause() {
            return refuseCause;
        }

        public void setRefuseCause(String refuseCause) {
            this.refuseCause = refuseCause;
        }

        public String getRefuseImg() {
            return refuseImg;
        }

        public void setRefuseImg(String refuseImg) {
            this.refuseImg = refuseImg;
        }

        public double getRefusePrice() {
            return refusePrice;
        }

        public void setRefusePrice(double refusePrice) {
            this.refusePrice = refusePrice;
        }

        public String getRefuseReason() {
            return refuseReason;
        }

        public void setRefuseReason(String refuseReason) {
            this.refuseReason = refuseReason;
        }

        public int getRefuseState() {
            return refuseState;
        }

        public void setRefuseState(int refuseState) {
            this.refuseState = refuseState;
        }

        public String getRefuseTime() {
            return refuseTime;
        }

        public void setRefuseTime(String refuseTime) {
            this.refuseTime = refuseTime;
        }

        public String getSecondSpecificationName() {
            return secondSpecificationName;
        }

        public void setSecondSpecificationName(String secondSpecificationName) {
            this.secondSpecificationName = secondSpecificationName;
        }

        public String getSecondSpecificationValue() {
            return secondSpecificationValue;
        }

        public void setSecondSpecificationValue(String secondSpecificationValue) {
            this.secondSpecificationValue = secondSpecificationValue;
        }
    }
}
