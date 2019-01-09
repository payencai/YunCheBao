package com.rongcloud.activity.contact;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
import com.google.gson.JsonObject;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.activity.CreateGroupActivity;
import com.rongcloud.adapter.GridAdapter;
import com.rongcloud.model.Group;
import com.rongcloud.model.GroupUser;

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

    Group mGroup;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGroup= (Group) getIntent().getSerializableExtra("group");
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        initView();
        getData();
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
            final com.vipcenter.model.UserInfo userInfo = MyApplication.getUserInfo();
            Map<String, Object> params = new HashMap<>();
            params.put("crowdId",mGroup.getId());
            if (userInfo != null)
                HttpProxy.obtain().post(PlatformContans.Chat.quitCrowdByCrowdId, userInfo.getToken(), params, new ICallBack() {
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

    private void getData(){
        Map<String,Object>params=new HashMap<>();
        params.put("crowdId",mGroup.getId());
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByCrowdId, params,userinfo.getToken(), new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data",result);
                try {
                    JSONObject Json=new JSONObject(result);
                    JSONObject data=Json.getJSONObject("data");
                    JSONArray indexList = data.getJSONArray("indexList");
                    JSONObject object=data.getJSONObject("indexUser");
                    GroupUser groupUser = new Gson().fromJson(object.toString(), GroupUser.class);
                    nickname.setText(groupUser.getNickName());
                    people.setText(indexList.length()+"人");
                    for (int i = 0; i <indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        GroupUser applyFriend = new Gson().fromJson(item.toString(), GroupUser.class);
                        mAllGroupUser.add(applyFriend);
                        if(i<10){
                            mShowGroupUser.add(applyFriend);
                        }
                    }
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
    private void initView(){
        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startGroupChat(GroupDetailActivity.this, mGroup.getHxCrowdId(), mGroup.getCrowdName());
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
        chatname.setText(mGroup.getCrowdName());
        crow.setText(mGroup.getHxCrowdId());
        Glide.with(this).load(mGroup.getImage()).into(iv_icon);
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
