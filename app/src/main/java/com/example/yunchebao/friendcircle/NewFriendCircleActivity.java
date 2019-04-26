package com.example.yunchebao.friendcircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.adapter.CircleDataAdapter;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.newversion.CircleData;
import com.newversion.FriendsCircleActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewFriendCircleActivity extends AppCompatActivity {

    @BindView(R.id.rv_circle)
    RecyclerView rv_circle;
    CircleDataAdapter mCircleDataAdapter;
    List<CircleData> mCircleData;
    private int friendsCircleType = 0;
    boolean first=true;
    int page =1;
    boolean isLoadMore;
    String firstItemId;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_circle);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mCircleData=new ArrayList<>();
        mCircleDataAdapter=new CircleDataAdapter(R.layout.item_friends_dynamic,mCircleData);
        rv_circle.setLayoutManager(new LinearLayoutManager(this));
        rv_circle.setAdapter(mCircleDataAdapter);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if (!first) {
            params.put("scrollId", firstItemId);
        }
        String url;
        if (friendsCircleType == 1) {
            //获取我自己的朋友圈
            url = PlatformContans.CommunicationCircle.getMyCommunicationCircleList;
        } else if (friendsCircleType == 2) {
            //获取他人的朋友圈列表
            params.put("userId", userId);
            url = PlatformContans.CommunicationCircle.getCommunicationCircleListByUserId;
        } else {
            //获取全部人的App
            url = PlatformContans.CommunicationCircle.getCommunicationCircleListForApp;
        }
        HttpProxy.obtain().get(url, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getCircleList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data != null && data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject obj = data.getJSONObject(i);
                            CircleData circleData = new Gson().fromJson(obj.toString(), CircleData.class);
                            mCircleData.add(circleData);
                        }
                        if (first) {
                            firstItemId = mCircleData.get(0).getId();
                        }

                    } else {

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
