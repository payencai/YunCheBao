package com.example.yunchebao.yuedan.model;

/**
 * 作者：凌涛 on 2018/12/28 09:39
 * 邮箱：771548229@qq..com
 */
public class WashCarType {

    /**
     * earnestMoneyFive : 0
     * earnestMoneyFour : 0
     * earnestMoneyOne : 0
     * earnestMoneyThree : 0
     * earnestMoneyTwo : 0
     * id : 0
     * name : string
     * price : 0
     * type : 0
     */

    private double earnestMoneyFive;
    private double earnestMoneyFour;
    private double earnestMoneyOne;
    private double earnestMoneyThree;
    private double earnestMoneyTwo;
    private int id;
    private String name;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;

    public double getEarnestMoneyFive() {
        return earnestMoneyFive;
    }

    public void setEarnestMoneyFive(double earnestMoneyFive) {
        this.earnestMoneyFive = earnestMoneyFive;
    }

    public double getEarnestMoneyFour() {
        return earnestMoneyFour;
    }

    public void setEarnestMoneyFour(double earnestMoneyFour) {
        this.earnestMoneyFour = earnestMoneyFour;
    }

    public double getEarnestMoneyOne() {
        return earnestMoneyOne;
    }

    public void setEarnestMoneyOne(double earnestMoneyOne) {
        this.earnestMoneyOne = earnestMoneyOne;
    }

    public double getEarnestMoneyThree() {
        return earnestMoneyThree;
    }

    public void setEarnestMoneyThree(double earnestMoneyThree) {
        this.earnestMoneyThree = earnestMoneyThree;
    }

    public double getEarnestMoneyTwo() {
        return earnestMoneyTwo;
    }

    public void setEarnestMoneyTwo(double earnestMoneyTwo) {
        this.earnestMoneyTwo = earnestMoneyTwo;
    }

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
}
