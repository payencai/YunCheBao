package com.payencai.library.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.payencai.library.R;

public class PassWordDialog extends BaseDialog {
    public PassWordDialog(Context context) {
        super(context);
    }

    @Override
    protected int getDialogStyleId() {
        return BaseDialog.DIALOG_COMMON_STYLE;
    }

    @Override
    protected View getView() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pwd, null);

        //得到各种view

        initViewEvent();
        return view;

    }
    //View的事件
    private void initViewEvent() {
        //设置对话框那个叉叉的方法，点击关闭对话框

    }



}
