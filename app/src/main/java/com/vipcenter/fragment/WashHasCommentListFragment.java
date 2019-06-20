package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.vipcenter.adapter.WashHasCommentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/2/6.
 * 洗护宝评价_已评价列表
 */

public class WashHasCommentListFragment extends BaseFragment {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    private Context ctx;
    private List<PhoneGoodEntity> list;
    private WashHasCommentListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_newonly, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        ctx = getActivity();

        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_ee));
        listView.setDividerHeight(14);
        list = new ArrayList<>();
        adapter = new WashHasCommentListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }

}
