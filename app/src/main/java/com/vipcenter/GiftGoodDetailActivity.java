package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

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

/**
 * Created by sdhcjhss on 2018/1/16.
 */

public class GiftGoodDetailActivity extends NoHttpBaseActivity {
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView;

    @BindView(R.id.menuTabText1)
    TextView tabText1;
    @BindView(R.id.menuTabText2)
    TextView tabText2;
    @BindView(R.id.menuTabLine1)
    View tabLine1;
    @BindView(R.id.menuTabLine2)
    View tabLine2;


    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_good_detail_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "商品详情页");
        ButterKnife.bind(this);
        ctx = this;

        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0; i < 3; i++) {
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", "https://you.autoimg.cn/_autohomecar__zhouyouji/657C2F909017074F9C59CA0B88DA0F0BDDC9.jpg?imageMogr2/format/jpg/thumbnail/790|watermark/2/text/5rG96L2m5LmL5a62/font/5b6u6L2v6ZuF6buR/fontsize/270/fill/I0ZGRkZGRg==");
//            image_uri.put("imageUris", adList.get(i).getCid());
            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);
    }

    @OnClick({R.id.back, R.id.menuTabText1, R.id.menuTabText2, R.id.submitBtn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.menuTabText1:
                findViewById(R.id.view1).setVisibility(View.VISIBLE);
                findViewById(R.id.view2).setVisibility(View.GONE);
                tabText1.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                tabText2.setTextColor(ContextCompat.getColor(ctx, R.color.gray_99));
                tabLine1.setVisibility(View.VISIBLE);
                tabLine2.setVisibility(View.GONE);
                break;
            case R.id.menuTabText2:
                findViewById(R.id.view2).setVisibility(View.VISIBLE);
                findViewById(R.id.view1).setVisibility(View.GONE);
                tabText2.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
                tabText1.setTextColor(ContextCompat.getColor(ctx, R.color.gray_99));
                tabLine2.setVisibility(View.VISIBLE);
                tabLine1.setVisibility(View.GONE);
                break;
            case R.id.submitBtn:
                ActivityAnimationUtils.commonTransition(GiftGoodDetailActivity.this, GiftOrderConfirmActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
