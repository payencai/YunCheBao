package com.baike;

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

/**
 * Created by sdhcjhss on 2017/12/18.
 */

public class DecorationListActivity extends NoHttpBaseActivity {
    //轮播图片
    private List<Map<String,String>> imageList = new ArrayList<>();
    @BindView(R.id.slideshowView)
    SlideShowView slideShowView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decoration_list_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"装饰百科");

        //网络地址获取轮播图
        imageList.clear();
        for (int i = 0 ; i < 3 ; i++ ){
            Map<String,String> image_uri = new HashMap<String,String>();
            image_uri.put("imageUrls", "http://image.tianjimedia.com/uploadImages/2015/217/32/17O1Z9FE863O.jpg");
//            image_uri.put("imageUris", adList.get(i).getCid());
            imageList.add(image_uri);
        }
        slideShowView.setImageUrls(imageList);
    }

    @OnClick({R.id.back,R.id.lay1,R.id.lay2,R.id.lay3,R.id.lay4})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
            case R.id.lay2:
            case R.id.lay3:
            case R.id.lay4:
                ActivityAnimationUtils.commonTransition(this,DecorationDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
