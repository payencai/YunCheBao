package com.order;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/28 14:00
 * 邮箱：771548229@qq..com
 */
public class CarOrder implements Serializable{

    /**
     * carCategory : string
     * className : string
     * coachId : string
     * coachName : string
     * commodityId : string
     * createTime : 2019-01-28T03:20:43.838Z
     * handleTime : 2019-01-28T03:20:43.838Z
     * id : string
     * image : string
     * name : string
     * number : 0
     * orderNo : string
     * payMethod : 0
     * payTime : 2019-01-28T03:20:43.838Z
     * price : 0
     * remark : string
     * seat : string
     * shopId : string
     * shopName : string
     * state : 0
     * telephone : string
     * title : string
     * total : 0
     * type : 0
     * userId : string
     */
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String carCategory;
    private String className;
    private int isComment;
    private String serveCategory;
    private String serveTitle;

    public int getIsComment() {
        return isComment;
    }

    public void setIsComment(int isComment) {
        this.isComment = isComment;
    }

    public String getServeCategory() {
        return serveCategory;
    }

    public void setServeCategory(String serveCategory) {
        this.serveCategory = serveCategory;
    }

    public String getServeTitle() {
        return serveTitle;
    }

    public void setServeTitle(String serveTitle) {
        this.serveTitle = serveTitle;
    }

    private String coachId;
    private String coachName;
    private String commodityId;
    private String createTime;
    private String handleTime;
    private String id;
    private String image;
    private String name;
    private int number;
    private String orderNo;
    private int payMethod;
    private String payTime;
    private double price;
    private String remark;
    private String seat;
    private String shopId;
    private String shopName;
    private int state;
    private String telephone;
    private String title;
    private double total;
    private int type;
    private String userId;

    public String getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(String carCategory) {
        this.carCategory = carCategory;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
