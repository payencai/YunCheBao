package com.comment;

/**
 * 作者：凌涛 on 2019/2/16 11:44
 * 邮箱：771548229@qq..com
 */
public class Comment {
    private int score;
    private int isRealName;
    private String content;
    private String imgs;
    private String orderItemId;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(int isRealName) {
        this.isRealName = isRealName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
}
