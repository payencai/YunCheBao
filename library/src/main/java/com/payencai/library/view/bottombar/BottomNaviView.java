package com.payencai.library.view.bottombar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.payencai.library.R;
import com.payencai.library.util.DensityUtil;
import com.payencai.library.view.bottombar.entity.BottomItem;

import java.util.ArrayList;
import java.util.List;

public class BottomNaviView extends LinearLayout {
    //tab的数量
    private int tabCount = 0;
    //tab对应数据的集合
    private List<BottomItem> mBottomItems = new ArrayList<>();
    //图标控件集合
    private List<ImageView> mTabIcons = new ArrayList<>();
    //文字控件集合
    private List<TextView> mTabTitle = new ArrayList<>();
    //红点view集合
    private List<View> redTabDots = new ArrayList<>();
    //消息数量view集合
    private List<TextView> msgNum = new ArrayList<>();

    public BottomNaviView(Context context) {
        super(context);
        initViews(context, null);
    }
    private onTabItemClickListener mOnTabItemClickListener;
    public void setOnTabItemClickListener(onTabItemClickListener mOnTabItemClickListener){
        this.mOnTabItemClickListener=mOnTabItemClickListener;
    }
    public interface  onTabItemClickListener{
        void onClick(View view,BottomItem bottomItem);
    }
    public BottomNaviView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    private void initViews(Context context, @Nullable AttributeSet attrs) {

    }

    //设置tab个数
    public BottomNaviView setTabCount(int tabCount) {
        this.tabCount = tabCount;
        return this;
    }
    //设置tab数据
    public BottomNaviView setTabData(List<BottomItem> bottomItems){
        this.mBottomItems=bottomItems;
        return this;

    }
    public void build() {
        if (tabCount > 0) {
            for (int i = 0; i < tabCount; i++) {
                View tabView = View.inflate(getContext(), R.layout.bottombar_item, null);
                BottomItem bottomItem = mBottomItems.get(i);
                ImageView tabIcon = tabView.findViewById(R.id.item_icon);
                TextView tabTitle = tabView.findViewById(R.id.item_title);
                TextView tabMsgnum = tabView.findViewById(R.id.item_msgNum);
                View tabDot = tabView.findViewById(R.id.item_dot);
                tabIcon.setImageResource(bottomItem.getUnPressIcon());
                tabTitle.setText(bottomItem.getItemTitle());
                if(bottomItem.isHasRedDot()){
                    tabDot.setVisibility(VISIBLE);
                    tabMsgnum.setVisibility(GONE);
                }
                if(bottomItem.getItemCount()>0){
                    tabMsgnum.setText(bottomItem.getItemCount()+"");
                    tabMsgnum.setVisibility(VISIBLE);
                    tabDot.setVisibility(GONE);
                }
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.width = DensityUtil.getScreenWidth(getContext()) / tabCount;
                tabView.setLayoutParams(layoutParams);
                tabView.setId(i);
                tabView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select(view.getId());
                        mOnTabItemClickListener.onClick(view,mBottomItems.get(view.getId()));
                    }
                });
                redTabDots.add(tabDot);
                mTabIcons.add(tabIcon);
                mTabTitle.add(tabTitle);
                msgNum.add(tabMsgnum);
                addView(tabView);

            }
            select(0);
        }

    }

    private void select(int position) {
        for (int i = 0; i < tabCount; i++) {
            BottomItem bottomItem = mBottomItems.get(i);
            ImageView tabIcon = mTabIcons.get(i);
            TextView tabTitle = mTabTitle.get(i);
            if (i == position) {
                tabIcon.setImageResource(bottomItem.getPressIcon());
                tabTitle.setTextColor(getResources().getColor(R.color.colorAccent));
            } else {
                tabIcon.setImageResource(bottomItem.getUnPressIcon());
                tabTitle.setTextColor(getResources().getColor(R.color.cp_color_gray));
            }
        }
    }

}
