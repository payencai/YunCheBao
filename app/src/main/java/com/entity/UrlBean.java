package com.entity;

/**
 * 作者：凌涛 on 2018/12/21 13:53
 * 邮箱：771548229@qq..com
 */
public class UrlBean {

    /**
     * id : 0
     * image : string
     * name : string
     * type : 0
     * url : string
     */

    private int id;
    private String image;
    private String name;
    private int type;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
