package com.example.yunchebao.road.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.fourshop.activity.FourShopDetailActivity;
import com.example.yunchebao.fourshop.bean.FourShopComment;
import com.example.yunchebao.road.NewRoadDetailActivity;
import com.example.yunchebao.road.adapter.RoadCommentAdapter;
import com.example.yunchebao.road.model.RoadComment;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

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
public class RoadCommentFragment extends Fragment {

    @BindView(R.id.rv_comment)
    RecyclerView rv_comment;
    int page=1;
    String id;
    boolean isLoadMore=false;
    RoadCommentAdapter mRoadCommentAdapter;
    List<RoadComment> mRoadComments;
    NewRoadDetailActivity mActivity;
    public RoadCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_road_comment, container, false);
        ButterKnife.bind(this,view);
        mActivity= (NewRoadDetailActivity) getActivity();
        id=mActivity.getId();
        initView();
        return view;
    }

    private void initView() {
        mRoadComments=new ArrayList<>();
        mRoadCommentAdapter=new RoadCommentAdapter(mRoadComments);
        mRoadCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_comment);
        rv_comment.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_comment.setAdapter(mRoadCommentAdapter);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("shopId", id);
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getRoadRescueOrderCommentByShopId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getDrivingSchool", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<RoadComment> replaceDrives=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RoadComment replaceDrive = new Gson().fromJson(item.toString(), RoadComment.class);
                        mRoadComments.add(replaceDrive);
                        replaceDrives.add(replaceDrive);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        if (data.length() == 0) {
                            mRoadCommentAdapter.loadMoreEnd(true);
                        } else {
                            mRoadCommentAdapter.addData(replaceDrives);
                            mRoadCommentAdapter.loadMoreComplete();
                        }
                    } else {
                        mRoadCommentAdapter.setNewData(mRoadComments);

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
