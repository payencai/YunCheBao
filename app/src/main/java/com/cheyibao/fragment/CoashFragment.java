package com.cheyibao.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yunchebao.driverschool.DrivingOrderActivity;
import com.example.yunchebao.driverschool.CoachDetailActivity;
import com.example.yunchebao.driverschool.DrivingSchoolActivity;
import com.cheyibao.adapter.CoashItemAdapter;
import com.cheyibao.list.SpreadListView;
import com.cheyibao.model.CoachItem;
import com.cheyibao.model.DrvingSchool;
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
public class CoashFragment extends Fragment {


    public CoashFragment() {
        // Required empty public constructor
    }

    private List<CoachItem> list;
    private CoashItemAdapter adapter;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    SpreadListView listView;
    int page=1;
    String id;
    String schoolname;
    DrvingSchool mDrvingSchool;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_coash, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }
    private void initView() {
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        //listView.setFocusable(false);
        list = new ArrayList<>();
        adapter = new CoashItemAdapter(getContext(),list);
        adapter.setOnSelectListener(new CoashItemAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                Log.e("postion",position+"");
                Intent intent=new Intent(getContext(), DrivingOrderActivity.class);
                intent.putExtra("coash",list.get(position));
                intent.putExtra("id", id);
                intent.putExtra("name", mDrvingSchool);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CoachItem coachItem=list.get(position);
                Intent intent=new Intent(getContext(), CoachDetailActivity.class);
                intent.putExtra("id", coachItem.getId());
                startActivity(intent);
            }
        });
        getData();

    }
    public void getData(){
        DrivingSchoolActivity drivingSchoolActivity= (DrivingSchoolActivity) getActivity();
        mDrvingSchool=drivingSchoolActivity.getDrvingSchool();
        id=drivingSchoolActivity.getDrvingSchool().getId();
        schoolname=drivingSchoolActivity.getDrvingSchool().getName();
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("merchantId",id);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getDrivingSchoolCoach, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getdata", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        CoachItem baikeItem = new Gson().fromJson(item.toString(), CoachItem.class);
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

