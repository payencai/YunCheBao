package com.example.yunchebao.rongcloud.activity.contact;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.newversion.MyTagsActivity;
import com.newversion.NewTag;
import com.payencai.library.util.ToastUtil;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class FriendDetailActivity extends AppCompatActivity {
    UserMsg mUserMsg;
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
    @BindView(R.id.iv_msg)
    ImageView iv_msg;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.rl_clears)
    RelativeLayout   rl_clears;
    @BindView(R.id.cd_car)
    CardView cd_card;
    @BindView(R.id.sex)
    TextView  tv_sex;
    @BindView(R.id.car)
    TextView  tv_car;
    @BindView(R.id.nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_tag)
    TextView tv_tag;
    @BindView(R.id.rl_tag)
    RelativeLayout  rl_tag;
    Conversation.ConversationNotificationStatus conversationNotificationStatus1;
    String id;
    String name;
    String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getIntent().getStringExtra("id");
        setContentView(R.layout.activity_friend_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }
    public void getTag() {

        HttpProxy.obtain().get(PlatformContans.Label.getLabelList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getLabelList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        NewTag newTag = new Gson().fromJson(item.toString(), NewTag.class);
                        for (int j = 0; j < newTag.getList().size(); j++) {
                            if(id.equals(newTag.getList().get(j).getUserId())){
                                tag=tag+" "+newTag.getName();
                            }
                        }
                    }
                    if(!TextUtils.isEmpty(tag))
                      tv_tag.setText(tag.replace("null"," "));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
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
                addToBlack(id);
            }
        });
        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCirclePop.dismiss();
                deleteFriend(id);
            }
        });

        mCirclePop.showAtAnchorView(view, YGravity.BELOW, XGravity.ALIGN_RIGHT, 0, 0);
    }

    private void addToBlack(String userId) {
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

    private void deleteFriend(String id) {

        Map<String, Object> params = new HashMap<>();
        params.put("friendId", id);
        //Log.e("friendId", mContactModel.getUserId());

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
    private void setUi( UserMsg userMsg){
        tv_sex.setText(userMsg.getSex());
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
                .skipMemoryCache(true);//不做内存缓存
        mRequestOptions.error(R.mipmap.ic_default_head);
        mRequestOptions.placeholder(R.mipmap.ic_default_head);
        Glide.with(this).load(userMsg.getHeadPortrait()).apply(mRequestOptions).into(head);
        chatname.setText(userMsg.getName());
        account.setText(userMsg.getUsername());
        tv_nickname.setText("");
        if(userMsg.getCarShowState()==1){
            cd_card.setVisibility(View.VISIBLE);
            if(userMsg.getCarList()!=null){
                if(userMsg.getCarList().size()>0)
                    tv_car.setText(userMsg.getCarList().get(0).getModels());
            }

        }else{
            cd_card.setVisibility(View.GONE);
        }
    }
    private void getDetail(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("userId",id);
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
    private void initView() {
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> images=new ArrayList<>();
                images.add(mUserMsg.getHeadPortrait());
                PictureConfig config = new PictureConfig.Builder()
                        .setListData(images)  //图片数据List<String> list
                        .setPosition(0)                         //图片下标（从第position张图片开始浏览）
                        .setDownloadPath("head")        //图片下载文件夹地址
                        .setIsShowNumber(true)                  //是否显示数字下标
                        .needDownload(true)                     //是否支持图片下载
                        .setPlaceHolder(R.mipmap.ic_launcher)   //占位符
                        .build();
                config.gotoActivity(FriendDetailActivity.this, config);
            }
        });
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(FriendDetailActivity.this,id, mUserMsg.getName());
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
                RongCallKit.startSingleCall(FriendDetailActivity.this, id, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
        });
        tv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongCallKit.startSingleCall(FriendDetailActivity.this, id, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO);
            }
        });
        rl_clears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().deleteMessages(Conversation.ConversationType.PRIVATE, id, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        ToastUtil.showToast(FriendDetailActivity.this,"清除成功！");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
        rl_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FriendDetailActivity.this, MyTagsActivity.class));
            }
        });
        getTag();
        getStatus();
        getIsTop();
        getDetail(id);
        iv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.PRIVATE, id, conversationNotificationStatus1, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                    @Override
                    public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                        getStatus();
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }

        });
        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 RongIM.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, id, isTop, new RongIMClient.ResultCallback<Boolean>() {
                     @Override
                     public void onSuccess(Boolean aBoolean) {
                         getIsTop();
                     }

                     @Override
                     public void onError(RongIMClient.ErrorCode errorCode) {

                     }
                 });
            }
        });
        findViewById(R.id.rl_nick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNickDialog();
            }
        });


    }
    private void updateNick(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("nickName", name);
        params.put("id", mUserMsg.getId());
        HttpProxy.obtain().post(PlatformContans.Chat.updateFriendsById, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("resutl", result);


            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void showNickDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nick, null);
        //获得dialog的window窗口
        //将自定义布局加载到dialog上
        TextView tv_confirm= (TextView) dialogView.findViewById(R.id.tv_confirm);
        EditText et_nick= (EditText) dialogView.findViewById(R.id.et_nick);
        TextView tv_cancel= (TextView) dialogView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String name=et_nick.getEditableText().toString();
                if(!TextUtils.isEmpty(name)){
                    updateNick(name);
                    tv_nickname.setText(name);
                }
            }
        });
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.CENTER);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = (int) (display.getWidth()*0.7);
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
    }
    boolean isTop=true;
    private void getIsTop(){

        RongIM.getInstance().getConversation(Conversation.ConversationType.PRIVATE, id, new RongIMClient.ResultCallback<Conversation>() {
            @Override
            public void onSuccess(Conversation conversation) {
                if(conversation==null){
                    isTop=true;
                    iv_top.setImageResource(R.mipmap.white_switch);
                }
                else{
                    if(conversation.isTop()){
                        isTop=false;
                        iv_top.setImageResource(R.mipmap.blue_switch);
                    }else{
                        isTop=true;
                        iv_top.setImageResource(R.mipmap.white_switch);
                    }
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }
    private void getStatus(){
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.PRIVATE, id, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
            @Override
            public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                final int value = conversationNotificationStatus.getValue();

                if (value == 1) {
                    iv_msg.setImageResource(R.mipmap.white_switch);
                    conversationNotificationStatus1 = conversationNotificationStatus.setValue(0);

                } else {
                    iv_msg.setImageResource(R.mipmap.blue_switch);
                    conversationNotificationStatus1 = conversationNotificationStatus.setValue(1);
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                //ToastUtil.showToast(FriendDetailActivity.this, errorCode.getMessage() + "");
            }
        });
    }
}
