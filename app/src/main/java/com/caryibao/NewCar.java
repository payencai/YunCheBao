package com.caryibao;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2018/12/26 14:35
 * 邮箱：771548229@qq..com
 */
public class NewCar implements Serializable{

    /**
     * advicePrice : 0
     * carCategoryDetail : {"advicePrice":0,"banner1":"string","banner2":"string","banner3":"string","carCategoryId":"string","country":"string","createTime":"2018-12-26T06:34:20.318Z","displacement":"string","fuel":"string","id":"string","models":"string","param":[{"belong":"string","carCategoryDetailId":"string","createTime":"2018-12-26T06:34:20.318Z","id":"string","param":[null],"paramName":"string","paramValue":"string"}],"remark":"string","sales":0,"seat":"string","variableBox":"string"}
     * carCategoryDetailId : string
     * color : string
     * createTime : 2018-12-26T06:34:20.318Z
     * firstId : string
     * firstName : string
     * id : string
     * isDel : 0
     * merchantId : string
     * minPrice : 0
     * nakedCarPrice : 0
     * sales : 0
     * secondId : string
     * secondName : string
     * state : 0
     * thirdId : string
     * thirdName : string
     */

    private double advicePrice;
    private CarCategoryDetailBean carCategoryDetail;
    private String carCategoryDetailId;
    private String color;
    private String createTime;
    private String firstId;
    private String firstName;
    private String id;
    private int isDel;
    private String merchantId;
    private double minPrice;
    private double nakedCarPrice;
    private int sales;
    private String secondId;
    private String secondName;
    private int state;
    private String thirdId;
    private String thirdName;

    public double getAdvicePrice() {
        return advicePrice;
    }

    public void setAdvicePrice(double advicePrice) {
        this.advicePrice = advicePrice;
    }

    public CarCategoryDetailBean getCarCategoryDetail() {
        return carCategoryDetail;
    }

    public void setCarCategoryDetail(CarCategoryDetailBean carCategoryDetail) {
        this.carCategoryDetail = carCategoryDetail;
    }

    public String getCarCategoryDetailId() {
        return carCategoryDetailId;
    }

    public void setCarCategoryDetailId(String carCategoryDetailId) {
        this.carCategoryDetailId = carCategoryDetailId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getNakedCarPrice() {
        return nakedCarPrice;
    }

    public void setNakedCarPrice(double nakedCarPrice) {
        this.nakedCarPrice = nakedCarPrice;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public static class CarCategoryDetailBean implements Serializable{
        /**
         * advicePrice : 0
         * banner1 : string
         * banner2 : string
         * banner3 : string
         * carCategoryId : string
         * country : string
         * createTime : 2018-12-26T06:34:20.318Z
         * displacement : string
         * fuel : string
         * id : string
         * models : string
         * param : [{"belong":"string","carCategoryDetailId":"string","createTime":"2018-12-26T06:34:20.318Z","id":"string","param":[null],"paramName":"string","paramValue":"string"}]
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

        public static class ParamBean implements Serializable{
            /**
             * belong : string
             * carCategoryDetailId : string
             * createTime : 2018-12-26T06:34:20.318Z
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
            private List<Object> param;

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

            public List<Object> getParam() {
                return param;
            }

            public void setParam(List<Object> param) {
                this.param = param;
            }
        }
    }
}
