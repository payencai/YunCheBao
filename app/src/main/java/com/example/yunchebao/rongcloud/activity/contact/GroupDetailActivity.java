package com.example.yunchebao.rongcloud.activity.contact;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.payencai.library.view.CircleImageView;
import com.example.yunchebao.rongcloud.activity.GroupQrcodeActivity;
import com.example.yunchebao.rongcloud.adapter.GridAdapter;
import com.example.yunchebao.rongcloud.model.GroupUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class GroupDetailActivity extends AppCompatActivity {
    //收缩时显示的行数
    private static final int SHOWED_LINES = 2;
    //GridView的列数
    private static final int NUM_COLUMNS = 5;

    private List<GroupUser> mAllGroupUser;
    private List<GroupUser> mShowGroupUser;
    private List<GroupUser> m2GroupUser=new ArrayList<>();
    //是否收缩标志，默认收缩
    private boolean mIsShrink = true;

    private GridAdapter mAdapter;

    @BindView(R.id.iv_icon)
    CircleImageView iv_icon;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.crow)
    TextView crow;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.crowuser)
    GridView mGridView;
    @BindView(R.id.tv_show)
    TextView mToggle;
    @BindView(R.id.sendmsg)
    TextView sendmsg;
    @BindView(R.id.people)
    TextView people;
    @BindView(R.id.rl_add)
    RelativeLayout rl_top;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.iv_msg)
    ImageView iv_msg;
    @BindView(R.id.tv_clear)
            TextView tv_clear;
    String hxCrowdId;
    String groupId;
    String name;
    int indexId;
    Conversation.ConversationNotificationStatus conversationNotificationStatus1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hxCrowdId= (String) getIntent().getStringExtra("id");
        Log.e("hxCrowdId",hxCrowdId);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
        getDetail();

    }
    private void getStatus(){
        RongIM.getInstance().getConversationNotificationStatus(Conversation.ConversationType.GROUP, hxCrowdId, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
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
                //ToastUtil.showToast(GroupDetailActivity.this, errorCode.getMessage() + "");
            }
        });
    }
    private void initWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_group, null);
        TextView content = (TextView) v.findViewById(R.id.content);
        content.setText("退出群聊");
        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);

        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initWindow(rl_top);
                exitGroup();
            }
        });

        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);


    }

    private void exitGroup() {
        if (true) {

            Map<String, Object> params = new HashMap<>();
            params.put("crowdId",groupId);

                HttpProxy.obtain().post(PlatformContans.Chat.quitCrowdByCrowdId, MyApplication.token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("delete", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("resultCode");
                            if (code == 0) {
                                Toast.makeText(GroupDetailActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
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
    }
    String cloudId;
    private void getDetail(){
        Map<String,Object>params=new HashMap<>();
        params.put("hxCrowdId",hxCrowdId);
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByHxCrowdId, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject Json=new JSONObject(result);
                    JSONObject data=Json.getJSONObject("data");
                    name=data.getString("crowdName");
                    groupId=data.getString("id");
                    cloudId=data.getString("hxCrowdId");
                    JSONArray indexList = data.getJSONArray("indexList");
                    JSONObject object=data.getJSONObject("indexUser");
                    GroupUser groupUser = new Gson().fromJson(object.toString(), GroupUser.class);
                    chatname.setText(name);
                    crow.setText(data.getString("id"));
                    Glide.with(GroupDetailActivity.this).load(data.getString("image")).into(iv_icon);
                    nickname.setText(groupUser.getNickName());
                    people.setText(indexList.length()+"人");
                    for (int i = 0; i <indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        GroupUser groupUser1 = new Gson().fromJson(item.toString(), GroupUser.class);
                        if(MyApplication.getUserInfo().getId().equals(groupUser1.getUserId())){
                            indexId=groupUser1.getId();
                        }
                        mAllGroupUser.add(groupUser1);
                        if(i<10){
                            mShowGroupUser.add(groupUser1);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    setGridViewHeightBasedOnChildren(mGridView);
                    getStatus();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void initView(){
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(GroupDetailActivity.this, hxCrowdId, name);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    RongIM.getInstance().setConversationNotificationStatus(Conversation.ConversationType.GROUP, hxCrowdId, conversationNotificationStatus1, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
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
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().clearMessages(Conversation.ConversationType.GROUP,hxCrowdId , new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        ToastUtil.showToast(GroupDetailActivity.this,"清除成功");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });
            }
        });
        rl_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initWindow(v);
            }
        });
        findViewById(R.id.rl_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupDetailActivity.this, GroupQrcodeActivity.class);
                intent.putExtra("id",groupId);
                startActivity(intent);
            }
        });
        findViewById(R.id.rl_nick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNickDialog();
            }
        });
        mAllGroupUser = new ArrayList();
        mShowGroupUser = new ArrayList();
        mAdapter=new GridAdapter(this,mShowGroupUser);

        //绑定Adapter
        mGridView.setAdapter(mAdapter);

        mToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsShrink) {
                   expand();
                } else {
                   collapse();
                }
                setGridViewHeightBasedOnChildren(mGridView);
                //每次点击都要调用
               // setGridViewHeightBasedOnChildren(mGridView);
            }
        });
        //第一次调用
        //setListViewHeightBasedOnChildren(mGridView);

    }
    private void updateMyNick(String name){
        Map<String,Object> params=new HashMap<>();
        params.put("id",indexId);
        params.put("nickName",name);
        HttpProxy.obtain().post(PlatformContans.Chat.updateMyCrowdData, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                ToastUtil.showToast(GroupDetailActivity.this,"修改成功");
                mShowGroupUser.clear();
                mShowGroupUser.clear();
                m2GroupUser.clear();
                getDetail();
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
                    RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(hxCrowdId,MyApplication.getUserInfo().getId(),name));
                    nickname.setText(name);
                    updateMyNick(name);
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
    public  void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns=0;
        int horizontalBorderHeight=0;
        int verticalBorderHeight =0;
        Class<?> clazz=gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column=clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns=(Integer)column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing=clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight=(Integer)horizontalSpacing.get(gridView);
            //利用反射，取得属竖向分割线高度
//            Field verticalSpacing =clazz.getDeclaredField("mRequestedVerticalSpacing");
//            verticalSpacing.setAccessible(true);
//            verticalBorderHeight=(Integer)verticalSpacing.get(gridView);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if(listAdapter.getCount()%columns>0){
            rows=listAdapter.getCount()/columns+1;
        }else {
            rows=listAdapter.getCount()/columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight+horizontalBorderHeight*(rows-1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }


    //展开
    private void expand() {
        m2GroupUser.addAll(mShowGroupUser);
        mShowGroupUser.clear();
        mShowGroupUser.addAll(mAllGroupUser);
        mAdapter.notifyDataSetChanged();
        mToggle.setText("收起");
        mIsShrink = false;
    }

    //收缩
    private void collapse() {
        mShowGroupUser.clear();
        mShowGroupUser.addAll(m2GroupUser);
        m2GroupUser.clear();
        mAdapter.notifyDataSetChanged();
        mToggle.setText("全部展开");
        mIsShrink = true;
    }



}
