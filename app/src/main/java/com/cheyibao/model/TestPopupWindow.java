package com.cheyibao.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.yunchebao.R;

import io.rong.push.platform.hms.common.UIUtils;

/**
 * 作者：凌涛 on 2019/1/7 11:12
 * 邮箱：771548229@qq..com
 */
public class TestPopupWindow extends BasePopupWindowWithMask {
    private int[] mIds;
    private View contentView;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TestPopupWindow(Context context, int[] mIds) {
        super(context);
        this.mIds = mIds;

        initListener();
    }

    @Override
    protected View initContentView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.popup_select, null, false);
        return contentView;
    }

    private void initListener() {
        for (int i = 0; i < mIds.length; i++) {
            contentView.findViewById(mIds[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        listener.OnItemClick(v);
                    }
                    dismiss();
                }
            });
        }
    }
    @Override
    protected int initHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
    @Override
    protected int initWidth() {
        return (int) (com.cheyibao.model.UIUtils.getScreenWidth(context));
    }
}
