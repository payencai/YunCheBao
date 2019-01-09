package com.tool.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 作者：凌涛 on 2019/1/2 10:56
 * 邮箱：771548229@qq..com
 */
public class PersonalListView extends ListView {

    public PersonalListView(Context context) {
        super(context);
    }

    public PersonalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//    int flag = 0;
//    float StartX;
//    float StartY;
//    float ScollX;
//    float ScollY;
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////总是调用listview的touch事件处理
//        onTouchEvent(ev);
//        if(ev.getAction()==MotionEvent.ACTION_DOWN){
//            StartX = ev.getX();
//            StartY = ev.getY();
//            return false;
//        }
//        if(ev.getAction()== MotionEvent.ACTION_MOVE){
//            ScollX = ev.getX()-StartX;
//            ScollY = ev.getY()-StartY;
////判断是横滑还是竖滑，竖滑的话拦截move事件和up事件（不拦截会由于listview和scrollview同时执行滑动卡顿）
//            if(Math.abs(ScollX)<Math.abs(ScollY)){
//                flag = 1;
//                return true;
//            }
//            return false;
//        }
//        if(ev.getAction()==MotionEvent.ACTION_UP){
//            if(flag==1){
//
//                return true;
//            }
//            return false;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
//
//作者：布吉岛原住民
//        链接：https://www.jianshu.com/p/e4e2e5870f00
//        來源：简书
//        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。