package com.example.yunchebao.road.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.road.NewRoadDetailActivity;
import com.example.yunchebao.road.adapter.RoadServiceAdapter;
import com.example.yunchebao.road.model.RoadService;
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
public class RoadServiceFragment extends Fragment {
    RoadServiceAdapter mServiceAdapter;
    String id;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    List<RoadService> mRoadServices;
    NewRoadDetailActivity mActivity;
    public RoadServiceFragment() {
        // Required empty public constructor
    }
    private void getService(){
        Map<String, Object> params = new HashMap<>();
        params.put("shopId",id);
        HttpProxy.obtain().get(PlatformContans.RoadRescue.getRoadRescueServeListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        RoadService road = new Gson().fromJson(item.toString(), RoadService.class);
                        mRoadServices.add(road);
                    }
                    mServiceAdapter.setNewData(mRoadServices);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_road, container, false);
        ButterKnife.bind(this,view);
        mActivity= (NewRoadDetailActivity) getActivity();
        id=mActivity.getId();
        mRoadServices=new ArrayList<>();
        mServiceAdapter=new RoadServiceAdapter(mRoadServices);
        rv_road.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_road.setAdapter(mServiceAdapter);
        getService();
        return view;
    }

}
