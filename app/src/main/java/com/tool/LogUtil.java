package com.tool;

import android.util.Log;

public class LogUtil {

    private static final boolean isLog = true;


    public static final void Log(String tag, String content) {
        if (isLog) {
            Log.d(tag, "Log: " + content);
        }
    }

    public static final void Log(String content) {
        if (isLog) {
            Log.d("lingtao", "Log: " + content);
        }
    }
}
