package com.example.yunchebao.maket.model;

/**
 * 作者：凌涛 on 2019/1/10 15:55
 * 邮箱：771548229@qq..com
 */
public class GoodInfoParams {

    /**
     * babyCommodityId : string
     * createTime : 2019-01-10T07:54:30.769Z
     * id : string
     * paramName : string
     * paramValue : string
     */

    private String babyCommodityId;
    private String createTime;
    private String id;
    private String paramName;
    private String paramValue;

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
