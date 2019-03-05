package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/3/5 15:34
 * 邮箱：771548229@qq..com
 */
public class CoinRlue {

    /**
     * id : 1
     * conditions : 成功分享微信/QQ
     * coin : 20
     * createTime : 2019-01-09 12:00:20
     * isUsing : 1
     */

    private int id;
    private String conditions;
    private int coin;
    private String createTime;
    private int isUsing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(int isUsing) {
        this.isUsing = isUsing;
    }
}
