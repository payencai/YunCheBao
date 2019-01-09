package com.tool.listview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 作者：凌涛 on 2019/1/2 10:56
 * 邮箱：771548229@qq..com
 */
public class PersonalViewPager extends ViewPager {
    //是否可以进行滑动
    private boolean canScroll = true;//默认可以滑动
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
        Log.e("viewpager",this.getChildCount()+"");
        for (int i = 0; i < this.getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if(h>height){
                height=h;
            }
            maps.put(i, h);
        }
        if (getChildCount() > 0) {
            if(getChildAt(position)!=null)
            height = getChildAt(position).getMeasuredHeight();
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroll;
    }
//---------------------
//    作者：月色下的独轮车
//    来源：CSDN
//    原文：https://blog.csdn.net/baidu_31093133/article/details/80777101
//    版权声明：本文为博主原创文章，转载请附上博文链接！
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
