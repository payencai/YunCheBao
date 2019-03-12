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

import com.application.MyApplication;
import com.baike.adapter.CarListAdapter;
import com.cheyibao.NewCarSellerActivity;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.maket.GoodDetailActivity;
import com.maket.model.GoodList;
import com.maket.model.LoadMoreListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.adapter.GoodCollectAdapter;
import com.vipcenter.adapter.GoodCollectListAdapter;
import com.vipcenter.model.GoodsCollect;

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
 * 商品收藏
 */

public class GoodsCollectFragment extends BaseFragment {
    @BindView(R.id.lv_loadmore)
    LoadMoreListView listView;
    @BindView(R.id.srl_collect)
    SwipeRefreshLayout srl_collect;
    private List<GoodsCollect> list;
    private GoodCollectAdapter adapter;
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
        adapter = new GoodCollectAdapter(getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                GoodList goodList=new GoodList();
                GoodsCollect goodsCollect=list.get(position);
                goodList.setId(goodsCollect.getCommodityId());
                goodList.setOriginalPrice(goodsCollect.getOriginalPrice());
                goodList.setDiscountPrice(goodsCollect.getDiscountPrice());
                goodList.setCommodityImage(goodsCollect.getCommodityImage());
                goodList.setName(goodsCollect.getCommodityName());
                bundle.putSerializable("data",goodList);
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
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
            token = MyApplication.token;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.Collect.getCommodityCollectionList, params, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodsCollect baikeItem = new Gson().fromJson(item.toString(), GoodsCollect.class);
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
