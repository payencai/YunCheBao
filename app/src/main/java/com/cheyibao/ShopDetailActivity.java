package com.cheyibao;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheyibao.fragment.RentCarModelsFragment;
import com.cheyibao.fragment.RentShopComentFragment;
import com.cheyibao.model.RentShop;
import com.cheyibao.util.Const;
import com.common.ConfirmDialog;
import com.common.DialPhoneUtils;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.maket.adapter.GoodsOrderImageAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tool.DialogPopup;
import com.tool.view.HorizontalListView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class ShopDetailActivity extends AppCompatActivity {

    @BindView(R.id.shop_name_view)
    TextView shopNameView;
    @BindView(R.id.grade_view)
    TextView gradeView;
    @BindView(R.id.business_hours_view)
    TextView businessHoursView;
    @BindView(R.id.sb_score)
    SimpleRatingBar sbScore;
    @BindView(R.id.score_view)
    TextView scoreView;
    @BindView(R.id.address_view)
    TextView addressView;
    @BindView(R.id.phone_view)
    TextView phoneView;
    @BindView(R.id.head_view)
    ImageView headView;
    @BindView(R.id.photo_list_view)
    HorizontalListView photoListView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.app_barlayout)
    AppBarLayout appBarlayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.call_view)
    TextView callView;
    @BindView(R.id.online_chat_view)
    TextView onlineChatView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private RentShop rentShop;

    private GoodsOrderImageAdapter adapter;

    ArrayList<Fragment> mFragments = new ArrayList<>();

    String[] titles = {"租借车型", "查看评论"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        rentShop = (RentShop) Const.rentCarInfo.get(Const.RENT_CAR_INFO_SHOP);
        initView();
    }

    private void initView() {
        shopNameView.setText(rentShop.getName());
        gradeView.setText(String.format("%s", rentShop.getGrade()));
        businessHoursView.setText(String.format("营业时间:上午%s~%s 下午%s~%s", rentShop.getAmStart(), rentShop.getAmStop(), rentShop.getPmStart(), rentShop.getPmStop()));
        sbScore.setRating(rentShop.getScore());
        scoreView.setText(String.format("%s分", rentShop.getScore()));
        addressView.setText(String.format("%s%s%s%s", rentShop.getProvince(), rentShop.getCity(), rentShop.getArea(), rentShop.getAddress()));
        phoneView.setText(rentShop.getSaleTelephone());
        List<String> images = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555134766474&di=e9bf4c2cb0a08509a9b1b8088d7828b6&imgtype=0&src=http%3A%2F%2Fpic39.nipic.com%2F20140308%2F13085675_113419113000_2.jpg");
        }
        if (images.size() == 0) {
            photoListView.setVisibility(View.GONE);
        }
        adapter = new GoodsOrderImageAdapter(this, images);
        photoListView.setAdapter(adapter);

        mFragments.add(new RentCarModelsFragment());
        mFragments.add(new RentShopComentFragment());
        tabLayout.setViewPager(viewpager, titles, this, mFragments);
    }

    @OnClick(R.id.head_view)
    public void onHeadViewClicked() {
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.call_view)
    public void onCallViewClicked() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CALL_PHONE).subscribe(aBoolean -> {
            if (aBoolean){
                ConfirmDialog confirmDialog = new ConfirmDialog(ShopDetailActivity.this);
                confirmDialog .setTitle("拨号提示")
                        .setMessageText("确定拨打电话吗？")
                        .setCancelable(true)
                        .setConfirmClickListener(v -> {
                            DialPhoneUtils.startDialNumber(ShopDetailActivity.this,rentShop.getSaleTelephone());
                            confirmDialog.dismiss();
                        })
                        .setCancelClickListener(v -> confirmDialog.dismiss());
                confirmDialog.show();
            }
        });
    }

    @OnClick(R.id.online_chat_view)
    public void onOnlineChatViewClicked() {
    }
}
