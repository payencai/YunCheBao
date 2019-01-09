package com.bbcircle.data;

/**
 * 作者：凌涛 on 2018/12/30 10:01
 * 邮箱：771548229@qq..com
 */
public class History {


    /**
     * createTime : 2018-12-30T03:16:19.184Z
     * id : string
     * name : string
     * title : string
     * type : 0
     */

    private String createTime;
    private String id;
    private String name;
    private String title;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
