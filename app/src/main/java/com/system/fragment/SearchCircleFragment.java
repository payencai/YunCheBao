package com.system.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.MyApplication;
import com.baike.model.BaikeItem;
import com.bbcircle.DriverFriendsDetailActivity;
import com.bbcircle.DrivingSelfDetailActivity;
import com.bbcircle.adapter.BKItemAdapter;
import com.bbcircle.adapter.CarFriendAdapter;
import com.bbcircle.data.CarFriend;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.vipcenter.RegisterActivity;
import com.xihubao.AssistanceDetailActivity;
import com.xihubao.model.Road;

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
public class SearchCircleFragment extends Fragment {

    int page = 1;
    boolean isLoadMore = false;
    CarFriendAdapter mRoadAdapter;
    List<CarFriend> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;

    public SearchCircleFragment() {
        // Required empty public constructor
    }
    String word;
    public static SearchCircleFragment newInstance(String value) {
        SearchCircleFragment fragment=new SearchCircleFragment();
        Bundle bundle=new Bundle();
        bundle.putString("word",value);
        fragment.setArguments(bundle);
        return fragment;
    }
    public  void getNewData(String value){
        word=value;
        page=1;
        mRoads.clear();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_new, container, false);
        ButterKnife.bind(this, view);
        word=getArguments().getString("word");
        initView();
        return view;
    }

    private void initView() {
        mRoads = new ArrayList<>();
        mRoadAdapter = new CarFriendAdapter(R.layout.bbcircle_list_item_layout, mRoads);
        mRoadAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore = true;
                getData();

            }
        }, rv_road);
        mRoadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CarFriend road = (CarFriend) adapter.getItem(position);
                Intent intent = new Intent(getContext(), AssistanceDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", road.getId());
                bundle.putInt("type", road.getType());
                intent.putExtras(bundle);
                if (MyApplication.isLogin) {
                    if(road.getType()==1)
                       ActivityAnimationUtils.commonTransition(getActivity(), DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                    else if(road.getType()==2){
                        ActivityAnimationUtils.commonTransition(getActivity(), DriverFriendsDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                    }
                }else{
                    startActivity(new Intent(getContext(), RegisterActivity.class));
                }
            }

// }

        });
        rv_road.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv_road.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_road.setAdapter(mRoadAdapter);

        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("keyword", "车");
        params.put("searchType", 9);
        Log.e("road", params.toString());
        HttpProxy.obtain().get(PlatformContans.Commom.searchAll, params, "", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    List<CarFriend> carFriends=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CarFriend road = new Gson().fromJson(item.toString(), CarFriend.class);
                        mRoads.add(road);
                        carFriends.add(road);
                    }

                    if (isLoadMore) {
                        isLoadMore = false;
                        mRoadAdapter.addData(carFriends);
                        mRoadAdapter.loadMoreComplete();
                    } else {
                        mRoadAdapter.setNewData(mRoads);
                        mRoadAdapter.loadMoreEnd(true);
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
