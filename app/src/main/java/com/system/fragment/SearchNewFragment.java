package com.system.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.model.NewCar;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.system.adapter.SearchNewAdapter;
import com.system.adapter.WashRepairAdapter;
import com.system.model.WashRepair;

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
public class SearchNewFragment extends Fragment {

    int page=1;
    boolean isLoadMore=false;
    SearchNewAdapter mRoadAdapter;
    List<NewCar> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    public SearchNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_search_new, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        mRoads=new ArrayList<>();
        mRoadAdapter=new SearchNewAdapter(R.layout.item_search_new,mRoads);
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
//                Road road= (Road) adapter.getItem(position);
//                Intent intent =new Intent(getContext(),AssistanceDetailActivity.class);
//                intent.putExtra("entity",road);
//                startActivity(intent);
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
        params.put("keyword", "车");
        params.put("searchType", 4);
        Log.e("road",params.toString());
        HttpProxy.obtain().get(PlatformContans.Commom.searchAll, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewCar road = new Gson().fromJson(item.toString(), NewCar.class);
                        mRoads.add(road);
                    }
                    mRoadAdapter.setNewData(mRoads);
                    if(isLoadMore){
                        isLoadMore=false;
                        mRoadAdapter.loadMoreComplete();
                    }else{
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
