package com.rongcloud.activity.contact;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.bumptech.glide.Glide;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.activity.AddFriendActivity;
import com.rongcloud.activity.ChatActivity;
import com.rongcloud.activity.CreateGroupActivity;
import com.rongcloud.activity.stranger.SaomaActivity;
import com.rongcloud.adapter.GridAdapter;
import com.rongcloud.model.Group;
import com.rongcloud.model.GroupUser;
import com.tool.view.GridViewForScrollView;

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

public class MyGroupDetailActivity extends AppCompatActivity {
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
    ImageView iv_icon;
    @BindView(R.id.chatname)
    TextView chatname;
    @BindView(R.id.account)
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
    @BindView(R.id.iv_menu)
    ImageView iv_menu;
    String id;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id= (String) getIntent().getStringExtra("id");
        setContentView(R.layout.activity_my_group_detail);
        ButterKnife.bind(this);
        initView();
        getDetail();
    }
    String cloudId;
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
                    RongIM.getInstance().refreshGroupUserInfoCache(new GroupUserInfo(cloudId,MyApplication.getUserInfo().getId(),name));
                    nickname.setText(name);
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
    private void getDetail(){
        Map<String,Object> params=new HashMap<>();
        params.put("crowdId",id);
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByCrowdId, params,MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject Json=new JSONObject(result);
                    JSONObject data=Json.getJSONObject("data");
                    name=data.getString("crowdName");
                    cloudId=data.getString("hxCrowdId");
                    JSONArray indexList = data.getJSONArray("indexList");
                    JSONObject object=data.getJSONObject("indexUser");
                    GroupUser groupUser = new Gson().fromJson(object.toString(), GroupUser.class);
                    chatname.setText(name);
                    crow.setText(data.getString("id"));
                    Glide.with(MyGroupDetailActivity.this).load(data.getString("image")).into(iv_icon);
                    nickname.setText(groupUser.getNickName());
                    people.setText(indexList.length()+"人");
                    for (int i = 0; i <indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        GroupUser applyFriend = new Gson().fromJson(item.toString(), GroupUser.class);
                        mAllGroupUser.add(applyFriend);
                        if(i<8){
                            mShowGroupUser.add(applyFriend);
                        }
                    }
                    GroupUser add=new GroupUser();
                    add.setFlag(1);
                    GroupUser delete=new GroupUser();
                    delete.setFlag(2);
                    mShowGroupUser.add(add);
                    mShowGroupUser.add(delete);
                    mAllGroupUser.add(add);
                    mAllGroupUser.add(delete);
                    mAdapter.notifyDataSetChanged();
                    setGridViewHeightBasedOnChildren(mGridView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void deleteGroup(){
        if (true) {
            final com.vipcenter.model.UserInfo userInfo = MyApplication.getUserInfo();
            Map<String, Object> params = new HashMap<>();
            params.put("crowdId",id);
            if (userInfo != null)
                HttpProxy.obtain().post(PlatformContans.Chat.dismissCrowdByCrowdId, MyApplication.token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("delete", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("resultCode");
                            if (code == 0) {
                                Toast.makeText(MyGroupDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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
    private void initWindow(View view) {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_group, null);

        LinearLayout ll_shaoma = (LinearLayout) v.findViewById(R.id.ll_shaoma);

        ll_shaoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // initWindow(rl_top);
                deleteGroup();
            }
        });

        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);


    }

    private void initView(){
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(MyGroupDetailActivity.this, cloudId, name);
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.rl_nick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNickDialog();
            }
        });
        mAllGroupUser = new ArrayList();
        mShowGroupUser = new ArrayList();
        mAdapter=new GridAdapter(this,mShowGroupUser);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 GroupUser groupUser=mShowGroupUser.get(position);
                 if(groupUser.getFlag()==1){
                     Intent intent=new Intent(MyGroupDetailActivity.this,GroupManageActivity.class);
                     //intent.putExtra("user",mGroup);
                     intent.putExtra("flag",groupUser.getFlag());
                     startActivity(intent);
                 }else if(groupUser.getFlag()==2){
                     Intent intent=new Intent(MyGroupDetailActivity.this,GroupManageActivity.class);
                    // intent.putExtra("user",mGroup);
                     intent.putExtra("flag",groupUser.getFlag());
                     startActivity(intent);
                 }
            }
        });
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

    /**
     * 当GridView外层有ScrollView时，需要动态设置GridView高度
     *
     * @param gridview
     */
    protected void setListViewHeightBasedOnChildren(GridView gridview) {
        if (gridview == null) return;
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) return;

        int totalHeight;
        //向上取整
        int count = (int) Math.ceil(listAdapter.getCount() / 5.0);
        //获取一个子view
        View itemView = listAdapter.getView(0, null, gridview);
        //测量View的大小
        itemView.measure(0, 0);
        totalHeight = itemView.getMeasuredHeight();
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        //设置GridView的布局高度
        params.height = totalHeight * count;
        gridview.setLayoutParams(params);
    }

}