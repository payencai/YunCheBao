package com.entity;

import android.text.TextUtils;

/**
 * 描述：
 */
public class FilterUrl {
    private volatile static FilterUrl filterUrl;

    private FilterUrl() {
    }

    public static FilterUrl instance() {
        if (filterUrl == null) {
            synchronized (FilterUrl.class) {
                if (filterUrl == null) {
                    filterUrl = new FilterUrl();
                }
            }
        }
        return filterUrl;
    }

//    public String singleListPosition;
//    public String doubleListLeft;
//    public String doubleListRight;
//    public String singleGridPosition;
    public String doubleGridTop;
    public String doubleGridBottom;

    public String areaListPosition;
    public String doubleListLeft;
    public String doubleListRight;
    public String rankListPosition;

    public int position;
    public String positionTitle;

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (!TextUtils.isEmpty(areaListPosition)) {
            buffer.append("areaListPosition=");
            buffer.append(areaListPosition);
            buffer.append("\n");
        }


        if (!TextUtils.isEmpty(doubleListLeft)) {
            buffer.append("doubleListLeft=");
            buffer.append(doubleListLeft);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleListRight)) {
            buffer.append("doubleListRight=");
            buffer.append(doubleListRight);
            buffer.append("\n");
        }


        if (!TextUtils.isEmpty(rankListPosition)) {
            buffer.append("rankListPosition=");
            buffer.append(rankListPosition);
            buffer.append("\n");
        }

//        if (!TextUtils.isEmpty(singleGridPosition)) {
//            buffer.append("singleGridPosition=");
//            buffer.append(singleGridPosition);
//            buffer.append("\n");
//        }
//
        if (!TextUtils.isEmpty(doubleGridTop)) {
            buffer.append("doubleGridTop=");
            buffer.append(doubleGridTop);
            buffer.append("\n");
        }

        if (!TextUtils.isEmpty(doubleGridBottom)) {
            buffer.append("doubleGridBottom=");
            buffer.append(doubleGridBottom);
            buffer.append("\n");
        }

        return buffer.toString();
    }

    public void clear() {
        filterUrl = null;
    }


}