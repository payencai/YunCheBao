package com.example.yunchebao.wxapi;

import com.google.gson.annotations.SerializedName;

/**
 * 作者：凌涛 on 2019/3/19 14:44
 * 邮箱：771548229@qq..com
 */
public class WechatRes {

    /**
     * package : Sign=WXPay
     * appid : wx13acff5b460a0164
     * sign : E5F47A91CB2BB086BEA95DA62F48310A
     * partnerid : 1527437231
     * prepayid : wx1914451773575257aa5dcfcf2059744124
     * noncestr : umanz96fMzO8K5vBN4WPkegiMdbtYUMU
     * timestamp : 1552977917
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
