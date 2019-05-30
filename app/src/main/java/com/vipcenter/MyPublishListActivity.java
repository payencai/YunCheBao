package com.vipcenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.babycircle.carfriend.DriverFriendsDetailActivity;
import com.example.yunchebao.babycircle.selfdrive.DrivingSelfDetailActivity;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vipcenter.adapter.MyPubAdapter;
import com.vipcenter.adapter.MyPublishListAdapter;
import com.vipcenter.model.Mypublish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class MyPublishListActivity extends NoHttpBaseActivity {
    @BindView(R.id.rv_pub)
    RecyclerView rv_pub;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefreshLayout;
    private MyPubAdapter mMyPubAdapter;
    private List<Mypublish> list;
    int page = 1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pub_article);
        ButterKnife.bind(this);
        initView();
    }

    private void delete(int id, int type) {
        String url = "";
        Map<String, Object> params = new HashMap<>();
        switch (type) {
            case 1:
                url = PlatformContans.BabyCircle.deleteSelfDrivingCircle;
                break;
            case 2:
                url = PlatformContans.BabyCircle.deleteCarCommunicationCircle;
                break;
            case 3:
                url = PlatformContans.BabyCircle.deleteCarShowCircle;
                break;
        }
        params.put("id", id);
        HttpProxy.obtain().post(url, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                page = 1;
                list.clear();
                getData();
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        mMyPubAdapter = new MyPubAdapter(R.layout.my_publish_list_item, list);
        mMyPubAdapter.setOnDeleteClickListener(new MyPublishListAdapter.onDeleteClickListener() {
            @Override
            public void onClick(int pos) {
                Mypublish mypublish = list.get(pos);
                delete(mypublish.getId(), mypublish.getType());
            }

            @Override
            public void onItemClick(int pos) {
                Mypublish mypublish = list.get(pos);
                Intent intent;
                switch (mypublish.getType()) {
                    case 1:
                        intent = new Intent(MyPublishListActivity.this, DrivingSelfDetailActivity.class);
                        intent.putExtra("id", mypublish.getId() + "");
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(MyPublishListActivity.this, DriverFriendsDetailActivity.class);
                        intent.putExtra("id", mypublish.getId() + "");
                        startActivity(intent);
                        break;


                }
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                list.clear();
                mMyPubAdapter.setNewData(list);
                getData();
            }
        });
        rv_pub.setLayoutManager(new LinearLayoutManager(this));
        rv_pub.setAdapter(mMyPubAdapter);

        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getMyCircle, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                mRefreshLayout.finishRefresh();
                Log.e("getFourShopListByApp", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<Mypublish> mypublishes = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Mypublish mypublish = new Gson().fromJson(item.toString(), Mypublish.class);
                        mypublishes.add(mypublish);
                        list.add(mypublish);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mMyPubAdapter.loadMoreEnd(true);
                        } else {
                            mMyPubAdapter.addData(mypublishes);
                            mMyPubAdapter.loadMoreComplete();
                        }
                    } else {
                        mMyPubAdapter.setNewData(list);

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


    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
