package com.baike.model;

/**
 * 作者：凌涛 on 2018/12/28 16:02
 * 邮箱：771548229@qq..com
 */
public class BaikeItem {

    /**
     * id : 29cbde69-fbc9-477f-b7f1-502eb3a66a90
     * title : 收到w
     * picture : null
     * synopsis : 撒爱上
     * content : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/text/49ea8a44-0e06-410e-a632-223232e9cb11
     * classifyId : 7dfbb737-ec40-4957-9db5-fd1882309d3a
     * createTime : 2018-12-26 18:35:38
     * type : 1
     * isDel : 1
     * classifyName : gfh
     */

    private String id;
    private String title;
    private String picture;
    private String synopsis;
    private String content;
    private String classifyId;
    private String createTime;
    private int type;
    private int isDel;
    private String classifyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
