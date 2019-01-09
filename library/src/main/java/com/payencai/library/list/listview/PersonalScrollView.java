package com.payencai.library.list.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 作者：凌涛 on 2019/1/2 10:57
 * 邮箱：771548229@qq..com
 */
public class PersonalScrollView extends ScrollView {

    public PersonalScrollView(Context context) {
        super(context);
    }

    public PersonalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PersonalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        View view = (View) getChildAt(getChildCount() - 1);

        int d = view.getBottom();

        d -= (getHeight() + getScrollY());

        if ((d == 0) && (onScrollBottomListener != null)) {
            onScrollBottomListener.onScrollBottom();
        }
    }

    public OnScrollBottomListener onScrollBottomListener = null;

    public interface OnScrollBottomListener {
        void onScrollBottom();
    }

    public void setOnScrollBottomListener(OnScrollBottomListener onScrollBottomListener) {
        this.onScrollBottomListener = onScrollBottomListener;
    }
}
//
//作者：布吉岛原住民
//        链接：https://www.jianshu.com/p/e4e2e5870f00
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
