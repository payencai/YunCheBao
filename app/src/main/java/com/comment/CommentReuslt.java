package com.comment;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/2/16 11:14
 * 邮箱：771548229@qq..com
 */
public class CommentReuslt {
    private int score;
    private int isRealName;
    private String content;
    private String imgs="";
    private String orderItemId;
    private List<String> images=new ArrayList<>();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(int isRealName) {
        this.isRealName = isRealName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        for (int i = 0; i <images.size() ; i++) {
             imgs=imgs+","+images.get(i);
        }
        if(!TextUtils.isEmpty(imgs)){
            if(imgs.contains(","));{
                imgs=imgs.substring(1);
            }
        }
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }
}
