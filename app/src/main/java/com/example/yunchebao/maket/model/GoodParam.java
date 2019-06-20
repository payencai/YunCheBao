package com.example.yunchebao.maket.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/11 10:03
 * 邮箱：771548229@qq..com
 */
public class GoodParam implements Serializable{

    /**
     * babyCommodityId : string
     * createTime : 2019-01-11T02:01:16.519Z
     * id : string
     * secondSpecifications : [{"comInventory":0,"createTime":"2019-01-11T02:01:16.519Z","firstSpecificationsId":"string","id":"string","price":0,"specificationsName":"string","specificationsValue":"string"}]
     * specificationsName : string
     * specificationsValue : string
     */

    private String babyCommodityId;
    private String createTime;
    private String id;
    private String specificationsName;
    private String specificationsValue;
    private List<SecondSpecificationsBean> secondSpecifications;

    public String getBabyCommodityId() {
        return babyCommodityId;
    }

    public void setBabyCommodityId(String babyCommodityId) {
        this.babyCommodityId = babyCommodityId;
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

    public String getSpecificationsName() {
        return specificationsName;
    }

    public void setSpecificationsName(String specificationsName) {
        this.specificationsName = specificationsName;
    }

    public String getSpecificationsValue() {
        return specificationsValue;
    }

    public void setSpecificationsValue(String specificationsValue) {
        this.specificationsValue = specificationsValue;
    }

    public List<SecondSpecificationsBean> getSecondSpecifications() {
        return secondSpecifications;
    }

    public void setSecondSpecifications(List<SecondSpecificationsBean> secondSpecifications) {
        this.secondSpecifications = secondSpecifications;
    }

    public static class SecondSpecificationsBean implements Serializable{
        /**
         * comInventory : 0
         * createTime : 2019-01-11T02:01:16.519Z
         * firstSpecificationsId : string
         * id : string
         * price : 0
         * specificationsName : string
         * specificationsValue : string
         */

        private int comInventory;
        private String createTime;
        private String firstSpecificationsId;
        private String id;
        private double price;
        private String specificationsName;
        private String specificationsValue;

        public int getComInventory() {
            return comInventory;
        }

        public void setComInventory(int comInventory) {
            this.comInventory = comInventory;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getFirstSpecificationsId() {
            return firstSpecificationsId;
        }

        public void setFirstSpecificationsId(String firstSpecificationsId) {
            this.firstSpecificationsId = firstSpecificationsId;
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

        public String getSpecificationsName() {
            return specificationsName;
        }

        public void setSpecificationsName(String specificationsName) {
            this.specificationsName = specificationsName;
        }

        public String getSpecificationsValue() {
            return specificationsValue;
        }

        public void setSpecificationsValue(String specificationsValue) {
            this.specificationsValue = specificationsValue;
        }
    }
}
