package com.bbcircle.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.bbcircle.CarShowDetailActivity;
import com.example.yunchebao.babycircle.selfdrive.DrivingSelfDetailActivity;
import com.bbcircle.adapter.CarShowAdapter;
import com.bbcircle.data.CarShow;
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
 * 热帖
 */

public class CarShowFragment extends BaseFragment {

    private List<CarShow> list = new ArrayList<>();
    private CarShowAdapter adapter;
//    private Context ctx;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    RecyclerView listView;
    boolean isLoadMore=false;
    int page = 1;
    CommonAdapter<CarShow> mCommonAdapter;
    LoadMoreWrapper<CarShow> mLoadMoreWrapper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recycleview_all, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    public void refreshData() {
        list.clear();
        page = 1;
        getData();
    }

    public void loadData() {
        isLoadMore=true;
        page++;
        getData();
    }

    private void initList() {
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new CarShowAdapter(R.layout.bbcircle_list_item_layout, list);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", list.get(position).getId());
                bundle.putInt("type", 3);
                if (MyApplication.isLogin) {
                        ActivityAnimationUtils.commonTransition(getActivity(), CarShowDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
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
        },listView);
        listView.setAdapter(adapter);



        mCommonAdapter = new CommonAdapter<CarShow>(getContext(), R.layout.bbcircle_list_item_layout, list) {
            @Override
            protected void convert(ViewHolder holder, CarShow carShow, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", carShow.getId());
                        bundle.putInt("type", 3);
                        if (MyApplication.isLogin) {
                            ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                        } else {
                            startActivity(new Intent(getContext(), RegisterActivity.class));
                        }
                    }
                });
                SimpleDraweeView img2 = (SimpleDraweeView) holder.getView(R.id.img2);
                TextView name1 = (TextView) holder.getView(R.id.bottom1);
                TextView name2 = (TextView) holder.getView(R.id.bottom2);
                TextView tv_title = (TextView) holder.getView(R.id.name2);
                ImageView iv_video = (ImageView) holder.getView(R.id.iv_video);
                name2.setText(carShow.getCommentNum() + "回帖");
                name1.setText(carShow.getName());
                tv_title.setText(carShow.getTitle());
                iv_video.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(carShow.getImage()))
                    img2.setImageURI(Uri.parse(carShow.getImage()));
            }
        };
        mLoadMoreWrapper=new LoadMoreWrapper(mCommonAdapter);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        });


    }

    private void initView() {

        initList();
        getData();

    }

    public void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getCarShowCircleList, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getCarShowCircleList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    List<CarShow> selfDrives = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarShow baikeItem = new Gson().fromJson(item.toString(), CarShow.class);
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
