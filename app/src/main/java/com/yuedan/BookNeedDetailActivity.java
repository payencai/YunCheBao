package com.yuedan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/24.
 */

public class BookNeedDetailActivity extends NoHttpBaseActivity {
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.typeName)
    TextView typeName;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.distanceText)
    TextView distanceText;
    @BindView(R.id.item1)
    TextView item1;
    @BindView(R.id.value1)
    TextView value1;
    @BindView(R.id.item2)
    TextView item2;
    @BindView(R.id.value2)
    TextView value2;
    @BindView(R.id.item3)
    TextView item3;
    @BindView(R.id.value3)
    TextView value3;
    @BindView(R.id.item4)
    TextView item4;
    @BindView(R.id.value4)
    TextView value4;
    @BindView(R.id.item5)
    TextView item5;
    @BindView(R.id.value5)
    TextView value5;
    @BindView(R.id.lay5)
    LinearLayout lay5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_need_detail_layout);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "需求详情");
        int type = (int) PlatformContans.OBJECT_MAP.get(PlatformContans.LoginContacts.BOOK_TYPE_ID);
        switch (type) {
            case 1:
                icon.setImageResource(R.mipmap.home_menu_1);
                typeName.setText("洗车店");
                item1.setText("预约时间");
                value2.setText("商户上门服务");
                lay5.setVisibility(View.VISIBLE);
                break;
            case 2:
                icon.setImageResource(R.mipmap.home_menu_2);
                typeName.setText("修车店");
                item1.setText("预约时间");
                value2.setText("商户上门服务");
                lay5.setVisibility(View.VISIBLE);
                break;
            case 3:
                icon.setImageResource(R.mipmap.home_menu_3);
                typeName.setText("4S店");
                item1.setText("发布时间");
                value2.setText("在线推荐");
                break;
            case 4:
                icon.setImageResource(R.mipmap.old_car_home_icon);
                typeName.setText("二手车");
                item1.setText("发布时间");
                value2.setText("在线推荐");
                break;
        }

    }

    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;

        }
    }
}
