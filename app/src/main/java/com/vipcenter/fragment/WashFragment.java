package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.example.yunchebao.rongcloud.model.CarShop;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.xihubao.WashCarDetailActivity;
import com.xihubao.adapter.WashCarListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class WashFragment extends BaseFragment {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    private Context ctx;
    List<CarShop> list = new ArrayList<>();
    WashCarListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_only, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new WashCarListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(getActivity(), WashCarDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });

    }
}
