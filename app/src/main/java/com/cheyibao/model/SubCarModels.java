package com.cheyibao.model;

import java.util.List;

public class SubCarModels {

    private List<CarModelsFirstLevel> carCategory;
    private List<ParamBeanX> param;

    public List<CarModelsFirstLevel> getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(List<CarModelsFirstLevel> carCategory) {
        this.carCategory = carCategory;
    }

    public List<ParamBeanX> getParam() {
        return param;
    }

    public void setParam(List<ParamBeanX> param) {
        this.param = param;
    }

    public static class ParamBeanX {
        /**
         * advicePrice : 0
         * banner1 : string
         * banner2 : string
         * banner3 : string
         * carCategoryId : string
         * country : string
         * createTime : 2019-04-19T02:00:29.066Z
         * displacement : string
         * fuel : string
         * id : string
         * models : string
         * param : [{"belong":"string","carCategoryDetailId":"string","createTime":"2019-04-19T02:00:29.066Z","id":"string","param":[null],"paramName":"string","paramValue":"string"}]
         * remark : string
         * sales : 0
         * seat : string
         * variableBox : string
         */

        private double advicePrice;
        private String banner1;
        private String banner2;
        private String banner3;
        private String carCategoryId;
        private String country;
        private String createTime;
        private String displacement;
        private String fuel;
        private String id;
        private String models;
        private String remark;
        private int sales;
        private String seat;
        private String variableBox;
        private List<ParamBean> param;

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

        public String getCarCategoryId() {
            return carCategoryId;
        }

        public void setCarCategoryId(String carCategoryId) {
            this.carCategoryId = carCategoryId;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModels() {
            return models;
        }

        public void setModels(String models) {
            this.models = models;
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

        public String getSeat() {
            return seat;
        }

        public void setSeat(String seat) {
            this.seat = seat;
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
             * belong : string
             * carCategoryDetailId : string
             * createTime : 2019-04-19T02:00:29.066Z
             * id : string
             * param : [null]
             * paramName : string
             * paramValue : string
             */

            private String belong;
            private String carCategoryDetailId;
            private String createTime;
            private String id;
            private String paramName;
            private String paramValue;

            public String getBelong() {
                return belong;
            }

            public void setBelong(String belong) {
                this.belong = belong;
            }

            public String getCarCategoryDetailId() {
                return carCategoryDetailId;
            }

            public void setCarCategoryDetailId(String carCategoryDetailId) {
                this.carCategoryDetailId = carCategoryDetailId;
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
}
