package com.vipcenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.application.MyApplication;
import com.baike.MagzineCoverActivity;
import com.cheyibao.OldCarDetailActivity;
import com.cheyibao.adapter.CarRecommendListAdapter;
import com.cheyibao.model.OldCar;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
import com.entity.PhoneShopEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.model.GoodList;
import com.maket.model.LoadMoreListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.view.ListViewForScrollView;
import com.vipcenter.adapter.GoodCollectAdapter;
import com.vipcenter.adapter.OldcarCollectAdapter;
import com.vipcenter.model.GoodsCollect;
import com.vipcenter.model.OldCarCollect;
import com.xihubao.adapter.WashCarListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sdhcjhss on 2018/1/6.
 */

public class OldCarFragment extends BaseFragment {
    @BindView(R.id.lv_loadmore)
    LoadMoreListView listView;
    @BindView(R.id.srl_collect)
    SwipeRefreshLayout srl_collect;
    private List<OldCarCollect> list;
    private OldcarCollectAdapter adapter;
    int page = 1;
    boolean isLoadMore = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listview_loadmore, container, false);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {

        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new OldcarCollectAdapter(getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                OldCar oldCar=new OldCar();
                OldCarCollect oldCarCollect=list.get(position);
                oldCar.setCarImage(oldCarCollect.getCarImage());
                oldCar.setFirstName(oldCarCollect.getFirstName());
                oldCar.setSecondName(oldCarCollect.getSecondName());
                oldCar.setThirdName(oldCarCollect.getThirdName());
                oldCar.setNewPrice(oldCarCollect.getCarPrice());
                oldCar.setId(oldCarCollect.getOldCarMerchantCarId());
                bundle.putSerializable("data",oldCar);
                ActivityAnimationUtils.commonTransition(getActivity(), OldCarDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
            }
        });
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                page++;
                getData();
            }
        });
        srl_collect.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                list.clear();
                srl_collect.setRefreshing(false);
                getData();
            }
        });
        getData();

    }

    private void getData() {
        String token = "";
        if (MyApplication.isLogin) {
            token = MyApplication.getUserInfo().getToken();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Collect.getOldCarCollectionList, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getold", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        OldCarCollect baikeItem = new Gson().fromJson(item.toString(), OldCarCollect.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    //updateData();
                    listView.setLoadCompleted();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

}
