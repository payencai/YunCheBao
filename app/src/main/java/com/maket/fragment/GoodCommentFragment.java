package com.maket.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheyibao.fragment.StudyListFragment;
import com.entity.PhoneCommBaseType;
import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;
import com.tool.viewpager.IndicatorViewPager;
import com.tool.viewpager.OnTransitionTextListener;
import com.tool.viewpager.ScrollIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2017/12/9.
 */

public class GoodCommentFragment extends BaseFragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.good_comment_fragment, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {

    }



}
