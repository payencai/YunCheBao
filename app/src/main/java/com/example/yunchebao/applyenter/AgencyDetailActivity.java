package com.example.yunchebao.applyenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.xihubao.ShopInfoActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgencyDetailActivity extends AppCompatActivity {
    @BindView(R.id.banner)
    Banner banner;
    List<String> mImages=new ArrayList<>();
    Agency mAgency;
    @BindView(R.id.tv_nick)
    TextView tv_nick;
    @BindView(R.id.tv_grade)
    TextView tv_grade;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_dis)
    TextView tv_dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_detail);
        mAgency= (Agency) getIntent().getSerializableExtra("data");
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        ButterKnife.bind(this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setData();
    }
    private void initBanner() {

        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(AgencyDetailActivity.this).load((String) path).into(imageView);
            }
        });

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(mImages);//设置图片源
        banner.start();
    }
    private void setData(){
        tv_nick.setText(mAgency.getName());
        tv_address.setText(mAgency.getAddress());
        //tv_dis.setText(mAgency.get);
        tv_grade.setText(""+mAgency.getGrade());
        tv_num.setText(mAgency.getShopNumber()+"");
        String images=mAgency.getImgs();
        if(!TextUtils.isEmpty(images)){
            if(images.contains(",")){
                String [] imgs=images.split(",");
                for (int i = 0; i <imgs.length ; i++) {
                    mImages.add(imgs[i]);
                }
            }else{
                mImages.add(images);
            }
            initBanner();
        }
    }
}
