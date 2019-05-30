package com.newversion;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/29 14:12
 * 邮箱：771548229@qq..com
 */
public class NewTag implements Serializable {


    /**
     * createTime : 2019-01-29T06:33:36.923Z
     * id : 0
     * list : [{"headPortrait":"string","hxAccount":"string","name":"string","nickName":"string","userId":"string"}]
     * name : string
     * userId : string
     */

    private String createTime;
    private int id;
    private String name;
    private String userId;
    private List<ListBean> list;

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    private boolean isBack;
    private boolean isChecked;//条目是否被选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * headPortrait : string
         * hxAccount : string
         * name : string
         * nickName : string
         * userId : string
         */

        private String headPortrait;
        private String hxAccount;
        private String name;
        private String nickName;
        private String userId;

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getHxAccount() {
            return hxAccount;
        }

        public void setHxAccount(String hxAccount) {
            this.hxAccount = hxAccount;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
