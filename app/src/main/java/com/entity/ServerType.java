package com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/12/21 16:36
 * 邮箱：771548229@qq..com
 */
public class ServerType implements Serializable{

    /**
     * categoryName : string
     * serveList : [{"categoryName":"string","content":"string","createTime":"2018-12-21T08:27:04.149Z","id":"string","price":0,"shopId":"string","startTime":"2018-12-21T08:27:04.149Z","state":0,"title":"string"}]
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

    public static class ServeListBean implements Serializable{
        /**
         * categoryName : string
         * content : string
         * createTime : 2018-12-21T08:27:04.149Z
         * id : string
         * price : 0
         * shopId : string
         * startTime : 2018-12-21T08:27:04.149Z
         * state : 0
         * title : string
         */

        private String categoryName;
        private String content;
        private String createTime;
        private String id;
        private double price;
        private String shopId;
        private String startTime;
        private int state;
        private String title;

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
