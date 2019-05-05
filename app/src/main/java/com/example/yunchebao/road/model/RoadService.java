package com.example.yunchebao.road.model;

/**
 * 作者：凌涛 on 2019/4/29 17:38
 * 邮箱：771548229@qq..com
 */
public class RoadService {

    /**
     * content : string
     * createTime : 2019-04-29T09:37:36.430Z
     * id : string
     * shopId : string
     * title : string
     */

    private String content;
    private String createTime;
    private String id;
    private String shopId;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
