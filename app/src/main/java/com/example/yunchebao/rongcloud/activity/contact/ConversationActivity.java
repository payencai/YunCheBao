package com.example.yunchebao.rongcloud.activity.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.GroupUser;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.activity.StrangerDelActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends AppCompatActivity {
    String targetId;//传递的融云的id
    String title; //传递的融云名称/用户昵称
    @BindView(R.id.title)
    TextView tv_title;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    String type = "";
    UserMsg mUserMsg;
    GroupUser mGroupUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        tv_title.setText(title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(mUserMsg!=null){
                    if ("1".equals(mUserMsg.getIsFriend())) {
                        intent = new Intent(ConversationActivity.this, FriendDetailActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);
                    } else {
                        intent = new Intent(ConversationActivity.this, StrangerDelActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);

                    }
                }
                if(mGroupUser!=null){
                    if (mGroupUser.getUserId().equals(MyApplication.getUserInfo().getId())) {
                        intent = new Intent(ConversationActivity.this, MyGroupDetailActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);
                    } else {
                        intent = new Intent(ConversationActivity.this, GroupDetailActivity.class);
                        intent.putExtra("id", targetId);
                        startActivity(intent);
                    }
                }



            }
        });
        if (targetId.contains("-")) {
            getUserDetail(targetId);
        } else {
            getGroupDetail(targetId);
        }

    }

    private void getGroupDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("hxCrowdId", id);
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByHxCrowdId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject Json = new JSONObject(result);
                    JSONObject data = Json.getJSONObject("data");
                    JSONObject object = data.getJSONObject("indexUser");
                    mGroupUser = new Gson().fromJson(object.toString(), GroupUser.class);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getUserDetail(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", id);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    mUserMsg = new Gson().fromJson(data.toString(), UserMsg.class);

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
