package com.payencai.library.list.listview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：凌涛 on 2019/1/2 10:56
 * 邮箱：771548229@qq..com
 */
public class PersonalViewPager extends ViewPager {

    private int position;

    private HashMap<Integer, Integer> maps = new LinkedHashMap<Integer, Integer>();

    public PersonalViewPager(Context context) {
        super(context);
    }

    public PersonalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            maps.put(i, h);
        }
        if (getChildCount() > 0) {
            height = getChildAt(position).getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在切换tab的时候，重置viewPager的高度
     */
    public void resetHeight(int position) {
        this.position = position;
        if (maps.size() > position) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maps.get(position));
            } else {
                layoutParams.height = maps.get(position);
            }
            setLayoutParams(layoutParams);
        }
    }
}
//
//作者：布吉岛原住民
//        链接：https://www.jianshu.com/p/e4e2e5870f00
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
