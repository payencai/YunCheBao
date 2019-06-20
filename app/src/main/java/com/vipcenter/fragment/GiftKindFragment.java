package com.vipcenter.fragment;

/**
 * Created by sdhcjhss on 2018/1/8.
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.MarketSelectListActivity;
import com.example.yunchebao.maket.adapter.KindMenuAdapter;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.GridViewForScrollView;
import com.vipcenter.GiftGoodDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GiftKindFragment extends BaseFragment {

    public static final String TAG = "MyFragment";
    private String str;
    private Context ctx;

    @BindView(R.id.gridView1)
    GridViewForScrollView gridView1;
    @BindView(R.id.gridView2)
    GridViewForScrollView gridView2;
    private KindMenuAdapter adapter1, adapter2;
    private List<PhoneGoodEntity> list1, list2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kind_fragment, null);
        ButterKnife.bind(this, view);
        ctx = getActivity();
        //得到数据
        str = getArguments().getString(TAG);
        initView();
        return view;
    }

    private void initView() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        adapter1 = new KindMenuAdapter(ctx, list1);
        gridView1.setAdapter(adapter1);
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(getActivity(), GiftGoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        adapter2 = new KindMenuAdapter(ctx, list2);
        gridView2.setAdapter(adapter2);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityAnimationUtils.commonTransition(getActivity(), GiftGoodDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
    }
}
