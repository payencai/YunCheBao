package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.driverschool.DrivingSchoolActivity;
import com.cheyibao.adapter.ShopCommentAdapter;
import com.cheyibao.model.ShopComment;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.maket.model.LoadMoreListView;

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
public class ShopCommentFragment extends Fragment {

    private List<ShopComment> list;
    private ShopCommentAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    LoadMoreListView listView;
    int page=1;
    String id;
    boolean isLoadMore=false;
    public ShopCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_shop_comment, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;

    }
    private void initView() {
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        listView.setFocusable(false);
        list = new ArrayList<>();
        adapter = new ShopCommentAdapter(getContext(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                isLoadMore=true;
                page++;
                getData();
            }
        });
        getData();

    }
    public void getData(){
        DrivingSchoolActivity drivingSchoolActivity= (DrivingSchoolActivity) getActivity();
        id=drivingSchoolActivity.getDrvingSchool().getId();
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        //params.put("merchantId",id);
        params.put("type",4);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getUserComment, params, MyApplication.token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getUserComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ShopComment baikeItem = new Gson().fromJson(item.toString(), ShopComment.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
                    if(isLoadMore){
                        listView.setLoadCompleted();
                        isLoadMore=false;
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

}
