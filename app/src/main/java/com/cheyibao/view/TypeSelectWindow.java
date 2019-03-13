package com.cheyibao.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.yunchebao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/3/13 11:40
 * 邮箱：771548229@qq..com
 */
public class TypeSelectWindow extends PopupWindow {

    private Activity activity;
    private ListAdapter mListAdapter = null;
    View contentView;

    View target;
    public TypeSelectWindow(Activity activity){
        this.activity = activity;
        initDatas();
        initView();
    }



    private void initView(){
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        contentView = LayoutInflater.from(activity).inflate(R.layout.join_popup_layout,null);
        target=contentView.findViewById(R.id.ll_target);

        this.setContentView(contentView);//设置布局
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setAnimationStyle(R.style.SelectPopupWindow);
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }

    /**
     * 模拟数据
     */
    List<String> listDatas = new ArrayList<>();
    private void initDatas(){
        listDatas.clear();
        listDatas.add("价格最低");
        listDatas.add("价格最高");
        listDatas.add("车龄最短");
        listDatas.add("里程最少");

    }
    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24){
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }
    //显示
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent);
            //执行进入动画，这里主要是执行列表下滑，背景变半透明在setAnimationStyle(R.style.SelectPopupWindow);中实现
            AnimationUtil.createAnimation(true,contentView,target,null);
        } else {
            dismissPopup();
        }
    }

    //消失
    public void dismissPopup(){
        super.dismiss();// 调用super.dismiss(),如果直接dismiss()会一直会调用下面的dismiss()
    }

//    @Override
//    public void dismiss() {
//        //执行推出动画，列表上滑退出，同时背景变透明
//        AnimationUtil.createAnimation(false,contentView,gridView , new AnimationUtil.AnimInterface() {
//            @Override
//            public void animEnd() {
//                dismissPopup();//动画执行完毕后消失
//            }
//        });
//    }
}

