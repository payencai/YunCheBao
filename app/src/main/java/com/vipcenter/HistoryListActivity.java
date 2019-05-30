package com.vipcenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yunchebao.MyApplication;
import com.example.yunchebao.babycircle.carfriend.DriverFriendsDetailActivity;
import com.example.yunchebao.babycircle.selfdrive.DrivingSelfDetailActivity;
import com.bbcircle.data.History;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.HistoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class HistoryListActivity extends NoHttpBaseActivity {
    @BindView(R.id.lv_history)
    ListView listView;
    private HistoryListAdapter adapter;
    private List<History> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list_layout);
        ButterKnife.bind(this);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"浏览历史");
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        initView();
        getHistory();
    }
    private void getHistory(){

        HttpProxy.obtain().get(PlatformContans.BabyCircle.getHistoryList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("history", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        History baikeItem = new Gson().fromJson(item.toString(), History.class);
                        if(baikeItem.getType()<=2)
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
    private void initView() {

        listView.setDivider(getResources().getDrawable(R.color.gray_ee));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new HistoryListAdapter(this,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History mypublish = list.get(position);
                Intent intent;
                switch (mypublish.getType()) {
                    case 1://自驾游
                        intent=new Intent(HistoryListActivity.this, DrivingSelfDetailActivity.class);
                        intent.putExtra("id",mypublish.getId()+"");
                        startActivity(intent);
                        break;
                    case 2://车友会
                        intent=new Intent(HistoryListActivity.this, DriverFriendsDetailActivity.class);
                        intent.putExtra("id",mypublish.getId()+"");
                        startActivity(intent);
                        break;

                }
            }
        });
    }


    @OnClick({R.id.back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
