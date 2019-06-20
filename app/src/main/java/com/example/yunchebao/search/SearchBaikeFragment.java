package com.example.yunchebao.search;


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
public class SearchBaikeFragment extends Fragment {

    int page=1;
    boolean isLoadMore=false;
    BKItemAdapter mRoadAdapter;
    List<BaikeItem> mRoads;
    @BindView(R.id.rv_road)
    RecyclerView rv_road;
    public SearchBaikeFragment() {
        // Required empty public constructor
    }
    String word;
    public static SearchBaikeFragment newInstance(String value) {
        SearchBaikeFragment fragment=new SearchBaikeFragment();
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
        mRoadAdapter=new BKItemAdapter(R.layout.item_baike,mRoads);
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
        rv_road.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rv_road.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_road.setAdapter(mRoadAdapter);

        getData();
    }

    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("keyword", word);
        params.put("searchType", 8);
        Log.e("road",params.toString());
        HttpProxy.obtain().get(PlatformContans.Commom.searchAll, params,"", new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("road", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject = jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("list");
                    List<BaikeItem> baikeItems=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        BaikeItem road = new Gson().fromJson(item.toString(), BaikeItem.class);
                        mRoads.add(road);
                        baikeItems.add(road);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        mRoadAdapter.addData(baikeItems);
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
