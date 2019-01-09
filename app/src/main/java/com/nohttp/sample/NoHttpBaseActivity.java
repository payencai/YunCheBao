package com.nohttp.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.nohttp.NoHttp;
import com.nohttp.rest.Request;
import com.nohttp.rest.RequestQueue;
import com.tool.BottomMenuDialog;

import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by yyy on 2017/5/8.
 */
public class NoHttpBaseActivity extends Activity {
    /**
     * 用来标记取消。
     */
    private Object object = new Object();
    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    private NoHttpBaseActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化请求队列，传入的参数是请求并发值。
        mContext = this;
        mQueue = NoHttp.newRequestQueue(1);
    }


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean
            isLoading) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }

    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);

        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

        super.onDestroy();
    }

    protected void cancelAll() {
        mQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        mQueue.cancelBySign(object);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, int message) {
        showMessageDialog(getText(title), getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog(getText(title), message);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, int message) {
        showMessageDialog(title, getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
//        builder.setPositiveButton(R.string.know, (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /***
     * 默认收起软键盘
     */
    public void commHiddenKeyboard(View view) {
        view.findViewById(R.id.title).setFocusable(true);
        view.findViewById(R.id.title).setFocusableInTouchMode(true);
        view.findViewById(R.id.title).requestFocus();
    }


    public void setToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 30, 30);
        toast.show();
    }


    public static void showToast(final Activity activity, final String word, final long time) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, time);
            }
        });
    }

    public static void setPoint(final EditText editText , final int decimalDigits) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > decimalDigits) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + decimalDigits+1);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    public void toastAlert(Context ctx, String error) {
        new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText(error)
                .setConfirmText("我知道了")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    public String getFormatDoubleStr(int num,String param){
        if (param.contains(".")){
            String[] readingArray = param.split(Pattern.quote("."));
            if (readingArray.length > 1){
                if (readingArray[1].length() < num){
                    param = readingArray[0]+readingArray[1]+ "0";
                }else{
                    param = readingArray[0]+readingArray[1];
                }
            }
        }else{
            for (int i = 0;i< num ;i++){
                param += "0";
            }
        }
        return param;
    }
    public String setFormatDoubleStr(int num,String param){
        if (param != null ){
            if (param.length() > num){
                param = param.substring(0,param.length()-num);
            }else if (param.length() > 0){
                param = "0."+param;
            }else {
                param = "";
            }
        }else {
            param = "";
        }
        return param;
    }

    public String readData(String data) {
        SharedPreferences pref = getSharedPreferences(PlatformContans.LoginContacts.FILENAME, MODE_MULTI_PROCESS);
        String str = pref.getString(data, "");
        return str;
    }

    public Boolean readBool(String data){
        SharedPreferences pref = getSharedPreferences(PlatformContans.LoginContacts.FILENAME, MODE_MULTI_PROCESS);
        boolean bol = pref.getBoolean(data,false);
        return false;
    }

    public boolean writeData(String userinfo, String password, boolean isUserinfo, boolean isPassword , boolean isAutoLogin, String dataSetId,String dataSetName,String companyId,String companyName) {
        SharedPreferences.Editor share_edit = getSharedPreferences(PlatformContans.LoginContacts.FILENAME,
                MODE_MULTI_PROCESS).edit();
        share_edit.putString(PlatformContans.LoginContacts.USERNAME, userinfo);
        share_edit.putString(PlatformContans.LoginContacts.PASSWORD, password);
        share_edit.putBoolean(PlatformContans.LoginContacts.IS_REM_USERNAME, isUserinfo);
        share_edit.putBoolean(PlatformContans.LoginContacts.IS_REM_PASSWORD, isPassword);
        share_edit.putBoolean(PlatformContans.LoginContacts.IS_AUTO_LOGIN, isAutoLogin);
        share_edit.commit();
        return true;
    }

    private String[] items = new String[] { "选择本地图片", "拍照" };
    private BottomMenuDialog bottomDialog;
    private void alertPicPanel(Context ctx){
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("上传图片");
        builder.addMenu(items[0], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地选择
//                picSelect();
                bottomDialog.dismiss();
            }
        });
        builder.addMenu(items[1], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
//                picCamera();
                bottomDialog.dismiss();
            }
        });
        bottomDialog = builder.create();
        bottomDialog.show();
    }


    public void callToPhone(String phone) {
        /**
         *  借助ContextCompat.checkSelfPermission()方法判断是否授予权限，接收两个参数，Context和具体权限名，方法的返回值与
         *  PackageManager.PERMISSION_GRANTED做比较，相等说明已经授权
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            /**
             * 同样借助ContextCompat.requestPermissions()方法弹出权限申请的对话框
             * 参数为Context,具体权限名，作为返回结果的识别码（自定义）
             */
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            //已授权拨打电话
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }
}
