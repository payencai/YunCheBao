package com.baike.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/28 15:20
 * 邮箱：771548229@qq..com
 */
public class ClassifyWiki implements Serializable{

    /**
     * createTime : 2018-12-28T07:17:12.761Z
     * id : string
     * isDel : 0
     * name : string
     * type : 0
     */

    private String createTime;
    private String id;
    private int isDel;
    private String name;
    private int type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
