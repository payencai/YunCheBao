package com.bbcircle.data;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/28 10:26
 * 邮箱：771548229@qq..com
 */
public class CircleComment {

    /**
     * circleId : 0
     * commentReplyList : [{"content":"string","createTime":"2019-02-28T01:54:16.189Z","id":"string","nickname":"string","recordId":"string"}]
     * content : string
     * createTime : 2019-02-28T01:54:16.189Z
     * headPortrait : string
     * id : string
     * name : string
     * type : 0
     * userId : string
     */

    private int circleId;
    private String content;
    private String createTime;
    private String headPortrait;
    private String id;
    private String name;
    private int type;
    private String userId;
    private List<CommentReplyListBean> commentReplyList;

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

    public List<CommentReplyListBean> getCommentReplyList() {
        return commentReplyList;
    }

    public void setCommentReplyList(List<CommentReplyListBean> commentReplyList) {
        this.commentReplyList = commentReplyList;
    }

    public static class CommentReplyListBean {
        /**
         * content : string
         * createTime : 2019-02-28T01:54:16.189Z
         * id : string
         * nickname : string
         * recordId : string
         */

        private String content;
        private String createTime;
        private String id;
        private String nickname;
        private String recordId;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }
    }
}
