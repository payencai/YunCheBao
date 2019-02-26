package com.system.model;

/**
 * 作者：凌涛 on 2019/2/26 19:10
 * 邮箱：771548229@qq..com
 */
public class ShopInfo {

    /**
     * id : string
     * imgs : string
     * name : string
     * personSign : string
     * sex : string
     * ycbAccount : string
     */

    private String id;
    private String imgs;
    private String name;
    private String personSign;
    private String sex;
    private String ycbAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonSign() {
        return personSign;
    }

    public void setPersonSign(String personSign) {
        this.personSign = personSign;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYcbAccount() {
        return ycbAccount;
    }

    public void setYcbAccount(String ycbAccount) {
        this.ycbAccount = ycbAccount;
    }
}
