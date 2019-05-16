package com.example.yunchebao.rongcloud.model;

/**
 * 作者：凌涛 on 2018/12/20 14:03
 * 邮箱：771548229@qq..com
 */
public class WeekDate {
    private String date;
    private String week;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
