package com.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class DialPhoneUtils {

    /**
     * //跳转到拨号界面
     * @param context
     * @param telePhone
     */
    public static void startDialNumber(Context context,String telePhone){
        Uri uri = Uri.parse("tel:" + telePhone);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    /**
     * 直接拨打电话
     * @param context
     * @param telePhone
     */
    public static void startCallPhone(Context context,String telePhone){
        Uri uri1 = Uri.parse("tel:"+telePhone);
        Intent intent1 = new Intent(Intent.ACTION_CALL,uri1);
        context.startActivity(intent1);
    }
}
