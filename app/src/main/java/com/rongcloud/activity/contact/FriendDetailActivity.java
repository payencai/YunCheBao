package com.rongcloud.activity.contact;

import android.graphics.Color;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.activity.AddFriendDetailActivity;
import com.rongcloud.sidebar.ContactModel;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

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
    @BindView(R.id.voice)
    TextView tv_voice;
    @BindView(R.id.video)
    TextView tv_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactModel = (ContactModel) getIntent().getSerializableExtra("data");
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initWindow(View view) {
        EasyPopup mCirclePop = EasyPopup.create()
                .setContentView(this, R.layout.dialog_friend_detail)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                //允许背景变暗
                .setBackgroundDimEnable(true)
                //变暗的透明度(0-1)，0为完全透明
                .setDimValue(0.4f)
                //变暗的背景颜色
                .setDimColor(Color.BLACK)
                .apply();
        LinearLayout ll_shaoma = (LinearLayout) mCirclePop.findViewById(R.id.ll_shaoma);
        LinearLayout ll_delete = (LinearLayout) mCirclePop.findViewById(R.id.ll_delete);
        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                addToBlack(mContactModel.getUserId());
            }
        });
        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                deleteFriend();
            }
        });
        mCirclePop.showAtAnchorView(view, YGravity.BELOW,XGravity.ALIGN_RIGHT,0,0);
    }
    private void addToBlack(String userId){
        RongIM.getInstance().addToBlacklist(userId, new RongIMClient.OperationCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(FriendDetailActivity.this, "拉黑成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Toast.makeText(FriendDetailActivity.this, "拉黑失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void deleteFriend() {

        Map<String, Object> params = new HashMap<>();
        params.put("friendId", mContactModel.getUserId());
        Log.e("friendId", mContactModel.getUserId());

        HttpProxy.obtain().post(PlatformContans.Chat.deleteMyFriend, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("delete", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        Toast.makeText(FriendDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
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
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(this).load(mContactModel.getHeadPortrait()).apply(mRequestOptions).into(head);
        chatname.setText(mContactModel.getName());
        account.setText(mContactModel.getHxAccount());
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(FriendDetailActivity.this, mContactModel.getUserId(), mContactModel.getName());
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
                initWindow(rl_top);
            }
        });
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongCallKit.startSingleCall(FriendDetailActivity.this, mContactModel.getUserId(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
        });
        tv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongCallKit.startSingleCall(FriendDetailActivity.this, mContactModel.getUserId(), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO);
            }
        });
    }
}
