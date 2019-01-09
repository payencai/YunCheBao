package com.xihubao.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/3 11:19
 * 邮箱：771548229@qq..com
 */
public class CarBrand implements Serializable{

    /**
     * fistId : string
     * fistName : string
     * id : string
     * image : string
     * isDel : 0
     * level : 0
     * name : string
     * remark : string
     * secondId : string
     * secondName : string
     * thirdId : string
     * thirdName : string
     */
    private String initial;

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    /**
     * id : f4f959f1-9f8a-45ea-9ee3-113ef18662b3
     * name : 法拉类
     * fistId : f4f959f1-9f8a-45ea-9ee3-113ef18662b3
     * fistName : null
     * secondId : null
     * secondName : null
     * thirdId : null
     * thirdName : null
     * level : 1
     * image : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2018122914520780
     * remark : 木有
     * isDel : 1
     */

    private String id;
    private String name;
    private String fistId;
    private String fistName;
    private String secondId;
    private String secondName;
    private String thirdId;
    private String thirdName;
    private int level;
    private String image;
    private String remark;
    private int isDel;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFistId() {
        return fistId;
    }

    public void setFistId(String fistId) {
        this.fistId = fistId;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
