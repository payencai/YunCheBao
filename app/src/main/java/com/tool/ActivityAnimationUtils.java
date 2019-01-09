package com.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;


/***
 *  Activity之间切换动画
 * @ClassName: ActivityAnimationUtils 
 * @Description: TODO
 * @author Guo Jingbing
 * @date 2013-9-5 上午10:57:24
 */
@SuppressLint("NewApi")
public class ActivityAnimationUtils 
{
    /** TODO Activity之间跳转
     * @param cls 需要跳转到的视图
     * @param animation 动画编号 1: 淡入淡出   2： 从下往上推    3:从上往下推  4:左右滑动 其他：系统默认
     * **/
    public static void commonTransition(Activity act, Class<?> cls, int animation)
    {
        Intent intent = new Intent();
        intent.setClass(act.getApplicationContext(),cls);
        execTransitionAFR(act,intent,animation);
    }
    /***
     * TODO 包含一个对象
     * @param act
     * @param cls
     * @param animation
     */
    public static void commonTransition(Activity act, Class<?> cls, int animation, Bundle bundle )
    {
        Intent intent = new Intent();
//      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //必须加
        intent.setClass(act.getApplicationContext(),cls);
        intent.putExtras(bundle);
        execTransitionAFR(act,intent,animation);
    }

    /***
     * TODO 执行跳转
     * @param act
     * @param intent
     * @param animation
     */
    private static void execTransitionAFR(Activity act,Intent intent,int animation)
    {
        try
        {
            act.startActivityForResult(intent, 0);
            switch(animation)
            {
                case ActivityConstans.Animation.FADE:
                    act.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
//                case ActivityConstans.Animation.PUSH_UP:
//                    act.overridePendingTransition(R.anim.push_up_in_animation,R.anim.push_up_out_animation);
//                    break;
//                case ActivityConstans.Animation.PUSH_DOWN:
//                    act.overridePendingTransition(R.anim.push_down_in_animation,R.anim.push_down_out_animation);
//                    break;
//                case ActivityConstans.Animation.SLIDE_IN:
//                    act.overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
//                    break;
//                case ActivityConstans.Animation.SLIDE_LEFT:
//                    act.overridePendingTransition(R.anim.slide_left_in,R.anim.slide_left_out);
//                    break;
//                case ActivityConstans.Animation.SLIDE_RIGHT:
//                    act.overridePendingTransition(R.anim.slide_right_in,R.anim.slide_right_out);
//                    break;
//                case ActivityConstans.Animation.SLIDE_OUT:
//                    act.overridePendingTransition(R.anim.slide_right_in, R.anim.out_not_move);
//
//                    break;
            }
        }catch(Exception ex)
        {
            ex.getStackTrace();
//            JEREHDebugTools.Sysout(ActivityAnimationUtils.class, "execTransition", Log.ERROR, ex);
        }
    }
    

	public static AlphaAnimation mHideAnimation = null;
	public static AlphaAnimation mShowAnimation = null;
	/**
	 * View渐隐动画效果
	 */
	public static void setHideAnimation(View view, int duration) {
		if (null == view || duration < 0) {
			return;
		}
		if (null != mHideAnimation) {
			mHideAnimation.cancel();
		}
		mHideAnimation = new AlphaAnimation(1.0f, 0.0f);
		mHideAnimation.setDuration(duration);
//		mHideAnimation.setFillAfter(true);
		view.setVisibility(View.GONE);
		view.startAnimation(mHideAnimation);
	}

	/**
	 * View渐现动画效果
	 */
	public static void setShowAnimation(View view, int duration) {
		if (null == view || duration < 0) {
			return;
		}
		if (null != mShowAnimation) {
			mShowAnimation.cancel();
		}
		mShowAnimation = new AlphaAnimation(0.0f, 1.0f);
		mShowAnimation.setDuration(duration);
//		mShowAnimation.setFillAfter(true);
		view.setVisibility(View.VISIBLE);
		view.startAnimation(mShowAnimation);
	}

	/**
	 * View渐现动画效果
	 */
	public static void setShowAnimation(View view, int duration, int type) {
		if (null == view || duration < 0) {
			return;
		}else{
			if(type == 1){
				((TextView)view).setText("+1");
			}else{
				((TextView)view).setText("-1");
			}
			setShowAnimation(view, duration);
		}
	}
}
