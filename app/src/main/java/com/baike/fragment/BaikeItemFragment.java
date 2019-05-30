package com.baike.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.MyApplication;
import com.baike.model.BaikeItem;
import com.bbcircle.adapter.BKItemAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.WebviewActivity;

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
 * A simple {@link Fragment} subclass.
 */
public class BaikeItemFragment extends Fragment {
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView rv_content;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout sr_refresh;
    private int type;
    private String id;
    BKItemAdapter mBaikeItemAdapter;
    int page = 1;
    boolean isLoadMore = false;
    List<BaikeItem> mBaikeItems = new ArrayList<>();

    public BaikeItemFragment() {

    }

    public static BaikeItemFragment newInstance(int type, String id) {
        BaikeItemFragment baikeItemFragment = new BaikeItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("id", id);
        baikeItemFragment.setArguments(bundle);
        return baikeItemFragment;
    }

    public void update() {
        mBaikeItems.clear();
        page = 1;
        getData();
    }

    public void loadMore() {
        isLoadMore = true;
        page++;
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        type = getArguments().getInt("type");
        id = getArguments().getString("id");
        View view = inflater.inflate(R.layout.recycleview_all, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mBaikeItems.clear();
        rv_content.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBaikeItemAdapter = new BKItemAdapter(R.layout.item_baike, mBaikeItems);
        mBaikeItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                BaikeItem baikeItem= (BaikeItem) adapter.getItem(position);
                Intent intent = new Intent(getContext(), WebviewActivity.class);
                String token="";
                if(MyApplication.isLogin){
                    token=MyApplication.token;
                }
                intent.putExtra("url", "http://www.yunchebao.com:8080/h5baby/?id="+baikeItem.getId()+"&token="+token);
                startActivity(intent);
            }
        });
        mBaikeItemAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

                  loadMore();
                // loadData();
            }
        });
        rv_content.setAdapter(mBaikeItemAdapter);

        sr_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                mBaikeItems.clear();
                mBaikeItemAdapter.setNewData(mBaikeItems);
                getData();
            }
        });
        page=1;
        getData();
    }

    private void getData() {
        // UserInfo userInfo = MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("page", page);
        params.put("classifyId", id);
        HttpProxy.obtain().get(PlatformContans.WiKi.getBabyWikiByclassifyId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                if(sr_refresh.isRefreshing()){
                    sr_refresh.setRefreshing(false);
                }
                Log.e("getWikiClassifyByType", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<BaikeItem> baikeItems = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        BaikeItem baikeItem = new Gson().fromJson(item.toString(), BaikeItem.class);
                        mBaikeItems.add(baikeItem);
                        baikeItems.add(baikeItem);
                    }
                    if (data.length() == 0) {
                        if(isLoadMore){
                            mBaikeItemAdapter.loadMoreEnd(true);
                            isLoadMore = false;
                        }
                    } else {
                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaikeItemAdapter.addData(baikeItems);
                            mBaikeItemAdapter.loadMoreComplete();
                        } else {
                            mBaikeItemAdapter.setNewData(baikeItems);
                            mBaikeItemAdapter.loadMoreComplete();
                        }
                    }

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
