package com.system.View;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.example.yunchebao.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * 作者：凌涛 on 2019/3/18 16:18
 * 邮箱：771548229@qq..com
 */
public class MenuWindow extends BasePopupWindow {
    public MenuWindow(Context context) {
        super(context);
    }
    // 必须实现，这里返回您的contentView
    // 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.dialog_four_menu);
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

}
