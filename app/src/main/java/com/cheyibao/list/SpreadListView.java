package com.cheyibao.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 作者：凌涛 on 2019/1/5 14:48
 * 邮箱：771548229@qq..com
 */
public class SpreadListView extends ListView {
    public SpreadListView(Context context) {
        super(context);
    }

    public SpreadListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpreadListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeightSpec);
    }
}
//---------------------
//        作者：heyBa1
//        来源：CSDN
//        原文：https://blog.csdn.net/iceunc1e/article/details/79042004
//        版权声明：本文为博主原创文章，转载请附上博文链接！