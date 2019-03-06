package com.xihubao.model;

/**
 * 作者：凌涛 on 2019/3/6 11:32
 * 邮箱：771548229@qq..com
 */
public class RoadService {

    /**
     * id : 9426a052-7073-4582-8aaf-4d26a44c1bb9
     * title : 电瓶搭电
     * createTime : 2018-12-11 16:31:34
     * shopId : 0d02d7ed-889b-46eb-82ca-906a4d30ebee
     * content : 电瓶漏电
     */

    private String id;
    private String title;
    private String createTime;
    private String shopId;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
