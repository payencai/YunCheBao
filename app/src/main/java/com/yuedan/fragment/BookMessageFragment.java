package com.yuedan.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.TopMiddlePopup;
import com.yuedan.adapter.BookMessageListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class BookMessageFragment extends BaseFragment {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    @BindView(R.id.rule_line_tv)
    TextView topLineTv;
    @BindView(R.id.menuText)
    TextView menuText;

    private ArrayList<String> items = new ArrayList<String>();
    private boolean isOpen = false;
    public static int screenW, screenH;

    private TopMiddlePopup middlePopup;
    private Context ctx;

    private BookMessageListAdapter adapter;
    private List<PhoneGoodEntity> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.book_message_list_layout, container, false);
        commHiddenKeyboard(rootView);
        ctx = getActivity();
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        getItemsName();
        getScreenPixels();
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new BookMessageListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                int typeId = 0 ;
//                switch (position) {
//                    case 0:
//                        typeId = PlatformContans.LoginContacts.BOOK_TYPE_WASH_ID;
//                        break;
//                    case 1:
//                        typeId =  PlatformContans.LoginContacts.BOOK_TYPE_REPAIR_ID;
//                        break;
//                    case 2:
//                        typeId =  PlatformContans.LoginContacts.BOOK_TYPE_NEW_ID;
//                        break;
//                    case 3:
//                        typeId =  PlatformContans.LoginContacts.BOOK_TYPE_OLD_ID;
//                        break;
//                }
                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.BOOK_TYPE_ID,position);
               // ActivityAnimationUtils.commonTransition(getActivity(), BookChatDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
    }

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }

    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(getActivity(), screenW, screenH,
                onItemClickListener, items, type);
        middlePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isOpen = false;
                menuText.setTextColor(getResources().getColor(R.color.black_33));
                Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
                drawable.setBounds(0, 0, 20, 0);
                menuText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }
        });
        if (isOpen) {
            menuText.setTextColor(getResources().getColor(R.color.colorPrimary));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            menuText.setTextColor(getResources().getColor(R.color.black_33));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    }


    /**
     * 设置弹窗内容
     *
     * @return
     */
    private void getItemsName() {
        items.add("全部");
        items.add("洗车店");
        items.add("修车店");
        items.add("新车整车");
        items.add("二手车");
    }


    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            middlePopup.dismiss();
            menuText.setText(items.get(position));
            menuText.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
            Drawable drawable = ContextCompat.getDrawable(ctx, R.mipmap.yellow_arrow_small);
            drawable.setBounds(0, 0, 20, 0);
            menuText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
    };

    @OnClick({R.id.menuText})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.menuText:
                isOpen = isOpen ? false : true;
                setPopup(0);
                middlePopup.show(topLineTv);
                break;
        }
    }
}
