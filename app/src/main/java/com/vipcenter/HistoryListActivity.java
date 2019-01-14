package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.MyApplication;
import com.baike.MagzineCoverActivity;
import com.baike.adapter.MagzineListAdapter;
import com.baike.model.BaikeItem;
import com.bbcircle.data.History;
import com.costans.PlatformContans;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.HistoryListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class HistoryListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    private HistoryListAdapter adapter;
    private List<History> list;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list_layout);
        initView();
        getHistory();
    }
    private void getHistory(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.getUserInfo().getToken();
        }
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getHistoryList, token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("history", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        History baikeItem = new Gson().fromJson(item.toString(), History.class);
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
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW,"浏览历史");
        ButterKnife.bind(this);
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        ctx = this;
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_ee));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new HistoryListAdapter(ctx,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
