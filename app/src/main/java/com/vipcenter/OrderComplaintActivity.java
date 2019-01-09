package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.yunchebao.R;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.BottomMenuDialog;
import com.tool.UIControlUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2018/1/5.
 */

public class OrderComplaintActivity extends NoHttpBaseActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_complaint_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "填写投诉申请");
        ButterKnife.bind(this);
        ctx = this;
    }

    private BottomMenuDialog bottomDialog;
    private void alertReasonPanel(Context ctx){
        String[] items = new String[] { "商家表示商品缺货", "未按约定时间发货" };
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("请选择投诉原因");
        builder.addMenu(items[0], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //本地选择
//                picSelect();
                bottomDialog.dismiss();
            }
        });
        builder.addMenu(items[1], new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
//                picCamera();
                bottomDialog.dismiss();
            }
        });
        bottomDialog = builder.create();
        bottomDialog.show();
    }

    @OnClick({R.id.back, R.id.reason})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.reason:
                alertReasonPanel(ctx);
                break;
        }
    }
}
