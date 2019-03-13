package com.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 作者：凌涛 on 2019/3/13 09:59
 * 邮箱：771548229@qq..com
 */
public class DialogPopup extends BasePopupWindow {


    public DialogPopup(Context context) {
        super(context);

    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_city);
    }
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }
}
