package com.bbcircle.data;

import java.util.List;

/**
 * 作者：凌涛 on 2018/12/29 14:59
 * 邮箱：771548229@qq..com
 */
public class SeldDrvingDetail {


    /**
     * address : string
     * age : 0
     * commentNum : 0
     * content : string
     * createTime : 2019-05-22T10:46:28.179Z
     * endTime : 2019-05-22T10:46:28.179Z
     * enterNum : 0
     * headPortrait : string
     * id : 0
     * image : string
     * isCollection : 0
     * isEnter : 0
     * list : [{"createTime":"2019-05-22T10:46:28.179Z","headPortrait":"string","id":"string","name":"string","nickname":"string","recordId":0,"telephone":"string","userId":"string"}]
     * name : string
     * readNum : 0
     * sex : string
     * startTime : 2019-05-22T10:46:28.179Z
     * telephone : string
     * title : string
     * userId : string
     */

    private String address;
    private int age;
    private int commentNum;
    private String content;
    private String createTime;
    private String endTime;
    private int enterNum;
    private String headPortrait;
    private int id;
    private String image;
    private int isCollection;
    private int isEnter;
    private String name;
    private int readNum;
    private String sex;
    private String startTime;
    private String telephone;
    private String title;
    private String userId;
    private List<ListBean> list;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getEnterNum() {
        return enterNum;
    }

    public void setEnterNum(int enterNum) {
        this.enterNum = enterNum;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }

    public int getIsEnter() {
        return isEnter;
    }

    public void setIsEnter(int isEnter) {
        this.isEnter = isEnter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * createTime : 2019-05-22T10:46:28.179Z
         * headPortrait : string
         * id : string
         * name : string
         * nickname : string
         * recordId : 0
         * telephone : string
         * userId : string
         */

        private String createTime;
        private String headPortrait;
        private String id;
        private String name;
        private String nickname;
        private int recordId;
        private String telephone;
        private String userId;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
