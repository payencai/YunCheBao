package com.system;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.system.fragment.ChatNoticeFragment;
import com.system.fragment.CoinNoticeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    String[] titles = {"提醒", "动态"};
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.vp_notice)
    ViewPager vp_notice;
    @BindView(R.id.back)
    ImageView back;
    CoinNoticeFragment mCoinNoticeFragment;
    ChatNoticeFragment mChatNoticeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        vp_notice.clearDisappearingChildren();
        fragments=new ArrayList<>();
        mCoinNoticeFragment=new CoinNoticeFragment();
        mChatNoticeFragment=new ChatNoticeFragment();
        fragments.add(mCoinNoticeFragment);
        fragments.add(mChatNoticeFragment);
        tab_layout.setViewPager(vp_notice, titles, this, fragments);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
