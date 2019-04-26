package com.cheyibao.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HighCarCategory implements Parcelable {
    /**
     * createTime : 2019-04-26T08:40:02.847Z
     * firstId : string
     * firstName : string
     * highCarId : string
     * id : string
     * image : string
     * name : string
     * secondId : string
     * secondName : string
     */

    private String createTime;
    private String firstId;
    private String firstName;
    private String highCarId;
    private String id;
    private String image;
    private String name;
    private String secondId;
    private String secondName;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHighCarId() {
        return highCarId;
    }

    public void setHighCarId(String highCarId) {
        this.highCarId = highCarId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.firstId);
        dest.writeString(this.firstName);
        dest.writeString(this.highCarId);
        dest.writeString(this.id);
        dest.writeString(this.image);
        dest.writeString(this.name);
        dest.writeString(this.secondId);
        dest.writeString(this.secondName);
    }

    public HighCarCategory() {
    }

    protected HighCarCategory(Parcel in) {
        this.createTime = in.readString();
        this.firstId = in.readString();
        this.firstName = in.readString();
        this.highCarId = in.readString();
        this.id = in.readString();
        this.image = in.readString();
        this.name = in.readString();
        this.secondId = in.readString();
        this.secondName = in.readString();
    }

    public static final Parcelable.Creator<HighCarCategory> CREATOR = new Parcelable.Creator<HighCarCategory>() {
        @Override
        public HighCarCategory createFromParcel(Parcel source) {
            return new HighCarCategory(source);
        }

        @Override
        public HighCarCategory[] newArray(int size) {
            return new HighCarCategory[size];
        }
    };
}
