package com.example.yunchebao.yuedan.model;

import java.util.List;

public class CarCategoryDetail {

    /**
     * id : 5ffce15d-b8da-4462-a7ce-d057288e49c8
     * carCategoryId : f84e4965-9c28-49dd-aa73-311a148d556c
     * advicePrice : 270000
     * banner1 : https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052218213467,https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052218213464,https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052218213455,https://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019052218213422
     * banner2 : null
     * banner3 : null
     * displacement : 国三以上
     * fuel : 柴油
     * seat : 六座
     * remark : 规范化和
     * param : [{"id":"d5fbc0ae-c6c0-48e7-84b1-5bcb7c031dad","carCategoryDetailId":"5ffce15d-b8da-4462-a7ce-d057288e49c8","paramName":"苟富贵","paramValue":"苟富贵","belong":"N","createTime":"2019-05-22 18:21:36","param":[]}]
     * sales : 0
     * createTime : 2019-05-22 18:21:22
     * country : 法系
     * models : 两厢
     * variableBox : 手动
     */

    private String id;
    private String carCategoryId;
    private double advicePrice;
    private String banner1;
    private String banner2;
    private String banner3;
    private String displacement;
    private String fuel;
    private String seat;
    private String remark;
    private int sales;
    private String createTime;
    private String country;
    private String models;
    private String variableBox;
    private List<ParamBean> param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarCategoryId() {
        return carCategoryId;
    }

    public void setCarCategoryId(String carCategoryId) {
        this.carCategoryId = carCategoryId;
    }

    public double getAdvicePrice() {
        return advicePrice;
    }

    public void setAdvicePrice(double advicePrice) {
        this.advicePrice = advicePrice;
    }

    public String getBanner1() {
        return banner1;
    }

    public void setBanner1(String banner1) {
        this.banner1 = banner1;
    }

    public String getBanner2() {
        return banner2;
    }

    public void setBanner2(String banner2) {
        this.banner2 = banner2;
    }

    public String getBanner3() {
        return banner3;
    }

    public void setBanner3(String banner3) {
        this.banner3 = banner3;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getVariableBox() {
        return variableBox;
    }

    public void setVariableBox(String variableBox) {
        this.variableBox = variableBox;
    }

    public List<ParamBean> getParam() {
        return param;
    }

    public void setParam(List<ParamBean> param) {
        this.param = param;
    }

    public static class ParamBean {
        /**
         * id : d5fbc0ae-c6c0-48e7-84b1-5bcb7c031dad
         * carCategoryDetailId : 5ffce15d-b8da-4462-a7ce-d057288e49c8
         * paramName : 苟富贵
         * paramValue : 苟富贵
         * belong : N
         * createTime : 2019-05-22 18:21:36
         * param : []
         */

        private String id;
        private String carCategoryDetailId;
        private String paramName;
        private String paramValue;
        private String belong;
        private String createTime;
        private List<Object> param;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCarCategoryDetailId() {
            return carCategoryDetailId;
        }

        public void setCarCategoryDetailId(String carCategoryDetailId) {
            this.carCategoryDetailId = carCategoryDetailId;
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

        public String getBelong() {
            return belong;
        }

        public void setBelong(String belong) {
            this.belong = belong;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public List<Object> getParam() {
            return param;
        }

        public void setParam(List<Object> param) {
            this.param = param;
        }
    }
}
