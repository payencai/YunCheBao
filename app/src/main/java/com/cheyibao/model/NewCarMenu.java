package com.cheyibao.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/14 14:49
 * 邮箱：771548229@qq..com
 */
public class NewCarMenu implements Serializable{

    /**
     * id : 1
     * name : BYD
     * logo : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019011117254453
     * firstId : ac0135c8-c92f-474c-94f3-64f1e2220878
     * firstName : BYD
     * createTime : 2019-01-10 15:45:00
     * type : 1
     */

    private int id;
    private String name;
    private String logo;
    private String firstId;
    private String firstName;
    private String createTime;
    private int type;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
