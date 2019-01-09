package com.cheyibao;

import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.slideshowview.SlideShowView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2018/1/2.
 */

public class OnSaleCarDetailActivity extends NoHttpBaseActivity {
    //轮播图片
    private List<Map<String,String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_sale_car_detail);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"车辆详情");

        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0 ; i < 3 ; i++ ){
            Map<String,String> image_uri = new HashMap<String,String>();
            image_uri.put("imageUrls", "http://image.tianjimedia.com/uploadImages/2015/217/27/973A2CNH068A.jpg");
            image_uri.put("imageUrls","http://pic39.nipic.com/20140314/8098773_001017618110_2.jpg");
//            image_uri.put("imageUris", adList.get(i).getCid());
            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);
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

    @OnClick({R.id.back,R.id.shopBtn1,R.id.shopBtn2,R.id.askLowPriceBtn,R.id.callForMoreBtn,R.id.toDetailConfig})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.shopBtn1:
                ActivityAnimationUtils.commonTransition(OnSaleCarDetailActivity.this, OnSaleCarActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.shopBtn2:
                ActivityAnimationUtils.commonTransition(OnSaleCarDetailActivity.this, CompanyDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.toDetailConfig:
                ActivityAnimationUtils.commonTransition(OnSaleCarDetailActivity.this, OnSaleCarDetailConfigActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.askLowPriceBtn:
                ActivityAnimationUtils.commonTransition(OnSaleCarDetailActivity.this,AskLowPriceActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.callForMoreBtn:
                callToPhoneSweetAlert("10010");
                break;
        }
    }
}
