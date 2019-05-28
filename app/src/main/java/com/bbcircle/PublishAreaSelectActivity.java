package com.bbcircle;

import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.example.yunchebao.babycircle.carfriend.DriverFriendsRepublishActivity;
import com.example.yunchebao.babycircle.selfdrive.SelfDrivingRepublishActivity;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/23.
 */

public class PublishAreaSelectActivity extends NoHttpBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_area_select_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"选择发布的区块");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lay1,R.id.lay2,R.id.lay3,R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                ActivityAnimationUtils.commonTransition(PublishAreaSelectActivity.this, SelfDrivingRepublishActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay2:
                ActivityAnimationUtils.commonTransition(PublishAreaSelectActivity.this, DriverFriendsRepublishActivity.class, ActivityConstans.Animation.FADE);
                break;
            case R.id.lay3:
                ActivityAnimationUtils.commonTransition(PublishAreaSelectActivity.this,CarsShowRepublishActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
