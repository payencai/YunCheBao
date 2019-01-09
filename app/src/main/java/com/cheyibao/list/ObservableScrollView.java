package com.cheyibao.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 作者：凌涛 on 2019/1/5 14:46
 * 邮箱：771548229@qq..com
 */
public class ObservableScrollView extends ScrollView {
    public interface OnScrollViewListener {
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }
    private OnScrollViewListener onScrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollViewListener != null) {
            onScrollViewListener.onScrollChanged(x, y, oldx, oldy);
        }
    }
}

