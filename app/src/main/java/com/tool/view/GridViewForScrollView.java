package com.tool.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2017/3/25.
 */

public class GridViewForScrollView extends GridView{
    //private boolean havaScrollbar=true;
    public GridViewForScrollView(Context context) {
        super(context);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
     *设置是否有ScrollBar,当要在scrollview中显示时，应设为false,默认为true
     */
//    public void setHavaScrollbar(boolean havaScrollbar){
//        this.havaScrollbar=havaScrollbar;
//    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // if(!havaScrollbar){
            int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
//        }else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }
}
