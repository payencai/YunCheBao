package com.vipcenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.application.MyApplication;
import com.bbcircle.DrivingSelfDetailActivity;
import com.caryibao.NewCar;
import com.cheyibao.SellerDetailActivity;
import com.costans.PlatformContans;
import com.entity.PhoneArticleEntity;
import com.entity.PhoneMagEntity;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nohttp.sample.NoHttpBaseActivity;
import com.rongcloud.activity.contact.FriendDetailActivity;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.HistoryListAdapter;
import com.vipcenter.adapter.MyPublishListAdapter;
import com.vipcenter.model.Mypublish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sdhcjhss on 2017/12/11.
 */

public class MyPublishListActivity extends NoHttpBaseActivity {
    @BindView(R.id.listview)
    PullToRefreshListView refreshListView;
    ListView listView;
    private MyPublishListAdapter adapter;
    private List<Mypublish> list;
    private Context ctx;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list_layout);
        initView();
    }

    private void initView() {
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "我的发表");
        ButterKnife.bind(this);
        findViewById(R.id.topPanel).setVisibility(View.VISIBLE);
        ctx = this;
        listView = refreshListView.getRefreshableView();
        listView.setDivider(getResources().getDrawable(R.color.gray_ee));
        listView.setDividerHeight(1);
        list = new ArrayList<>();
        adapter = new MyPublishListAdapter(ctx, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Mypublish mypublish=list.get(position);
                Intent intent;
                switch (list.get(position).getType()){
//                    case 1:
//                        intent=new Intent(MyPublishListActivity.this, DrivingSelfDetailActivity.class);
//                        intent.putExtra("id",mypublish.getId());
//                        startActivity(intent);
//                        break;
//                    case 2:
//                        intent=new Intent(MyPublishListActivity.this, FriendDetailActivity.class);
//                        intent.putExtra("id",mypublish.getId());
//                        startActivity(intent);
//                        break;

                }
                //ActivityAnimationUtils.commonTransition(MyPublishListActivity.this, DrivingSelfDetailActivity.class, ActivityConstans.Animation.FADE);
            }
        });
        getData();
    }
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.BabyCircle.getMyCircle, params, MyApplication.token,  new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getnewcar", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Mypublish baikeItem = new Gson().fromJson(item.toString(), Mypublish.class);
                        list.add(baikeItem);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }


    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
}
