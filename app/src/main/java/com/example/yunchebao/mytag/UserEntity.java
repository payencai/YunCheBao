package com.example.yunchebao.mytag;


import com.example.yunchebao.sidebar.indexablerv.IndexableEntity;

/**
 * Created by YoKey on 16/10/8.
 */
public class UserEntity implements IndexableEntity {
    private String name;
    private String headPortrait;
    private boolean isSelect;
    private boolean isShow;
    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    private String userId;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity(String name, String mobile) {
        this.name = name;

    }

    public String getNick() {
        return name;
    }

    public void setNick(String name) {
        this.name = name;
    }

    @Override
    public String getFieldIndexBy() {
        return name;
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField;
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
        // 需要用到拼音时(比如:搜索), 可增添pinyin字段 this.pinyin  = pinyin
        // 见 CityEntity
    }
}
