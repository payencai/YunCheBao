package com.rongcloud.activity.contact;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.rongcloud.activity.StrangerDelActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends AppCompatActivity {
    String targetId ;//传递的融云的id
    String title ; //传递的融云名称/用户昵称
    @BindView(R.id.title)
    TextView tv_title;
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        
        targetId=getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        type=getIntent().getData().getQueryParameter("type");
        ToastUtil.showToast(ConversationActivity.this,"点击了头像"+type);
        tv_title.setText(title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        RongIM.getInstance().setConversationClickListener(new RongIM.ConversationClickListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {

                getDetail(userInfo.getUserId());
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                return false;
            }

            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s, Message message) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });

    }
    private void setUi( UserMsg userMsg){
        Intent intent;
        if("1".equals(userMsg.getIsFriend())){
            intent=new Intent(ConversationActivity.this, FriendDetailActivity.class);
            intent.putExtra("id",userMsg.getId());
            startActivity(intent);
        }else{
            intent=new Intent(ConversationActivity.this, StrangerDelActivity.class);
            intent.putExtra("id",userMsg.getId());
            startActivity(intent);
        }
    }
    UserMsg mUserMsg;
    private void getDetail(String targetId){
        Map<String,Object> params=new HashMap<>();
        params.put("userId",targetId);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("detail",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                    mUserMsg=new Gson().fromJson(data.toString(),UserMsg.class);
                    setUi(mUserMsg);
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
