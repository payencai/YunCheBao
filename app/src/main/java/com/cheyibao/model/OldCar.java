package com.cheyibao.model;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/5 17:06
 * 邮箱：771548229@qq..com
 */
public class OldCar implements Serializable{

    /**
     * audit : 0
     * carCategoryDetail : {"advicePrice":0,"banner1":"string","banner2":"string","banner3":"string","carCategoryId":"string","country":"string","createTime":"2019-01-05T09:06:11.080Z","displacement":"string","fuel":"string","id":"string","models":"string","param":[{"belong":"string","carCategoryDetailId":"string","createTime":"2019-01-05T09:06:11.080Z","id":"string","param":[null],"paramName":"string","paramValue":"string"}],"remark":"string","sales":0,"seat":"string","variableBox":"string"}
     * carCategoryDetailId : string
     * carDescribe : string
     * carImage : string
     * carNo : string
     * change : 0
     * color : string
     * createTime : 2019-01-05T09:06:11.080Z
     * distance : string
     * firstId : string
     * firstName : string
     * id : string
     * insuranceValidTime : 0
     * isDel : 0
     * lastValidateCar : 2019-01-05T09:06:11.080Z
     * linkman : string
     * linkmanBuyCarInvoice : string
     * linkmanDrivingLicense : string
     * linkmanRegistrationCertificate : string
     * linkmanTelephone : string
     * merchantId : string
     * newPrice : 0
     * oldPrice : 0
     * registrationAddress : string
     * registrationTime : 2019-01-05T09:06:11.080Z
     * rejectReason : string
     * secondId : string
     * secondName : string
     * state : 0
     * thirdId : string
     * thirdName : string
     * type : 0
     * userId : string
     */
    private String headPortrait;
    private String name;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int audit;
    private CarCategoryDetailBean carCategoryDetail;
    private String carCategoryDetailId;
    private String carDescribe;
    private String carImage;
    private String carNo;
    private int change;
    private String color;
    private String createTime;
    private String distance;
    private String firstId;
    private String firstName;
    private String id;
    private String insuranceValidTime;
    private int isDel;
    private String lastValidateCar;
    private String linkman;
    private String linkmanBuyCarInvoice;
    private String linkmanDrivingLicense;
    private String linkmanRegistrationCertificate;
    private String linkmanTelephone;
    private String merchantId;
    private double newPrice;
    private double oldPrice;
    private String registrationAddress;
    private String registrationTime;
    private String rejectReason;
    private String secondId;
    private String secondName;
    private int state;
    private String thirdId;
    private String thirdName;
    private int type;
    private String userId;

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
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

    public String getCarDescribe() {
        return carDescribe;
    }

    public void setCarDescribe(String carDescribe) {
        this.carDescribe = carDescribe;
    }

    public String getCarImage() {
        return carImage;
    }

    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getChange() {
        return change;
    }

    public void setChange(int change) {
        this.change = change;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
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

    public String getInsuranceValidTime() {
        return insuranceValidTime;
    }

    public void setInsuranceValidTime(String insuranceValidTime) {
        this.insuranceValidTime = insuranceValidTime;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getLastValidateCar() {
        return lastValidateCar;
    }

    public void setLastValidateCar(String lastValidateCar) {
        this.lastValidateCar = lastValidateCar;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinkmanBuyCarInvoice() {
        return linkmanBuyCarInvoice;
    }

    public void setLinkmanBuyCarInvoice(String linkmanBuyCarInvoice) {
        this.linkmanBuyCarInvoice = linkmanBuyCarInvoice;
    }

    public String getLinkmanDrivingLicense() {
        return linkmanDrivingLicense;
    }

    public void setLinkmanDrivingLicense(String linkmanDrivingLicense) {
        this.linkmanDrivingLicense = linkmanDrivingLicense;
    }

    public String getLinkmanRegistrationCertificate() {
        return linkmanRegistrationCertificate;
    }

    public void setLinkmanRegistrationCertificate(String linkmanRegistrationCertificate) {
        this.linkmanRegistrationCertificate = linkmanRegistrationCertificate;
    }

    public String getLinkmanTelephone() {
        return linkmanTelephone;
    }

    public void setLinkmanTelephone(String linkmanTelephone) {
        this.linkmanTelephone = linkmanTelephone;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public static class CarCategoryDetailBean implements Serializable{
        /**
         * advicePrice : 0
         * banner1 : string
         * banner2 : string
         * banner3 : string
         * carCategoryId : string
         * country : string
         * createTime : 2019-01-05T09:06:11.080Z
         * displacement : string
         * fuel : string
         * id : string
         * models : string
         * param : [{"belong":"string","carCategoryDetailId":"string","createTime":"2019-01-05T09:06:11.080Z","id":"string","param":[null],"paramName":"string","paramValue":"string"}]
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
             * createTime : 2019-01-05T09:06:11.080Z
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
