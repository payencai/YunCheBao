package com.example.yunchebao.gasstation.model;

/**
 * 作者：凌涛 on 2019/4/23 10:23
 * 邮箱：771548229@qq..com
 */
public class StationService {

    /**
     * content : string
     * createTime : 2019-04-23T02:22:42.375Z
     * id : string
     * price : 0.0
     * shopId : string
     * title : string
     */

    private String content;
    private String createTime;
    private String id;
    private double price;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
