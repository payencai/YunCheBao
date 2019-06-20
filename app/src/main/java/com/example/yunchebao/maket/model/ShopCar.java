package com.example.yunchebao.maket.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/12 11:25
 * 邮箱：771548229@qq..com
 */
public class ShopCar implements Serializable{

    /**
     * shopId : string
     * shopName : string
     * shoppingCarList : [{"commodityId":"string","commodityImage":"string","commodityName":"string","firstSpecificationId":"string","firstSpecificationName":"string","firstSpecificationValue":"string","id":"string","isExists":"string","number":0,"price":0,"secondSpecificationId":"string","secondSpecificationName":"string","secondSpecificationValue":"string","shopId":"string","shopName":"string"}]
     */

    private String shopId;
    private String shopName;
    private List<ShoppingCarListBean> shoppingCarList;

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

    public List<ShoppingCarListBean> getShoppingCarList() {
        return shoppingCarList;
    }

    public void setShoppingCarList(List<ShoppingCarListBean> shoppingCarList) {
        this.shoppingCarList = shoppingCarList;
    }

    public static class ShoppingCarListBean implements Serializable{
        /**
         * commodityId : string
         * commodityImage : string
         * commodityName : string
         * firstSpecificationId : string
         * firstSpecificationName : string
         * firstSpecificationValue : string
         * id : string
         * isExists : string
         * number : 0
         * price : 0
         * secondSpecificationId : string
         * secondSpecificationName : string
         * secondSpecificationValue : string
         * shopId : string
         * shopName : string
         */

        private String commodityId;
        private String commodityImage;
        private String commodityName;
        private String firstSpecificationId;
        private String firstSpecificationName;
        private String firstSpecificationValue;
        private String secondSpecificationId;
        private String secondSpecificationName;
        private String secondSpecificationValue;
        private String id;
        private String isExists;
        private int number;
        private int price;
        private String shopId;
        private String shopName;

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

        public String getFirstSpecificationId() {
            return firstSpecificationId;
        }

        public void setFirstSpecificationId(String firstSpecificationId) {
            this.firstSpecificationId = firstSpecificationId;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsExists() {
            return isExists;
        }

        public void setIsExists(String isExists) {
            this.isExists = isExists;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getSecondSpecificationId() {
            return secondSpecificationId;
        }

        public void setSecondSpecificationId(String secondSpecificationId) {
            this.secondSpecificationId = secondSpecificationId;
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
    }
}
