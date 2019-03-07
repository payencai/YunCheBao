package com.bbcircle.data;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/3 20:03
 * 邮箱：771548229@qq..com
 */
public class ClassItem implements Serializable{

    /**
     * className : string
     * classNo : string
     * classPrice : 0
     * createTime : 2019-01-03T12:03:10.792Z
     * id : string
     * isDel : 0
     * merchantId : string
     */

    private String className;
    private String classNo;
    private double classPrice;
    private String createTime;
    private String id;
    private int isDel;
    private String merchantId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public double getClassPrice() {
        return classPrice;
    }

    public void setClassPrice(double classPrice) {
        this.classPrice = classPrice;
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
}
