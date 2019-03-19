package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/19 18:10
 * 邮箱：771548229@qq..com
 */
public class MemberCard {

    /**
     * id : 67c8fa4f-dc85-4da1-857e-43acff632881
     * name : 千元面值
     * faceValue : 1000
     * price : 888
     * createTime : 2019-01-11 19:38:09
     * picture : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011116010370
     */

    private String id;
    private String name;
    private int faceValue;
    private double price;
    private String createTime;
    private String picture;

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

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int faceValue) {
        this.faceValue = faceValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
