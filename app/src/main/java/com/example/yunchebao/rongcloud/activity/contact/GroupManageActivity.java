package com.example.yunchebao.rongcloud.activity.contact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nanchen.wavesidebar.Trans2PinYinUtil;
import com.nanchen.wavesidebar.WaveSideBarView;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
import com.example.yunchebao.rongcloud.sidebar.ContactsAdapter;
import com.example.yunchebao.rongcloud.sidebar.PinnedHeaderDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupManageActivity extends AppCompatActivity {
    String id;

    private List<ContactModel> mShowModels;
    private RecyclerView mRecyclerView;
    private WaveSideBarView mWaveSideBarView;
    private EditText mSearchEditText;
    private List<ContactModel> mContactModels;
    private List<ContactModel> mGroupUsers;
    private ContactsAdapter mAdapter;
    List<String> userid = new ArrayList<>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.confirm)
    TextView confirm;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra("id");

        flag = getIntent().getIntExtra("flag", 0);
        setContentView(R.layout.activity_group_manage);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initData();
        if (flag == 2) {
            getData();
        } else {
            getGroupDetail();

        }

    }

    private void delete() {
        if (userid.size() > 0) {

            String ids = "";
            for (int i = 0; i < userid.size(); i++) {
                ids = ids + "," + userid.get(i);
            }
            Map<String, Object> params = new HashMap<>();
            if (!TextUtils.isEmpty(ids))
                params.put("userIds", ids.substring(1));
            params.put("crowdId", id);
            Log.e("del", ids);

            HttpProxy.obtain().post(PlatformContans.Chat.deleteCrowdByUserIds, MyApplication.token, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("delete", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("resultCode");
                        String msg = jsonObject.getString("message");
                        if (code == 0) {
                            Toast.makeText(GroupManageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(GroupManageActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void invite() {
        if (userid.size() > 0) {

            String ids = "";
            for (int i = 0; i < userid.size(); i++) {
                ids = ids + "," + userid.get(i);
            }
            Map<String, Object> params = new HashMap<>();
            if (!TextUtils.isEmpty(ids))
                params.put("userIds", ids.substring(1));
            params.put("crowdId", id);
            Log.e("ids", ids + "-" + id);

            HttpProxy.obtain().post(PlatformContans.Chat.joinCrowdByUserIds, MyApplication.token, params, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("invate", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String msg = jsonObject.getString("message");
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            //JSONObject data = jsonObject.getJSONObject("data");
                            Toast.makeText(GroupManageActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            //RongIM.getInstance().startGroupChat(GroupManageActivity.this, data.getString("hxCrowdId"), data.getString("crowdId"));
                            finish();
                        } else {
                            Toast.makeText(GroupManageActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    private void getGroupDetail() {
        Map<String, Object> params = new HashMap<>();
        params.put("crowdId", id);
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByCrowdId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject Json = new JSONObject(result);
                    JSONObject data = Json.getJSONObject("data");
                    JSONArray indexList = data.getJSONArray("indexList");
                    for (int i = 0; i < indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        ContactModel contactModel = new ContactModel(item.getString("nickName"));
                        contactModel.setHeadPortrait(item.getString("headPortrait"));
                        contactModel.setHxAccount(item.getString("hxAccount"));
                        contactModel.setId(item.getString("id"));
                        contactModel.setUserId(item.getString("userId"));
                        contactModel.setName(item.getString("nickName"));
                        contactModel.setIsNotice(item.getString("isNotice"));
                        contactModel.setNickName(item.getString("nickName"));
                        contactModel.setSelect(false);
                        mGroupUsers.add(contactModel);
                    }
                    getContacts();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void getContacts() {

        HttpProxy.obtain().get(PlatformContans.Chat.getMyFriendListForLabel, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("group", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ContactModel contactModel = new ContactModel(item.getString("name"));
                        contactModel.setHeadPortrait(item.getString("headPortrait"));
                        contactModel.setHxAccount(item.getString("hxAccount"));
                        contactModel.setId(item.getString("id"));
                        contactModel.setUserId(item.getString("userId"));
                        contactModel.setName(item.getString("name"));
                        contactModel.setIsNotice(item.getString("isNotice"));
                        contactModel.setNickName(item.getString("nickName"));
                        contactModel.setSelect(false);
                        mContactModels.add(contactModel);
                    }
                    for (int i = 0; i <mContactModels.size() ; i++) {
                        boolean isAdd=true;
                        ContactModel contacts=mContactModels.get(i);
                        for (int j = 0; j <mGroupUsers.size() ; j++) {
                            if(mContactModels.get(i).getUserId().equals(mGroupUsers.get(j).getUserId())){
                                isAdd=false;
                                break;
                            }
                        }
                        if(isAdd){
                            mShowModels.add(contacts);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("crowdId", id);
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdDetailsByCrowdId, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("data", result);
                try {
                    JSONObject Json = new JSONObject(result);
                    JSONObject data = Json.getJSONObject("data");
                    JSONArray indexList = data.getJSONArray("indexList");
                    for (int i = 0; i < indexList.length(); i++) {
                        JSONObject item = indexList.getJSONObject(i);
                        ContactModel contactModel = new ContactModel(item.getString("nickName"));
                        contactModel.setHeadPortrait(item.getString("headPortrait"));
                        contactModel.setHxAccount(item.getString("hxAccount"));
                        contactModel.setId(item.getString("id"));
                        contactModel.setUserId(item.getString("userId"));
                        contactModel.setName(item.getString("nickName"));
                        contactModel.setIsNotice(item.getString("isNotice"));
                        contactModel.setNickName(item.getString("nickName"));
                        contactModel.setSelect(false);
                        mContactModels.add(contactModel);
                    }
                    mShowModels.addAll(mContactModels);
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void initData() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mGroupUsers=new ArrayList<>();
        mContactModels = new ArrayList<>();
        mShowModels = new ArrayList<>();
        // RecyclerView设置相关
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new ContactsAdapter(mShowModels);
        mAdapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ImageView imageView) {
                ContactModel contactModel = mShowModels.get(position);
                mShowModels.remove(contactModel);
                if (!contactModel.isSelect()) {
                    userid.add(contactModel.getUserId());
                    imageView.setImageResource(R.mipmap.select);
                    contactModel.setSelect(true);
                } else {
                    userid.remove(contactModel.getUserId());
                    imageView.setImageResource(R.mipmap.unselect);
                    contactModel.setSelect(false);
                    //mAdapter.notifyDataSetChanged();
                }
                mShowModels.add(position, contactModel);
                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        // 侧边设置相关
        mWaveSideBarView = (WaveSideBarView) findViewById(R.id.main_side_bar);
        mWaveSideBarView.setOnSelectIndexItemListener(new WaveSideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String letter) {
                for (int i = 0; i < mContactModels.size(); i++) {
                    if (mContactModels.get(i).getIndex().equals(letter)) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1)
                    invite();
                else {
                    delete();
                }
            }
        });
        // 搜索按钮相关
        mSearchEditText = (EditText) findViewById(R.id.main_search);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mShowModels.clear();
                if(flag==2){
                    for (ContactModel model : mContactModels) {
                        String str = Trans2PinYinUtil.trans2PinYin(model.getName());
                        if (str.contains(s.toString()) || model.getName().contains(s.toString())) {
                            mShowModels.add(model);
                        }
                    }
                }else{
                    for (ContactModel model : mShowModels) {
                        String str = Trans2PinYinUtil.trans2PinYin(model.getName());
                        if (str.contains(s.toString()) || model.getName().contains(s.toString())) {
                            mShowModels.add(model);
                        }
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContactModels != null) {
            mContactModels.clear();
            mContactModels = null;
        }
    }
}
