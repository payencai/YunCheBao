package com.bbcircle.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.babycircle.selfdrive.DrivingSelfDetailActivity;
import com.bbcircle.adapter.BBCircleListAdapter;
import com.bbcircle.data.SelfDrive;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.RegisterActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;


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
 * Created by sdhcjhss on 2017/12/9.
 * 自驾游
 */

public class SelfDrivingFragment extends BaseFragment {

    private List<SelfDrive> list = new ArrayList<>();
    private BBCircleListAdapter adapter;
    //    private Context ctx;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout sr_refresh;
    int page = 1;
    boolean isLoadMore = false;
    CommonAdapter<SelfDrive> mCommonAdapter;
    LoadMoreWrapper<SelfDrive> mLoadMoreWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recycleview_all, container, false);
        ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }

    public void loadData() {
        isLoadMore=true;
        page++;
        getData();

    }

    private void initView() {

        initList();
        getData();

    }

    public void refreshData() {
        list.clear();
        adapter.notifyDataSetChanged();
        page = 1;
        getData();
    }

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getSelfDrivingCircleList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<SelfDrive> selfDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        SelfDrive baikeItem = new Gson().fromJson(item.toString(), SelfDrive.class);
                        list.add(baikeItem);
                        selfDrives.add(baikeItem);
                    }
                    if (data.length() == 0) {
                        if(isLoadMore){
                            adapter.loadMoreEnd(true);
                            isLoadMore = false;
                        }
                    } else {
                        if (isLoadMore) {
                            isLoadMore = false;
                            adapter.addData(selfDrives);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.setNewData(selfDrives);
                            adapter.loadMoreComplete();
                        }
                    }
                    if(sr_refresh.isRefreshing()){
                        sr_refresh.setRefreshing(false);
                    }
                    //updateData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initList() {
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BBCircleListAdapter(R.layout.bbcircle_list_item_layout, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
               Intent intent=new Intent(getContext(),DrivingSelfDetailActivity.class);
               intent.putExtra("id",list.get(position).getId()+"");
                if (MyApplication.isLogin) {
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, listView);
        mCommonAdapter = new CommonAdapter<SelfDrive>(getActivity(), R.layout.bbcircle_list_item_layout, list) {
            @Override
            protected void convert(ViewHolder holder, SelfDrive carShow, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", carShow.getId());
                        bundle.putInt("type", 1);
                        if (MyApplication.isLogin) {
                            ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                        } else {
                            startActivity(new Intent(getContext(), RegisterActivity.class));
                        }
                    }
                });
                SimpleDraweeView img2 = (SimpleDraweeView) holder.getView(R.id.img2);
                TextView name1 = (TextView) holder.getView(R.id.bottom1);
                TextView tv_num = (TextView) holder.getView(R.id.tv_num);
                TextView tv_time = (TextView) holder.getView(R.id.tv_time);
                TextView name2 = (TextView) holder.getView(R.id.bottom2);
                TextView tv_title = (TextView) holder.getView(R.id.name2);
                //ImageView iv_video = (ImageView) holder.getView(R.id.iv_video);
                name2.setText(carShow.getCommentNum() + "回帖");
                name1.setText(carShow.getName());
                tv_num.setText("已报名："+carShow.getEnterNum()+"人");
                tv_time.setText("时间："+carShow.getStartTime().substring(0,10)+"至"+carShow.getEndTime().substring(0,10));
                tv_title.setText(carShow.getTitle());
                //iv_video.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(carShow.getImage()))
                    img2.setImageURI(Uri.parse(carShow.getImage()));
            }


        };
        mLoadMoreWrapper = new LoadMoreWrapper(mCommonAdapter);
        mLoadMoreWrapper.setLoadMoreView(R.layout.item_loadmore);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        });
        sr_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();

            }
        });
        listView.setAdapter(adapter);

    }

}
