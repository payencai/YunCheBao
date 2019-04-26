package com.tool;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.R;


/**
 * Created by fanzh
 */
public class MyProgressDialog {
    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */

    public static Dialog dialog;
    public static Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        WindowManager.LayoutParams lp=loadingDialog.getWindow().getAttributes();
        lp.alpha=1.0f;
        loadingDialog.getWindow().setAttributes(lp);
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }

    public static void show(Context context, String msg){
//        if (dialog == null ){
//            dialog = createLoadingDialog(context,msg);
//            dialog.show();
//        }

        dialog = createLoadingDialog(context,msg);
        dialog.show();

    }
    public static void dismiss(){
        if(dialog!=null){
            dialog.dismiss();
        }
    }

//    public static void setListener(final Request<?> request){
//        if (dialog != null ){
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    request.cancel();
//                }
//            });
//        }
//    }
}
