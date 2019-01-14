package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.OldCar;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class OldCarDetailActivity extends NoHttpBaseActivity {
    OldCar mOldCar;
    @BindView(R.id.slideshowView)
    Banner banner ;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_oldprice)
    TextView tv_oldprice;
    @BindView(R.id.tv_newprice)
    TextView tv_newprice;

    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_color)
    TextView tv_color;

    @BindView(R.id.tv_nexttime)
    TextView tv_nexttime;
    @BindView(R.id.tv_city)
    TextView tv_city;
    @BindView(R.id.tv_deadline)
    TextView tv_deadline;

    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_regtime)
    TextView tv_regtime;
    @BindView(R.id.tv_dis)
    TextView tv_dis;

    List<String> images=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mOldCar= (OldCar) bundle.getSerializable("data");
        }
        setContentView(R.layout.oldcar_detail_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"车辆详情");
        ButterKnife.bind(this);
        //网络地址获取轮播图
        if (!TextUtils.isEmpty(mOldCar.getCarCategoryDetail().getBanner1()) && !"null".equals(mOldCar.getCarCategoryDetail().getBanner1()))
            images.add(mOldCar.getCarCategoryDetail().getBanner1());
        if (!TextUtils.isEmpty(mOldCar.getCarCategoryDetail().getBanner2()) && !"null".equals(mOldCar.getCarCategoryDetail().getBanner2()))
            images.add(mOldCar.getCarCategoryDetail().getBanner2());
        if (!TextUtils.isEmpty(mOldCar.getCarCategoryDetail().getBanner3()) && !"null".equals(mOldCar.getCarCategoryDetail().getBanner3()))
            images.add(mOldCar.getCarCategoryDetail().getBanner3());
        initBanner();
        tv_newprice.setText("新车含税:￥"+mOldCar.getOldPrice());
        tv_oldprice.setText("￥"+mOldCar.getNewPrice());
        String name=mOldCar.getFirstName();
        if (!TextUtils.isEmpty(mOldCar.getSecondName()) && !"null".equals(mOldCar.getSecondName())){
            name=name+mOldCar.getSecondName();
        }
        if (!TextUtils.isEmpty(mOldCar.getThirdName()) && !"null".equals(mOldCar.getThirdName())){
            name=name+mOldCar.getThirdName();
        }
        tv_name.setText(name);
        tv_nickname.setText(mOldCar.getLinkman());
        tv_content.setText(mOldCar.getCarCategoryDetail().getRemark());
        tv_color.setText(mOldCar.getColor());
        tv_nexttime.setText("2019-12");
        tv_regtime.setText("2017-01");
        tv_num.setText("3次");
        tv_deadline.setText("2019-12");
        tv_city.setText(mOldCar.getRegistrationAddress());
        tv_dis.setText(mOldCar.getDistance()+"");
    }

    public void callToPhoneSweetAlert(final String phone) {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("确定要电话联系商家吗？")
                .setConfirmText("拨打")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        callToPhone(phone);
                    }
                })
                .setCancelText("算了")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    private void initBanner() {
        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(OldCarDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.start();
    }
    @OnClick({R.id.back,R.id.toDetailConfig,R.id.askLowPriceBtn,R.id.callForMoreBtn})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.toDetailConfig:
                ActivityAnimationUtils.commonTransition(OldCarDetailActivity.this, OldCarConfigDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
//            case R.id.toSellerDetail:
//                ActivityAnimationUtils.commonTransition(OldCarDetailActivity.this, SellerDetailActivity.class, ActivityConstans.Animation.FADE);
//                break;
            case R.id.askLowPriceBtn:
                ActivityAnimationUtils.commonTransition(OldCarDetailActivity.this,AskLowPriceActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.callForMoreBtn:
                callToPhoneSweetAlert("10010");
                break;
        }
    }
}
