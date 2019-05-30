package com.example.yunchebao.view.dialog;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.WindowManager;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;


import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ScheduledExecutorService;

public class GlobalService extends Service implements Observer {

    private Dialog mDialog;

    private ScheduledExecutorService mScheduledExecutorService;

    private Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.show();
            }
        }

        ;
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //下面的代码如果开放，就可以测试全局弹dialog功能。是每5秒弹一次。

//        if (mDialog == null) {
//            mDialog = new Dialog(getApplicationContext());
//            mDialog.setContentView(R.layout.show_dialog);
//            // 加入系统服务
//            mDialog.getWindow().setType(
//                    (WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
//            mScheduledExecutorService = Executors
//                    .newSingleThreadScheduledExecutor();
//            mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
//
//                @Override
//                public void run() {
//                    // 5秒显示一次对话框
//                    mHandler.sendEmptyMessage(0);
//                }
//            }, 5, 5, TimeUnit.SECONDS);
//
//        }

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void update(Observable observable, Object data) {
        String msg = (String) data;
        if (msg != null) {
            if (mDialog == null) {
                mDialog = new Dialog(MyApplication.getContext());
                mDialog.setContentView(R.layout.dialog_exit);
            }
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.setTitle(msg);
                // 加入系统服务
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
//                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//                } else {
//                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//                }
                //8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
                }else {
                    mDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
                }
                mDialog.show();


            }
        } else {
            if (mDialog != null) {
                mDialog.cancel();
                mDialog = null;
            }
        }


    }
}
