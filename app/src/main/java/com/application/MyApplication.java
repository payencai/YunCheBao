package com.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;

import com.entity.PhoneUserEntity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.http.HttpProxy;
import com.http.processor.OkHttpProcessor;

import com.nohttp.Logger;
import com.nohttp.NoHttp;
import com.rongcloud.adapter.ListDataSave;
import com.rongcloud.sidebar.ContactModel;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.tauth.Tencent;
import com.tool.ExceptionHandler;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.vipcenter.model.UserInfo;


import org.litepal.LitePal;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserData;


import static android.provider.UserDictionary.Words.APP_ID;
import static io.rong.imkit.utils.SystemUtils.getCurProcessName;


/**
 * Created by pengying on 2017/3/9.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private static Context context;
    private static UserInfo sUserInfo;
    private static List<ContactModel> sUserInfos;
    private static AMapLocation aMapLocation;
    public static String token;
    public static List<ContactModel> getUserInfos() {
        return sUserInfos;
    }


    public static AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public static void setaMapLocation(AMapLocation aMapLocation) {
        MyApplication.aMapLocation = aMapLocation;
    }

    public static void setUserInfos(List<ContactModel> userInfos) {
        sUserInfos = userInfos;
    }
    private  static ListDataSave dataSave ;

    public static ListDataSave getDataSave() {
        return dataSave;
    }

    public static void setDataSave(ListDataSave listDataSave) {
        MyApplication.dataSave = listDataSave;;
    }

    private PhoneUserEntity user;
    public static boolean isLogin;

    private static final String TAG = "MyApplication--->>>";

    public static UserInfo getUserInfo() {
        return sUserInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        sUserInfo = userInfo;
    }

    public static boolean isIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        MyApplication.isLogin = isLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
        }
        context = getApplicationContext();
        instance = this;
        user = new PhoneUserEntity();
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        LitePal.initialize(this);
        // 系统崩溃处理
       // initCrashHandler();
        //初始化Nohttp
        initNohttp();
        //初始化Fresco
        initFresco();
        //初始化EaseUI

        HttpProxy.init(new OkHttpProcessor());
        ZXingLibrary.initDisplayOpinion(this);
    }




    private void initCrashHandler() { // 系统崩溃处理
        ExceptionHandler crashHandler = ExceptionHandler.getInstance();
        crashHandler.init(this);
    }

    //初始化Fresco
    private void initFresco() {
        Fresco.initialize(this);
    }

    //初始化Nohttp
    private void initNohttp() {
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。
        NoHttp.initialize(this);

    }
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public PhoneUserEntity getUser() {
        return user;
    }

    public void setUser(PhoneUserEntity user) {
        this.user = user;
    }
}
