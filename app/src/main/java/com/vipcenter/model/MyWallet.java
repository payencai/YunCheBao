package com.vipcenter.model;

/**
 * 作者：凌涛 on 2019/2/20 10:26
 * 邮箱：771548229@qq..com
 */
public class MyWallet {

    /**
     * id : 90039d05-4b5e-4381-92a0-8346c6233afc
     * password : null
     * goldCoin : 99939999
     * balance : 0
     * memberCardUpdateTime : null
     * isHasMemberCard : 0
     * memberCardImage : null
     * total : 0
     */

    private String id;
    private String password;
    private int goldCoin;
    private double balance;
    private String memberCardUpdateTime;
    private int isHasMemberCard;
    private String memberCardImage;
    private double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(int goldCoin) {
        this.goldCoin = goldCoin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getMemberCardUpdateTime() {
        return memberCardUpdateTime;
    }

    public void setMemberCardUpdateTime(String memberCardUpdateTime) {
        this.memberCardUpdateTime = memberCardUpdateTime;
    }

    public int getIsHasMemberCard() {
        return isHasMemberCard;
    }

    public void setIsHasMemberCard(int isHasMemberCard) {
        this.isHasMemberCard = isHasMemberCard;
    }

    public String getMemberCardImage() {
        return memberCardImage;
    }

    public void setMemberCardImage(String memberCardImage) {
        this.memberCardImage = memberCardImage;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
