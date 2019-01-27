package com.system.model;

/**
 * 作者：凌涛 on 2019/1/25 10:39
 * 邮箱：771548229@qq..com
 */
public class HomeImage {


    /**
     * id : 14
     * name : null
     * image : http://yunchebao.oss-cn-shenzhen.aliyuncs.com/image/2019012618034875
     * url : baidu.com
     * type : 3
     * superId : 3
     */

    private int id;
    private String name;
    private String image;
    private String url;
    private int type;
    private int superId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSuperId() {
        return superId;
    }

    public void setSuperId(int superId) {
        this.superId = superId;
    }
}
