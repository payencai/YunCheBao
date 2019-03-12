package com.maket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.cheyibao.OldCarListActivity;
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

/**
 * Created by sdhcjhss on 2017/12/28.
 */

public class MarketSelectActivity extends NoHttpBaseActivity {

    private Context ctx;
    @BindView(R.id.singleGrid1)
    SingleGridView<String> singleGridView;
    @BindView(R.id.et_min)
    EditText et_min;
    @BindView(R.id.et_max)
    EditText et_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_select_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "筛选");
        ButterKnife.bind(this);
        ctx = this;
        initView1();
    }

    private void initView1() {
        singleGridView.adapter(new SimpleTextAdapter<String>(null, ctx) {
            @Override
            public String provideText(String s) {
                return s;
            }

            @Override
            protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                checkedTextView.setPadding(0, UIUtil.dp(context, 14), 0, UIUtil.dp(context, 14));
                checkedTextView.setGravity(Gravity.CENTER);
                checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
//                checkedTextView.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_02));
            }
        }).onItemClick(new OnFilterItemClickListener<String>() {
            @Override
            public void onItemClick(String item) {

            }
        });

        List<String> list = new ArrayList<>();
        list.add("38-199");
        list.add("199-399");
        list.add("399以上");
        singleGridView.setList(list, -1);

    }


    @OnClick({R.id.back, R.id.submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                String min=et_min.getEditableText().toString();
                String max=et_max.getEditableText().toString();
                Intent intent =new Intent();
                intent.putExtra("min",min);
                intent.putExtra("max",max);
                setResult(1,intent);
                finish();
                //onBackPressed();
                break;
        }
    }
}
