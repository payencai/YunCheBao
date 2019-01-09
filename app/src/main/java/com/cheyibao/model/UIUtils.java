package com.cheyibao.model;

import android.content.Context;

/**
 * 作者：凌涛 on 2019/1/7 11:17
 * 邮箱：771548229@qq..com
 */

public class UIUtils {
    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
