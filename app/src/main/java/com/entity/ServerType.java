package com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/12/21 16:36
 * 邮箱：771548229@qq..com
 */
public class ServerType implements Serializable{


    /**
     * categoryId : string
     * categoryName : string
     * serveList : [{"createTime":"2019-03-15T07:43:12.098Z","firstContent":"string","firstName":"string","firstServiceCategoryId":"string","id":"string","price":0,"secondContent":"string","secondName":"string","secondServiceCategoryId":"string","shopId":"string","type":0}]
     */

    private String categoryId;
    private String categoryName;
    private List<ServeListBean> serveList;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ServeListBean> getServeList() {
        return serveList;
    }

    public void setServeList(List<ServeListBean> serveList) {
        this.serveList = serveList;
    }

    public static class ServeListBean implements Serializable{
        /**
         * createTime : 2019-03-15T07:43:12.098Z
         * firstContent : string
         * firstName : string
         * firstServiceCategoryId : string
         * id : string
         * price : 0.0
         * secondContent : string
         * secondName : string
         * secondServiceCategoryId : string
         * shopId : string
         * type : 0
         */

        private String createTime;
        private String firstContent;
        private String firstName;
        private String firstServiceCategoryId;
        private String id;
        private double price;
        private String secondContent;
        private String secondName;
        private String secondServiceCategoryId;
        private String shopId;
        private int type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFirstContent() {
            return firstContent;
        }

        public void setFirstContent(String firstContent) {
            this.firstContent = firstContent;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getFirstServiceCategoryId() {
            return firstServiceCategoryId;
        }

        public void setFirstServiceCategoryId(String firstServiceCategoryId) {
            this.firstServiceCategoryId = firstServiceCategoryId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getSecondContent() {
            return secondContent;
        }

        public void setSecondContent(String secondContent) {
            this.secondContent = secondContent;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public String getSecondServiceCategoryId() {
            return secondServiceCategoryId;
        }

        public void setSecondServiceCategoryId(String secondServiceCategoryId) {
            this.secondServiceCategoryId = secondServiceCategoryId;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
