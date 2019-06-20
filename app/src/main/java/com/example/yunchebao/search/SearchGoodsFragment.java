package com.example.yunchebao.search;


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
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.model.GoodList;
import com.system.adapter.SearchGoodsAdapter;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;

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
public class SearchGoodsFragment extends Fragment {

    int page=1;
    boolean isLoadMore=false;
    SearchGoodsAdapter mRoadAdapter;
    List<GoodList> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    public SearchGoodsFragment() {
        // Required empty public constructor
    }
    String word;
    public static SearchGoodsFragment newInstance(String value) {
        SearchGoodsFragment fragment=new SearchGoodsFragment();
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

        View view=inflater.inflate(R.layout.fragment_search_new, container, false);
        ButterKnife.bind(this,view);
        word=getArguments().getString("word");
        initView();
        return view;
    }

    private void initView() {
        mRoads=new ArrayList<>();
        mRoadAdapter=new SearchGoodsAdapter(R.layout.item_know_you,mRoads);
        mRoadAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();

            }
        },rv_road);
        mRoadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodList road= (GoodList) adapter.getItem(position);
                Bundle bundle=new Bundle();
                bundle.putSerializable("data",road);
                ActivityAnimationUtils.commonTransition(getActivity(), GoodDetailActivity.class, ActivityConstans.Animation.FADE,bundle);
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
        params.put("keyword",word);
        params.put("searchType", 10);
        Log.e("road",params.toString());
        HttpProxy.obtain().get(PlatformContans.Commom.searchAll, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    List<GoodList> goodLists=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        GoodList road = new Gson().fromJson(item.toString(), GoodList.class);
                        mRoads.add(road);
                        goodLists.add(road);
                    }

                    if(isLoadMore){
                        isLoadMore=false;
                        mRoadAdapter.addData(goodLists);
                        mRoadAdapter.loadMoreComplete();
                    }else{
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
