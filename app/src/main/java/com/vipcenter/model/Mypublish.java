package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/1/26 18:09
 * 邮箱：771548229@qq..com
 */
public class Mypublish {

    /**
     * createTime : 2019-01-26T10:06:00.292Z
     * id : 0
     * image : string
     * title : string
     * type : 0
     */

    private String createTime;
    private int id;
    private String image;
    private String title;
    private int type;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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
