package com.entity;

import android.content.res.Configuration;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class PhoneOrderEntity {
    public PhoneOrderEntity(int type, String status) {
        this.type = type;
        this.status = status;
    }

    private int type;
    private String status;
    public int getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }
}
