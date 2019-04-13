package com.cheyibao.model;

/**
 * 作者：凌涛 on 2019/1/8 16:15
 * 邮箱：771548229@qq..com
 */
public class Area {

    /**
     * city : 邯郸市
     * name : 市辖区
     * id : 130401
     */

    private String city;
    private String name;
    private String id;
    private boolean isSelecting = false;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelecting() {
        return isSelecting;
    }

    public void setSelecting(boolean selecting) {
        isSelecting = selecting;
    }
}
