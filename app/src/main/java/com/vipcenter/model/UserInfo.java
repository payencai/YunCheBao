package com.vipcenter.model;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/12 17:29
 * 邮箱：771548229@qq..com
 */
public class UserInfo implements Serializable{

    /**
     * id : 688036c1-31d2-4927-90c3-29a370ed43e9
     * username : 13202908144
     * hxAccount : iB252yuGQPW8144
     * hxPassword : 7BNPGSPIQrdi3GQawPzUunXiiqzbXsWqk12gvu88n50/S9xdbyY8cwjWS+Wc/S1/SfsweHLXYlGl69dg+aVmhrGx8RlCs/hza9G2p067Ef8=
     * name : null
     * sex : 保密
     * age : 18
     * birthday : null
     * headPortrait : null
     * token : headeyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzIwMjkwODE0NCIsInJvbGUiOiJST0xFX3VzZXIiLCJjcmVhdGVkIjoxNTQ0NjA2Nzc5MDcyLCJleHAiOjE1NDY0MDY3NzksInVzZXJJZCI6IjY4ODAzNmMxLTMxZDItNDkyNy05MGMzLTI5YTM3MGVkNDNlOSJ9.fne4JqJKR25AvSKCILJudEjZLA8g7GlLJxXis8CMjDjZpD4HfjyevVG0lpm6CZg6drFqcxYLkmtHPg1IE2Bkww
     */

    private String id;
    private String username;
    private String hxAccount;
    private String hxPassword;
    private String name;
    private String sex;
    private int age;
    private String birthday;
    private String headPortrait;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHxAccount() {
        return hxAccount;
    }

    public void setHxAccount(String hxAccount) {
        this.hxAccount = hxAccount;
    }

    public String getHxPassword() {
        return hxPassword;
    }

    public void setHxPassword(String hxPassword) {
        this.hxPassword = hxPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
