package com.xihubao.fragment;


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

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.xihubao.AssistanceDetailActivity;
import com.xihubao.adapter.RoadAdapter;
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
public class RoadFragment extends Fragment {
    int page=1;
    boolean isLoadMore=false;
    RoadAdapter mRoadAdapter;
    List<Road> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    public RoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_roat, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
         mRoads=new ArrayList<>();
         mRoadAdapter=new RoadAdapter(R.layout.item_road,mRoads);
        mRoadAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                getData();
                isLoadMore=true;
            }
        },rv_road);
        mRoadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Road road= (Road) adapter.getItem(position);
                Intent intent =new Intent(getContext(),AssistanceDetailActivity.class);
                intent.putExtra("entity",road);
                startActivity(intent);
            }
        });
        rv_road.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
         rv_road.setLayoutManager(new LinearLayoutManager(getContext()));
         rv_road.setAdapter(mRoadAdapter);

         getData();
    }

    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("longitude", MyApplication.getaMapLocation().getLongitude());
        params.put("latitude", MyApplication.getaMapLocation().getLatitude());
        params.put("city", MyApplication.getaMapLocation().getCity());
        //Log.e("road",params.toString());
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getRoadRescueShopListByApp, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    //jsonObject = jsonObject.getJSONObject("data");
                    List<Road> roadList=new ArrayList<>();
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Road road = new Gson().fromJson(item.toString(), Road.class);
                        mRoads.add(road);
                        roadList.add(road);
                    }

                    if(isLoadMore){
                        isLoadMore=false;
                        mRoadAdapter.addData(roadList);
                        if(data.length()==0){
                            mRoadAdapter.loadMoreEnd(true);
                        }else{
                            mRoadAdapter.loadMoreComplete();
                        }
                    }else{
                        mRoadAdapter.setNewData(mRoads);
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
