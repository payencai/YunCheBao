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

public class OrderReturnApplyActivity extends NoHttpBaseActivity {
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_return_apply_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "申请退款");
        ButterKnife.bind(this);
        ctx = this;
    }

    private BottomMenuDialog bottomDialog;

    private void alert1Panel(Context ctx) {
        String[] items = new String[]{"未收到货", "已收到货"};
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("货物状态");
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

    private void alert2Panel(Context ctx) {
        String[] items = new String[]{"退运费", "大小/重量与商品描述不符", "生产日期/保质期与商品描述不符", "标签/批次/包装/成分等与商品描述不符",
                "商品变质/发霉/有异物", "质量问题", "包装/商品破损"};
        BottomMenuDialog.Builder builder = new BottomMenuDialog.Builder(ctx);
        builder.setTitle("退款原因");
        for (int i = 0; i < items.length; i++) {
            builder.addMenu(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                picSelect();
                    bottomDialog.dismiss();
                }
            });
        }
        bottomDialog = builder.create();
        bottomDialog.show();
    }

    @OnClick({R.id.back, R.id.lay1, R.id.lay2})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.lay1:
                alert1Panel(ctx);
                break;
            case R.id.lay2:
                alert2Panel(ctx);
                break;
        }
    }
}
