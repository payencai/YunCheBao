package com.system;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.yunchebao.R;
import com.flyco.tablayout.SlidingTabLayout;
import com.example.yunchebao.search.SearchBaikeFragment;
import com.example.yunchebao.search.SearchCircleFragment;
import com.example.yunchebao.search.SearchGoodsFragment;
import com.example.yunchebao.search.SearchNewFragment;
import com.example.yunchebao.search.SearchOldFragment;
import com.example.yunchebao.search.SearchRentFragment;
import com.example.yunchebao.search.SearchRoadFragment;
import com.example.yunchebao.search.SearchSchoolFragment;
import com.example.yunchebao.search.SearchWashFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFinishActivity extends AppCompatActivity {
    @BindView(R.id.tab_layout)
    SlidingTabLayout tab_layout;
    @BindView(R.id.vp_search)
    ViewPager vp_search;
    @BindView(R.id.et_word)
    EditText et_word;
    SearchWashFragment fragment1;
    SearchRoadFragment fragment2;
    SearchNewFragment fragment3;
    SearchOldFragment fragment4;
    SearchRentFragment fragment5;
    SearchSchoolFragment fragment6;
    SearchBaikeFragment fragment7;
    SearchCircleFragment fragment8;
    SearchGoodsFragment fragment9;
    private ArrayList<Fragment> fragments;
    String word;
    String[] titles = {"洗修店", "紧急救援", "新车汇", "二手车", "车租赁", "驾校汇", "宝贝百科", "宝贝圈", "宝贝商城",};
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_finish);
        word=getIntent().getStringExtra("word");
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.tv_search,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_search:
                String word=et_word.getEditableText().toString();
                if(!TextUtils.isEmpty(word))
                   update(word);
                break;
        }
    }
    private void update(String word){
        switch (pos){
            case 0:
                fragment1.getNewData(word);
                break;
            case 1:
                fragment2.getNewData(word);
                break;
            case 2:
                fragment3.getNewData(word);
                break;
            case 3:
                fragment4.getNewData(word);
                break;
            case 4:
                fragment5.getNewData(word);
                break;
            case 5:
                fragment6.getNewData(word);
                break;
            case 6:
                fragment7.getNewData(word);
                break;
            case 7:
                fragment8.getNewData(word);
                break;
            case 8:
                fragment9.getNewData(word);
                break;
        }
    }
    private void initView() {
        et_word.setText(word);
        vp_search.clearDisappearingChildren();
        fragments = new ArrayList<>();
        fragment1 = SearchWashFragment.newInstance(word);
        fragment2 = SearchRoadFragment.newInstance(word);
        fragment3 = SearchNewFragment.newInstance(word);
        fragment4 = SearchOldFragment.newInstance(word);
        fragment5 = SearchRentFragment.newInstance(word);
        fragment6 = SearchSchoolFragment.newInstance(word);
        fragment7 = SearchBaikeFragment.newInstance(word);
        fragment8 = SearchCircleFragment.newInstance(word);
        fragment9 = SearchGoodsFragment.newInstance(word);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        fragments.add(fragment6);
        fragments.add(fragment7);
        fragments.add(fragment8);
        fragments.add(fragment9);
        tab_layout.setViewPager(vp_search, titles, this, fragments);
        vp_search.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
