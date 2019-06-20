package com.example.yunchebao.maket.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/10 12:10
 * 邮箱：771548229@qq..com
 */
public class GoodMenu implements Serializable{

    /**
     * id : 1
     * name : 车用清洗
     * logo : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019010216233462
     * firstId : ef801451-d93f-4f02-909b-f826791f95ac
     * first : 一级类型
     * secondId : 31e058ec-3c3d-4ec9-952c-51a479b925a5
     * second : 二级类型
     * createTime : 2019-01-02 16:23:59
     */

    private int id=0;
    private String name;
    private String logo;
    private String firstId;
    private String first;
    private String secondId;
    private String second;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
