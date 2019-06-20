package com.example.yunchebao.maket.model;

/**
 * 作者：凌涛 on 2019/1/11 16:13
 * 邮箱：771548229@qq..com
 */
public class GoodsComment {

    /**
     * answer : string
     * content : string
     * createTime : 2019-01-11T08:11:45.309Z
     * headPortrait : string
     * id : string
     * imgs : string
     * isRealName : 0
     * nickName : string
     * score : 0
     * userId : string
     */

    private String answer;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private String imgs;
    private int isRealName;
    private String nickName;
    private float score;
    private String userId;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public int getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(int isRealName) {
        this.isRealName = isRealName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
