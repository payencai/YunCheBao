package com.rongcloud.activity.contact;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.activity.AddFriendDetailActivity;
import com.rongcloud.sidebar.ContactModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

public class FriendDetailActivity extends AppCompatActivity {
    ContactModel mContactModel;
    @BindView(R.id.sendmsg)
    TextView sendmsg;
    @BindView(R.id.iv_icon)
    ImageView head;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.rl_add)
    RelativeLayout rl_top;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactModel = (ContactModel) getIntent().getSerializableExtra("data");
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);
        initView();
    }
    private void initWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_group, null);
        popupWindow.setContentView(v);
        TextView content = (TextView) v.findViewById(R.id.content);
        content.setText("删除好友");
        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);

        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initWindow(rl_top);
                deleteFriend(popupWindow);
            }
        });


        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);


    }

    private void deleteFriend(final PopupWindow popupWindow) {
        final com.vipcenter.model.UserInfo userInfo = MyApplication.getUserInfo();
        Map<String, Object> params = new HashMap<>();
        params.put("friendId",mContactModel.getUserId());
        Log.e("friendId",mContactModel.getUserId());
        if (userInfo != null)
            HttpProxy.obtain().post(PlatformContans.Chat.deleteMyFriend, userInfo.getToken(), params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    popupWindow.dismiss();
                    Log.e("delete", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            Toast.makeText(FriendDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                            finish();
                        }else{
                            //Toast.makeText(GroupManageActivity.this, msg, Toast.LENGTH_SHORT).show();
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


    private void initView() {
        Glide.with(this).load(mContactModel.getHeadPortrait()).into(head);
        chatname.setText(mContactModel.getName());
        account.setText(mContactModel.getHxAccount());
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(FriendDetailActivity.this, mContactModel.getId(), mContactModel.getName());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWindow(v);
            }
        });
    }
}
