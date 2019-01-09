package com.entity;

/**
 * 作者：凌涛 on 2018/12/22 09:48
 * 邮箱：771548229@qq..com
 */
public class Banner {

    /**
     * id : 0
     * picture : string
     * skipUrl : string
     * title : string
     * type : 0
     */

    private int id;
    private String picture;
    private String skipUrl;
    private String title;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
