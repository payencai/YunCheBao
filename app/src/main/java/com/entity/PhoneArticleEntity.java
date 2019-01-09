package com.entity;

/**
 * Created by sdhcjhss on 2017/12/22.
 */

public class PhoneArticleEntity {

    /**
     * circleId : 0
     * id : string
     * image : string
     * isCollection : 0
     * title : string
     * type : 0
     * typeName : string
     * updateTime : 2018-12-30T05:05:38.995Z
     * userId : string
     */

    private int circleId;
    private String id;
    private String image;
    private int isCollection;
    private String title;
    private int type;
    private String typeName;
    private String updateTime;
    private String userId;

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
