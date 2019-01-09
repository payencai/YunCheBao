package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/15.
 * 宝币使用帮助--如何获得
 */

public class HowToGetFragment extends BaseFragment {
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gift_how_to_get, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
    }
}
