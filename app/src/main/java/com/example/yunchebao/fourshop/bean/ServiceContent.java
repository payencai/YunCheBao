package com.example.yunchebao.fourshop.bean;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/18 15:08
 * 邮箱：771548229@qq..com
 */
public class ServiceContent {

    /**
     * categoryName : string
     * serveList : [{"createTime":"2019-04-18T07:07:49.051Z","firstContent":"string","firstName":"string","firstServiceCategoryId":"string","id":"string","price":0,"secondContent":"string","secondName":"string","secondServiceCategoryId":"string","shopId":"string"}]
     */

    private String categoryName;
    private List<ServeListBean> serveList;

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

    public static class ServeListBean {
        /**
         * createTime : 2019-04-18T07:07:49.051Z
         * firstContent : string
         * firstName : string
         * firstServiceCategoryId : string
         * id : string
         * price : 0.0
         * secondContent : string
         * secondName : string
         * secondServiceCategoryId : string
         * shopId : string
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
    }
}
