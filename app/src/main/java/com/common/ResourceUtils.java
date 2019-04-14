package com.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class ResourceUtils {

    public static Drawable getDrawableByResource(Context context, int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resource);
        }else {
            return context.getResources().getDrawable(resource,null);
        }
    }

    public static int getColorByResource(Context context, int resource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(resource);
        } else {
            return context.getResources().getColor(resource);
        }
    }
}
