package com.yuedan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.R;
import com.nohttp.sample.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/22.
 */

public class BookNewCarFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.book_new_car_layout, container, false);
        commHiddenKeyboard(rootView);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
