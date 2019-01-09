package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.entity.PhoneTraceEntity;
import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.adapter.LogisticsTraceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sdhcjhss on 2018/1/4.
 */

public class CheckLogisticsActivity extends NoHttpBaseActivity {
    private Context ctx;
    @BindView(R.id.listView)
    ListViewForScrollView listView;
    private LogisticsTraceListAdapter adapter;
    private List<PhoneTraceEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_logistics_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "查看物流");
        ButterKnife.bind(this);
        ctx = this;
        list = new ArrayList<>();
        adapter = new LogisticsTraceListAdapter(ctx, list);
        listView.setAdapter(adapter);

    }


    private void alertSweet() {
        new SweetAlertDialog(ctx, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("您的订单目前尚在正常生产中，\n" +
                        "请您耐心等候。感谢您的理解与\n" +
                        "支持。")
                .setConfirmText("确认")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    @OnClick({R.id.back, R.id.btn})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.btn:
                alertSweet();
                break;
        }
    }
}
