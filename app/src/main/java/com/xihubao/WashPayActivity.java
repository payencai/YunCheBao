package com.xihubao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.entity.ServerType;
import com.example.yunchebao.R;
import com.tool.slideshowview.SlideShowView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WashPayActivity extends AppCompatActivity {
    ServerType.ServeListBean mServeListBean;
    //轮播图片
    private List<Map<String, String>> imageList = new ArrayList<>();
    String banner;
    @BindView(R.id.slideshowView)
    SlideShowView mSlideShowView;
    @BindView(R.id.price1)
    TextView tv_price1;
    @BindView(R.id.price2)
    TextView tv_price2;
    @BindView(R.id.tv_shop)
    TextView tv_shop;
    @BindView(R.id.tv_catagory)
    TextView tv_catagory;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServeListBean= (ServerType.ServeListBean) getIntent().getSerializableExtra("data");
        banner=getIntent().getStringExtra("type");
        setContentView(R.layout.activity_wash_pay);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<String> result = Arrays.asList(banner.split(","));
        for (int i = 0; i < result.size(); i++) {
            String url = result.get(i);
            Map<String, String> image_uri = new HashMap<String, String>();
            image_uri.put("imageUrls", url);
            imageList.add(image_uri);
        }
        mSlideShowView.setImageUrls(imageList);

        tv_price1.setText("￥"+mServeListBean.getPrice());
        tv_price2.setText("￥"+mServeListBean.getPrice());
        tv_shop.setText(mServeListBean.getFirstName());
        tv_catagory.setText(mServeListBean.getFirstContent());
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WashPayActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
