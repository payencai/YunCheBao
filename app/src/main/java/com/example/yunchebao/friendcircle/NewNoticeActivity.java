package com.example.yunchebao.friendcircle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.costans.PlatformContans;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.example.yunchebao.friendcircle.adapter.FriendNoticeAdapter;
import com.example.yunchebao.friendcircle.model.FriendNotice;
import com.example.yunchebao.net.Api;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.vipcenter.model.ShopCollect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewNoticeActivity extends AppCompatActivity {
    @BindView(R.id.rv_notice)
    RecyclerView rv_notice;
    FriendNoticeAdapter adapter;
    List<FriendNotice> friendNotices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_notice);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        clearMsgNotice();
        friendNotices=new ArrayList<>();
        adapter=new FriendNoticeAdapter(R.layout.item_notice_friend,null);
        rv_notice.setLayoutManager(new LinearLayoutManager(this));
        rv_notice.setAdapter(adapter);
        getData();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void clearMsgNotice() {
        if (MyApplication.isLogin)
            NetUtils.getInstance().postByToken(Api.CommunicationCircle.clearCommunicationShowNotice, MyApplication.token, new OnMessageReceived() {
                @Override
                public void onSuccess(String response) {
                    Log.e("clearMsgNotice", response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
    }
    private void getData(){

        NetUtils.getInstance().get(MyApplication.token, Api.CommunicationCircle.getShowNoticeDetailsList, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Log.e("getshop", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            FriendNotice friendNotice = new Gson().fromJson(item.toString(), FriendNotice.class);
                            friendNotices.add(friendNotice);

                        }
                        adapter.setNewData(friendNotices);
                    }else{

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }


}
