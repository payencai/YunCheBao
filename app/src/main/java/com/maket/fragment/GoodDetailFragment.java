package com.maket.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.bbcircle.DrivingSelfDetailActivity;
import com.bbcircle.DrivingSelfReplaySuccessActivity;
import com.entity.PhoneAddressEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.maket.adapter.AttenAddressListAdapter;
import com.maket.adapter.ConfigAdapter;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.slideshowview.SlideShowView;
import com.tool.view.HorizontalListView;
import com.vipcenter.AddressAddActivity;
import com.vipcenter.OrderConfirmActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/9.
 */

public class GoodDetailFragment extends BaseFragment {
    private Context ctx;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;
    @BindView(R.id.my_scrollview)
    PullToRefreshScrollView pullToRefreshScrollView;

    OnTabChangeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.maket_good_detail_layout, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0; i < 3; i++) {
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//            image_uri.put("imageUris", adList.get(i).getCid());
            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);

        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (listener != null) {
                    listener.changeTab(1);
                }
            }
        });
    }

    /**
     * 自定义Toast信息显示面板,我要报名
     *
     * @Title: commonToastDefined
     * @Description: TODO
     * @param @param text
     * @return void
     * @throws
     */
    Dialog dialog;

    public void attenConfigToast() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.atten_good_config_select, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(getActivity(), R.style.dialog);
       // dialog.getWindow().setBackgroundDrawable(new ColorDrawable());

        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        dialog.setContentView(view);
        dialog.show();
        HorizontalListView gv_color = (HorizontalListView) view.findViewById(R.id.gv_color);
        HorizontalListView gv_size = (HorizontalListView) view.findViewById(R.id.gv_size);
        List<String> list = new ArrayList<>();
        List<String> list_size = new ArrayList<>();
        list_size.add("42");
        list_size.add("43");
        list.add("红");
        list.add("白");
        list.add("蓝");
        list.add("绿");
        list.add("黑");
        ConfigAdapter adapter = new ConfigAdapter(ctx, list);
        ConfigAdapter adapter1 = new ConfigAdapter(ctx, list_size);
        gv_color.setAdapter(adapter);
        gv_size.setAdapter(adapter1);
        gv_color.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.toShopCartBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.buyNowBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                ActivityAnimationUtils.commonTransition(getActivity(), OrderConfirmActivity.class, ActivityConstans.Animation.FADE);
            }
        });

    }

    public void attenAddressToast() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.atten_good_address_select, null);
        RelativeLayout ll = (RelativeLayout) view.findViewById(R.id.ll_root);
        ll.getBackground().setAlpha(20);
        dialog = new Dialog(getActivity(), R.style.DialogStyleNoTitle);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(view);
        dialog.show();
        ListView listView = (ListView) view.findViewById(R.id.listView);
        List<PhoneAddressEntity> list = new ArrayList<>();
        AttenAddressListAdapter adapter = new AttenAddressListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        view.findViewById(R.id.addAddressBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                startActivityForResult(new Intent(getActivity(), AddressAddActivity.class), 1);
            }
        });

    }


    public interface OnTabChangeListener {
        void changeTab(int position);
    }

    public void setListener(OnTabChangeListener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.commentLay, R.id.configSelectLay, R.id.addressLay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.commentLay:
                if (listener != null) {
                    listener.changeTab(2);
                }
                break;
            case R.id.configSelectLay:
                attenConfigToast();
                break;
            case R.id.addressLay:
                attenAddressToast();
                break;
        }
    }

}
