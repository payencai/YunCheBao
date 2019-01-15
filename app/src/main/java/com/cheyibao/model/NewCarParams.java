package com.cheyibao.model;

import com.cheyibao.list.ObservableScrollView;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/15 17:09
 * 邮箱：771548229@qq..com
 */
public class NewCarParams implements Serializable{

    /**
     * belong : string
     * carCategoryDetailId : string
     * createTime : 2019-01-15T08:57:08.675Z
     * id : string
     * param : [{"belong":"string","carCategoryDetailId":"string","createTime":"2019-01-15T08:57:08.675Z","id":"string","param":null,"paramName":"string","paramValue":"string"}]
     * paramName : string
     * paramValue : string
     */

    private String belong;
    private String carCategoryDetailId;
    private String createTime;
    private String id;
    private String paramName;
    private String paramValue;
    private List<ParamBean> param;

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
         * createTime : 2019-01-15T08:57:08.675Z
         * id : string
         * param : null
         * paramName : string
         * paramValue : string
         */
        private String parent;

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

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
