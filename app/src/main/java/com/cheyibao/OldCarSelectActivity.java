package com.cheyibao;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
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

public class OldCarSelectActivity extends NoHttpBaseActivity {
    @BindView(R.id.textBtn)
    TextView rightBtn;

    private Context ctx;
    @BindView(R.id.singleGrid1)
    SingleGridView<String> singleGridView;
    @BindView(R.id.singleGrid2)
    SingleGridView<String> singleGridView2;
    @BindView(R.id.singleGrid3)
    SingleGridView<String> singleGridView3;
    @BindView(R.id.singleGrid4)
    SingleGridView<String> singleGridView4;
    @BindView(R.id.singleGrid5)
    SingleGridView<String> singleGridView5;
    @BindView(R.id.singleGrid6)
    SingleGridView<String> singleGridView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_car_select_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "筛选");
        ButterKnife.bind(this);
        ctx = this;
        rightBtn.setTextColor(ContextCompat.getColor(ctx, R.color.yellow_65));
        rightBtn.setText("重置");
        initView1();
        initView2();
        initView3();
        initView4();
        initView5();
        initView6();
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
//                checkedTextView.setTextColor(ContextCompat.getColor(ctx, R.color.black_33));
            }
        }).onItemClick(new OnFilterItemClickListener<String>() {
            @Override
            public void onItemClick(String item) {

            }
        });

        List<String> list = new ArrayList<>();
        list.add("个人");
        list.add("商家");
        list.add("不限");
        singleGridView.setList(list, -1);

    }

    private void initView2() {
        singleGridView2.adapter(new SimpleTextAdapter<String>(null, ctx) {
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
        list.add("国二以上");
        list.add("国三以上");
        list.add("国四以上");
        list.add("不限");
        singleGridView2.setList(list, -1);
    }

    private void initView3() {
        singleGridView3.adapter(new SimpleTextAdapter<String>(null, ctx) {
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
        list.add("汽油");
        list.add("柴油");
        list.add("电动");
        list.add("油电混合");
        list.add("不限");
        singleGridView3.setList(list, -1);
    }

    private void initView4() {
        singleGridView4.adapter(new SimpleTextAdapter<String>(null, ctx) {
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
        list.add("自动");
        list.add("手动");
        list.add("不限");
        singleGridView4.setList(list, -1);
    }

    private void initView5() {
        singleGridView5.adapter(new SimpleTextAdapter<String>(null, ctx) {
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
        list.add("2座");
        list.add("4座");
        list.add("5座");
        list.add("6座");
        list.add("7座以上");
        list.add("不限");
        singleGridView5.setList(list, -1);
    }

    private void initView6() {
        singleGridView6.adapter(new SimpleTextAdapter<String>(null, ctx) {
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
        list.add("德系");
        list.add("日系");
        list.add("美系");
        list.add("法系");
        list.add("韩系");
        list.add("国产");
        list.add("不限");
        singleGridView6.setList(list, -1);
    }

    @OnClick({R.id.back, R.id.submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.submit:
                ActivityAnimationUtils.commonTransition(OldCarSelectActivity.this, OldCarListActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }
}
