package com.bbcircle.data;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/28 17:52
 * 邮箱：771548229@qq..com
 */
public class SelfDrive implements Serializable{
/*
* {
        "address": "string",
        "circleName": "string",
        "commentNum": 0,
        "content": "string",
        "createTime": "2018-12-28T10:36:54.818Z",
        "headPortrait": "string",
        "id": 0,
        "image": "string",
        "name": "string",
        "readNum": 0,
        "telephone": "string",
        "title": "string",
        "userId": "string"
      }
* */
    /**
     * address : string
     * commentNum : 0
     * content : string
     * createTime : 2018-12-28T09:51:31.591Z
     * endTime : 2018-12-28T09:51:31.591Z
     * headPortrait : string
     * id : 0
     * image : string
     * name : string
     * readNum : 0
     * startTime : 2018-12-28T09:51:31.591Z
     * telephone : string
     * title : string
     * userId : string
     */

    private String address;
    private int commentNum;
    private String content;
    private String createTime;
    private String endTime;
    private String headPortrait;
    private int id;
    private int enterNum;

    public int getEnterNum() {
        return enterNum;
    }

    public void setEnterNum(int enterNum) {
        this.enterNum = enterNum;
    }

    private String image;
    private String name;
    private int readNum;
    private String startTime;
    private String telephone;
    private String title;
    private String userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
