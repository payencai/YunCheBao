package com.example.yunchebao.drive.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.drive.fragment.DriveOrderFragment;
import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReplaceOrderActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.vp_content)
    ViewPager viewpager;
    @BindView(R.id.back)
    ImageView back;
    private ArrayList<Fragment> fragments;
    DriveOrderFragment fragment1;
    DriveOrderFragment fragment2;
    private String [] mTitles={"未完成","已付款"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_order);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment1.refresh();
        fragment2.refresh();
    }

    private void initView() {
        fragments=new ArrayList<>();
        fragment1=DriveOrderFragment.newInstance(1);
        fragment2=DriveOrderFragment.newInstance(2);
        fragments.add(fragment1);
        fragments.add(fragment2);
        tab_layout.setViewPager(viewpager,  mTitles,this,fragments);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
