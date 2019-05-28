package com.bbcircle.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bbcircle.adapter.BBCircleListAdapter;
import com.bbcircle.data.SelfDrive;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2017/12/9.
 * 热帖
 */

public class HotArticleFragment extends BaseFragment {

    private List<SelfDrive> list;
    private BBCircleListAdapter adapter;
    private Context ctx;
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_only, container, false);
        ButterKnife.bind(this, rootView);
        initView();
//        requestMethod(0);
        return rootView;
    }

    private void initView() {
        ctx = getActivity();
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
       // adapter = new BBCircleListAdapter(ctx, list, 0);
       // listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE);
//            }
//        });
    }

}
