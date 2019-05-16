package com.example.yunchebao.rongcloud.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * 作者：凌涛 on 2019/1/25 20:55
 * 邮箱：771548229@qq..com
 */
public class MySlidingMenu extends ViewGroup {
    private int mScrenWidht;
    private int menuWidht;
    private int mScrenHeight;
    private Scroller mScroller;
    private boolean isOpen;
    private int interDownX;
    private int interDownY;
    private int interMoveX;
    private int interMoveY;
    private int scrollMoveX;
    private int scrollMoveY;
    private int touchMoveX;
    private int touchScrollX;


    public MySlidingMenu(Context context) {
        this(context, null, 0);
    }


    public MySlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MySlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        mScrenWidht = metrics.widthPixels;
        mScrenHeight = metrics.heightPixels;
//定义侧滑区域的宽度
        menuWidht = mScrenWidht - mScrenWidht / 2;
        mScroller = new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            View mView = getChildAt(i);
            measureChild(mView, widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(mScrenWidht + menuWidht, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0, top = 0, right = 0, bottom = mScrenHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View mView = getChildAt(i);
//侧滑区域
            if (i == 0) {
                left = -menuWidht;
                right = 0;
            } else {
                left = 0;
                right = mScrenWidht;
            }
            mView.layout(left, top, right, bottom);
        }


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean interFlags = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interDownX = (int) ev.getRawX();
                interDownY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                interMoveX = (int) ev.getRawX() - scrollMoveX;
                interMoveY = (int) ev.getRawY() - scrollMoveY;
                if (Math.abs(interMoveX) > Math.abs(interMoveY)) {
//拦截
                    interFlags = true;
                } else {
                    interFlags = false;
                }
                scrollMoveX = interDownX;
                scrollMoveY = interDownY;
                break;
        }
        return interFlags;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                touchMoveX = (int) ev.getRawX();
                touchScrollX = interDownX - touchMoveX;
//左移动
                if (touchScrollX > 0) {
                    if (getScrollX() + touchScrollX >= 0) {
                        scrollTo(0, 0);
                    } else {
                        scrollBy(touchScrollX, 0);
                    }
                } else {
                    if (getScrollX() + touchScrollX <= -menuWidht) {
                        scrollTo(-menuWidht, 0);
                    } else {
                        scrollBy(touchScrollX, 0);
                    }
                }
                interDownX = touchMoveX;
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() < -menuWidht / 2) {
//打开
                    openMenu();
                    isOpen = true;
                } else {
                    closeMenu();
                    isOpen = false;
                }
                invalidate();
                break;
        }
        return true;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


    public void toggleMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }


    }


    /**
     *  * 关闭menu
     *  
     */
    private void closeMenu() {
        isOpen = false;
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 200);
        invalidate();
    }


    /**
     *  * 打开menu
     *  
     */
    private void openMenu() {
        isOpen = true;
        mScroller.startScroll(getScrollX(), 0, -menuWidht - getScrollX(), 0, 200);
        invalidate();
    }


    /**
     *  * 将传进来的数转化为dp
     *  
     */
    private int convertToDp(Context context, int num) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, num, context.getResources().getDisplayMetrics());
    }
}
