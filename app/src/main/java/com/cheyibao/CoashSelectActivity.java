package com.cheyibao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cheyibao.adapter.CoashItemAdapter;
import com.cheyibao.model.CoachItem;
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

public class CoashSelectActivity extends AppCompatActivity {

    private List<CoachItem> list;
    private CoashItemAdapter adapter;
    @BindView(R.id.lv_coash)
    LoadMoreListView listView;
    @BindView(R.id.back)
    ImageView back;
    int page=1;
    String id;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coash_select);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();
    }
    private void initView() {
        listView.setDivider(getResources().getDrawable(R.color.gray_cc));
        listView.setDividerHeight(1);
        //listView.setFocusable(false);
        list = new ArrayList<>();
        adapter = new CoashItemAdapter(this,list);
        adapter.setOnSelectListener(new CoashItemAdapter.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                Intent intent=new Intent();
                intent.putExtra("coash",list.get(position));
                setResult(0,intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setAdapter(adapter);
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

        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        params.put("merchantId",id);
        HttpProxy.obtain().get(PlatformContans.DrivingSchool.getDrivingSchoolCoach, params,new ICallBack() {
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
                    if(isLoadMore){
                        isLoadMore=false;
                        listView.setLoadCompleted();
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
