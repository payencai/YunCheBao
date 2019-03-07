package com.system.model;

/**
 * 作者：凌涛 on 2019/3/7 15:59
 * 邮箱：771548229@qq..com
 */
public class CoinNotice {

    /**
     * createTime : 2019-03-07T08:00:19.757Z
     * id : string
     * title : string
     * userId : string
     */

    private String createTime;
    private String id;
    private String title;
    private String userId;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
