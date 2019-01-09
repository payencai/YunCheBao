package com.xihubao.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 作者：凌涛 on 2018/12/22 17:56
 * 邮箱：771548229@qq..com
 */
public class MyListview extends ListView {

    private ScrollView mParent;

    private float mDownY;

    public MyListview(Context context) {
        super(context);
    }

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParent(ScrollView view){
        mParent = view;
    }


    //重写该方法 在按下的时候让父容器不处理滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setParentScrollAble(false);
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //
                if (isListViewReachTop() && ev.getY() - mDownY > 0) {
                    setParentScrollAble(true);
                } else if (isListViewReachBottom() && ev.getY() - mDownY < 0) {
                    setParentScrollAble(true);
                }
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                setParentScrollAble(true);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * @param flag
     */
    private void setParentScrollAble(boolean flag) {
        mParent.requestDisallowInterceptTouchEvent(!flag);
    }


    public boolean isListViewReachTop() {
        boolean result=false;
        if(getFirstVisiblePosition()==0){
            View topChildView = getChildAt(0);
            if (topChildView != null) {
                result=topChildView.getTop()==0;
            }
        }
        return result ;
    }

    public boolean isListViewReachBottom() {
        boolean result=false;
        if (getLastVisiblePosition() == (getCount() - 1)) {
            View bottomChildView = getChildAt(getLastVisiblePosition() - getFirstVisiblePosition());
            if (bottomChildView != null) {
                result= (getHeight() >= bottomChildView.getBottom());
            }
        }
        return  result;
    }
}

