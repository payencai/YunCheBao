package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nohttp.sample.BaseFragment;
import com.vipcenter.adapter.ShopHomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class ShopMainFragment extends BaseFragment {
    @BindView(R.id.listview)
    PullToRefreshGridView refreshListView;
    GridView listView;

    private int typeId;//

    private PullToRefreshScrollView pullToRefreshScrollView;
    private Context ctx;
    private List<PhoneGoodEntity> list;
    private ShopHomeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gridview_only, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();
        listView = refreshListView.getRefreshableView();
        listView.setNumColumns(2);
        list = new ArrayList<>();
        adapter = new ShopHomeListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    public void setPosition(int typeId) {
        this.typeId = typeId;
    }
}
