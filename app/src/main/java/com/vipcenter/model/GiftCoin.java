package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/1/9 15:43
 * 邮箱：771548229@qq..com
 */
public class GiftCoin {

    /**
     * coinCount : 0
     * contents : string
     * createTime : 2019-01-09T07:42:35.064Z
     * id : string
     * state : 0
     * userId : string
     */

    private int coinCount;
    private String contents;
    private String createTime;
    private String id;
    private int state;
    private String userId;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
