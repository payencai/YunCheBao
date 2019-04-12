package com.common;

import android.content.Context;

import com.example.yunchebao.R;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDateTime(Date dateDate,String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern,Locale.CHINA);
        return formatter.format(dateDate);
    }

    public static String formatDateTime(long time,String pattern) {
        Date dateDate = new Date(time);
       return formatDateTime(dateDate,pattern);
    }


    public static TimePickerDialog initTimePickerDialog(Context context,OnDateSetListener listener, String title){
        return new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setCyclic(false)
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(context.getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .setWheelItemTextSize(12)
                .build();
    }

    public static TimePickerDialog initTimePickerDialog(Context context,OnDateSetListener listener, String title,long millseconds){
        return new TimePickerDialog.Builder()
                .setCallBack(listener)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId(title)
                .setCyclic(false)
                .setThemeColor(context.getResources().getColor(R.color.timepicker_dialog_bg))
                .setToolBarTextColorId(R.color.colorPrimary)
                .setThemeColor(context.getResources().getColor(R.color.colorPrimary))
                .setType(Type.ALL)
                .setWheelItemTextSize(12)
                .setSelectorMillseconds(millseconds)
                .build();
    }

}
