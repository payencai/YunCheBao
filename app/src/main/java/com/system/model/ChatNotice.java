package com.system.model;

/**
 * 作者：凌涛 on 2019/3/7 16:00
 * 邮箱：771548229@qq..com
 */
public class ChatNotice {

    /**
     * circleId : 0
     * content : string
     * createTime : 2019-03-07T08:00:37.034Z
     * headPortrait : string
     * id : string
     * name : string
     * replyUserId : string
     * type : 0
     * userId : string
     */

    private int circleId;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private String name;
    private String replyUserId;
    private int type;
    private String userId;

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
