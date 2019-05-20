package com.cheyibao.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yunchebao.driverschool.DrivingSchoolActivity;
import com.cheyibao.adapter.CoashCommentAdapter;
import com.cheyibao.list.SpreadListView;
import com.cheyibao.model.CoashComment;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
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
public class CoashCommentFragment extends Fragment {

    private List<CoashComment> list;
    private CoashCommentAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    SpreadListView listView;
    int page=1;
    String id;
    public CoashCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_coash_comment, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;

    }
    private void initView() {
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        listView.setFocusable(false);
        list = new ArrayList<>();
        adapter = new CoashCommentAdapter(getContext(),list);
        listView.setAdapter(adapter);

        getData();

    }
    public void getData(){
        DrivingSchoolActivity drivingSchoolActivity= (DrivingSchoolActivity) getActivity();
        id=drivingSchoolActivity.getDrvingSchool().getId();
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("merchantId",id);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getCoashComment, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getCoashComment", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CoashComment baikeItem = new Gson().fromJson(item.toString(), CoashComment.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();
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
