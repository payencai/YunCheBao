package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baike.adapter.CarListAdapter;
import com.cheyibao.NewCarSellerActivity;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.ShopMainListActivity;
import com.vipcenter.adapter.ShopCollectListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class ShopCollectListFragment extends BaseFragment {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;

    private Context ctx;
    private List<PhoneShopEntity> list;
    private ShopCollectListAdapter adapter;

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
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new ShopCollectListAdapter(ctx, list, this);
        listView.setAdapter(adapter);
    }

    public void onShortcutMenuClickListener(Integer tag, Integer loc) {
        switch (tag.intValue()) {
            case 0://点击内容
                ActivityAnimationUtils.commonTransition(getActivity(), ShopMainListActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 1://点击取消关注
                break;
        }
    }
}
