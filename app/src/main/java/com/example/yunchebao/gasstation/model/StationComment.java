package com.example.yunchebao.gasstation.model;

/**
 * 作者：凌涛 on 2019/4/23 10:25
 * 邮箱：771548229@qq..com
 */
public class StationComment {

    /**
     * answer : string
     * answerTime : 2019-04-23T02:25:39.998Z
     * content : string
     * createTime : 2019-04-23T02:25:39.998Z
     * headPortrait : string
     * id : string
     * imgs : string
     * isDelete : 0
     * name : string
     * score : 0.0
     * shopId : string
     * state : 0
     * userId : string
     */

    private String answer;
    private String answerTime;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private String imgs;
    private int isDelete;
    private String name;
    private float score;
    private String shopId;
    private int state;
    private String userId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

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

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
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
