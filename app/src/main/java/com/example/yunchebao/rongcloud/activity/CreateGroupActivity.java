package com.example.yunchebao.rongcloud.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;

public class CreateGroupActivity extends AppCompatActivity {
    private List<ContactModel> mContactModels;
    private List<ContactModel> mShowModels;
    private RecyclerView mRecyclerView;
    private WaveSideBarView mWaveSideBarView;
    private EditText mSearchEditText;
    private ContactsAdapter mAdapter;
    List<String> userid = new ArrayList<>();
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.confirm)
    TextView confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        ButterKnife.bind(this);
        initData();
        getContacts();

    }

    private void createCrow() {
        if (userid.size() > 0) {

            final com.vipcenter.model.UserInfo userInfo = MyApplication.getUserInfo();
            String id = userInfo.getId();
            for (int i = 0; i < userid.size(); i++) {
                id = id + "," + userid.get(i);
            }
            Map<String, Object> params = new HashMap<>();
            params.put("userIds", id);
            params.put("crowdName", userInfo.getName() + new Date().getTime());
            Log.e("id",id);
            if (userInfo != null)
                HttpProxy.obtain().post(PlatformContans.Chat.addCrowds, MyApplication.token, params, new ICallBack() {
                    @Override
                    public void OnSuccess(String result) {
                        Log.e("friend", result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int code = jsonObject.getInt("resultCode");
                            if (code == 0) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                Toast.makeText(CreateGroupActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
                                //RongIM.getInstance().startGroupChat(CreateGroupActivity.this, data.getString("hxCrowdId"), data.getString("crowdId"));
                                finish();
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

    private void getContacts() {
        com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
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
                        //mContactModels.addAll(ContactModel.getContacts());
                        mShowModels.addAll(mContactModels);
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
                createCrow();
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
                for (ContactModel model : mContactModels) {
                    String str = Trans2PinYinUtil.trans2PinYin(model.getName());
                    if (str.contains(s.toString()) || model.getName().contains(s.toString())) {
                        mShowModels.add(model);
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
