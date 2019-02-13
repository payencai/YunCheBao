package com.xihubao.model;

/**
 * 作者：凌涛 on 2019/2/13 14:46
 * 邮箱：771548229@qq..com
 */
public class WashComment {

    /**
     * id : ab6a63ef-b009-40e5-85d7-e7108e948cf3
     * userId : 7087b41a-99f5-4da2-ac59-04ccab8835c8
     * nickName : 你是个好人
     * headPortrait : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019013010411513
     * content : 反反复复发
     * score : 3
     * createTime : 2019-02-13 14:31:30
     * answer : null
     * imgs : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019021314310588,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019021314310543,http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019021314310543
     */

    private String id;
    private String userId;
    private String nickName;
    private String headPortrait;
    private String content;
    private int score;
    private String createTime;
    private String answer;
    private String imgs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
