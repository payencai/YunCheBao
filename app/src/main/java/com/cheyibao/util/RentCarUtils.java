package com.cheyibao.util;

import android.util.SparseArray;

import java.util.HashMap;

public class RentCarUtils {


    public static final long MIN_DURATION = 28*60*60*1000; //最小时间段
    public static final long DAY = 24*60*60*1000;

    public static final String RENT_CAR_INFO_AREA_1 = "area1";
    public static final String RENT_CAR_INFO_AREA_2 = "area2";
    public static final String RENT_CAR_INFO_SHOP = "shop";
    public static final String RENT_CAR_INFO_START_TIME = "start_time";
    public static final String RENT_CAR_INFO_END_TIME = "end_time";
    public static final String RENT_CAR_INFO_CAR_MODEL = "car_model";
    public static final String RENT_CAR_INFO_IS_TO_HOME_SERVICE = "is_to_home_service";
    public static final String IS_EDIT_ORDER_ADDRESS = "is_edit_order_address";
    public static final String IS_EDIT_ORDER_TIME = "is_edit_order_time";
    public static final String IS_EDIT_ORDER_ONLY_ADDRESS = "is_edit_order_only_address";

    public static HashMap<String,Object> rentCarInfo;
    public static final int ONLINESERVE = 1;
    public static final int OFFLINESERVE = 2;

    public static int day(long duration){
        if (duration<=MIN_DURATION){
            return 1;
        }
        if ((duration-MIN_DURATION)%DAY==0){
            return (int) ((duration-MIN_DURATION)/DAY)+1;
        }
        return (int) (((duration-MIN_DURATION)/DAY) +2);
    }

    public static SparseArray<Object> sparseArray;
}
