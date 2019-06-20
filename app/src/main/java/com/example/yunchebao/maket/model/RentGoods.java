package com.example.yunchebao.maket.model;

/**
 * 作者：凌涛 on 2019/3/5 18:15
 * 邮箱：771548229@qq..com
 */
public class RentGoods {

    /**
     * id : 12
     * name : 阿斯顿撒
     * logo : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019022518283742
     * firstId : aabd7f38-fb3e-4a38-b4b0-1ee26f27a172
     * first : 保养品牌区
     * secondId : 5402fcc2-651f-4264-99e7-3a3b445a52b7
     * second : 美孚
     * createTime : 2019-02-25 18:28:39
     * type : 2
     */

    private int id;
    private String name;
    private String logo;
    private String firstId;
    private String first;
    private String secondId;
    private String second;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
