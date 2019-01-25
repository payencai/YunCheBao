package com.baike.model;

/**
 * 作者：凌涛 on 2018/12/28 16:02
 * 邮箱：771548229@qq..com
 */
public class BaikeItem {


    /**
     * id : 04755bbd-a16e-451e-bfd5-6aaa9bf3046f
     * title : 剑指牧马人等 大众或将推出电动SUV车型
     * picture : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2018122917042254
     * synopsis : 日前，有海外媒体报道称，大众正在着手研发一款全新的SUV车型，对手锁定在路虎卫士、吉普牧马人等车型，预计2022年左右上市。
     * content : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/text/4ce6683f-6e26-474f-84f5-4e800a87995e
     * classifyId : efe49eb6-c1db-4a6b-88a6-0fa0dd44d261
     * createTime : 2018-12-29 17:01:56
     * type : 1
     * isDel : 1
     * classifyName : null
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
