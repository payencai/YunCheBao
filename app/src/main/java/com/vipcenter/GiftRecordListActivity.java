package com.vipcenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.application.MyApplication;
import com.baike.MagzineCoverActivity;
import com.costans.PlatformContans;
import com.entity.PhoneGoodEntity;
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
import com.vipcenter.adapter.GiftHotListAdapter;
import com.vipcenter.adapter.RecordListAdapter;
import com.vipcenter.model.Gift;

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
 * 兑换记录
 */

public class GiftRecordListActivity extends NoHttpBaseActivity {
    @BindView(R.id.rv_gift)
    RecyclerView rv_gift;
    List<Gift> list;
    GiftHotListAdapter adapter;
    int page=1;
    boolean isLoaMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_hot);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        list=new ArrayList<>();
        adapter = new GiftHotListAdapter(R.layout.gift_record_list_item_layout,list);
        rv_gift.setLayoutManager(new LinearLayoutManager(this));
        rv_gift.setAdapter(adapter);
        getData();


    }

    public void getData(){
        String token="";
        if(MyApplication.isLogin){
            token=MyApplication.getUserInfo().getToken();
        }
        //  Log.e("token",MyApplication.getUserInfo().getToken());
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.Gift.getGiftCommodityListToAPP, params, token,new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getGiftCommodity", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    jsonObject=jsonObject.getJSONObject("data");
                    JSONArray data = jsonObject.getJSONArray("beanList");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Gift gift = new Gson().fromJson(item.toString(), Gift.class);
                        int height=(i % 2)*100 + 400;
                        gift.setHeight(height);
                        list.add(gift);
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
