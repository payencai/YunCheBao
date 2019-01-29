package com.newversion;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2019/1/29 16:10
 * 邮箱：771548229@qq..com
 */
public class TagUser implements Serializable{
    String userId;
    String head;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
