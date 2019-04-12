package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2018/1/2.
 */

public class OnSaleCarActivity extends NoHttpBaseActivity {
    @BindView(R.id.listView)
    ExpandableListView listView;


    private List<PhoneGoodEntity> groupList;
    private List<List<PhoneGoodEntity>> childList;

    private Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_sale_car_layout);
        initView();
    }

    private void initView() {

    }




}
