package com.payencai.library.view.bottombar.entity;

public class BottomItem {
    private int pressIcon;
    private int unPressIcon;
    private String itemTitle;
    private int itemCount;
    private boolean isHasRedDot;

    public int getPressIcon() {
        return pressIcon;
    }

    public void setPressIcon(int pressIcon) {
        this.pressIcon = pressIcon;
    }

    public int getUnPressIcon() {
        return unPressIcon;
    }

    public void setUnPressIcon(int unPressIcon) {
        this.unPressIcon = unPressIcon;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public boolean isHasRedDot() {
        return isHasRedDot;
    }

    public void setHasRedDot(boolean hasRedDot) {
        isHasRedDot = hasRedDot;
    }
}
