package com.example.yunchebao.maket.model;

/**
 * 作者：凌涛 on 2019/1/11 19:12
 * 邮箱：771548229@qq..com
 */
public class GoodsType {

    /**
     * id : ef801451-d93f-4f02-909b-f826791f95ac
     * name : 轮胎品牌
     * image : null
     * remark : 无
     * superId : 0
     * grade : 1
     * isDelete : 1
     * createTime : 2018-12-23 15:42:14
     */

    private String id;
    private String name;
    private String image;
    private String remark;
    private String superId;
    private int grade;
    private int isDelete;
    private String createTime;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
