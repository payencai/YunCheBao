package com.example.yunchebao.rongcloud.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.activity.contact.FriendDetailActivity;
import com.example.yunchebao.rongcloud.adapter.FriendAdapter;
import com.example.yunchebao.rongcloud.adapter.GroupAdapter;
import com.example.yunchebao.rongcloud.adapter.StrangerAdapter;
import com.example.yunchebao.rongcloud.model.Friend;
import com.example.yunchebao.rongcloud.model.Group;
import com.vipcenter.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.lv_result)
    ListView lv_result;
    @BindView(R.id.rv_friend)
    RecyclerView rv_friend;
    String type;
    String content;
    String province;
    String city;
    String area;
    String brand;
    String sex;
    String minAge;
    String maxAge;
    FriendAdapter mFriendAdapter;
    GroupAdapter mGroupAdapter;
    StrangerAdapter mStrangerAdapter;
    List<Friend> mFriends;
    int page = 1;
    boolean isLoadMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        content = getIntent().getStringExtra("content");
        type = getIntent().getStringExtra("type");
        province = getIntent().getStringExtra("province");
        city = getIntent().getStringExtra("city");
        area = getIntent().getStringExtra("area");
        brand = getIntent().getStringExtra("brand");
        sex = getIntent().getStringExtra("sex");
        minAge = getIntent().getStringExtra("minAge");
        maxAge = getIntent().getStringExtra("maxAge");
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mFriends = new ArrayList<>();
        et_search.setText(content);
        if (type.equals("0")) {//好友搜索
            rv_friend.setVisibility(View.VISIBLE);
            mStrangerAdapter = new StrangerAdapter(R.layout.item_friends,mFriends);
            mStrangerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Friend friend = (Friend) adapter.getItem(position);
                    Intent intent;
                    if ("0".equals(friend.getIsFriend())) {
                        intent = new Intent(SearchResultActivity.this, StrangerDelActivity.class);
                    } else {
                        intent = new Intent(SearchResultActivity.this, FriendDetailActivity.class);
                    }
                    intent.putExtra("id", friend.getUserId());
                    startActivity(intent);
                }
            });
            mStrangerAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    mFriends = new ArrayList<>();
                    page++;
                    isLoadMore=true;
                    getData();
                }
            },rv_friend);
            rv_friend.setLayoutManager(new LinearLayoutManager(this));
            rv_friend.setAdapter(mStrangerAdapter);
            getData();
        } else if (type.equals("1")) {//群组搜索
            lv_result.setVisibility(View.VISIBLE);
            searchGroup();
        }
        lv_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (type.equals("0")) {//好友搜索
                    Friend friend = (Friend) mFriendAdapter.getItem(position);
                    Intent intent;
                    if ("0".equals(friend.getIsFriend())) {
                        intent = new Intent(SearchResultActivity.this, StrangerDelActivity.class);
                    } else {
                        intent = new Intent(SearchResultActivity.this, FriendDetailActivity.class);
                    }
                    intent.putExtra("id", friend.getUserId());
                    startActivity(intent);
                } else if (type.equals("1")) {//群组搜索
                    Group group = (Group) mGroupAdapter.getItem(position);
                    Intent intent = new Intent(SearchResultActivity.this, AddGroupDetailActivity.class);
                    intent.putExtra("group", group);
                    startActivity(intent);
                }
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    content = et_search.getEditableText().toString();
                    page=1;
                    mFriends.clear();
                    if (type.equals("0")) {//好友搜索
                        getData();
                    } else if (type.equals("1")) {//群组搜索
                        searchGroup();
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void getData() {

        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        if (!TextUtils.isEmpty(minAge))
            params.put("minAge", Integer.parseInt(minAge));
        if (!TextUtils.isEmpty(maxAge))
            params.put("maxAge", Integer.parseInt(maxAge));
        if (!TextUtils.isEmpty(sex))
            params.put("sex", sex);
        if (!TextUtils.isEmpty(province))
            params.put("province", province);
        if (!TextUtils.isEmpty(city))
            params.put("city", city);
        if (!TextUtils.isEmpty(area))
            params.put("area", area);
        if (!TextUtils.isEmpty(brand))
            params.put("brand", brand);
        if (!TextUtils.isEmpty(content)) {
            params.put("keyWord", content);
        }
        Log.e("friend",new Gson().toJson(params));
        HttpProxy.obtain().get(PlatformContans.Chat.searchFriendByKeyWord, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("friend",  result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<Friend> friends=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Friend friend = new Gson().fromJson(item.toString(), Friend.class);
                        friends.add(friend);
                        mFriends.add(friend);
                    }
                    if (isLoadMore) {
                        isLoadMore = false;
                        mStrangerAdapter.addData(friends);
                        if (data.length() == 0) {
                            mStrangerAdapter.loadMoreEnd(true);
                        } else {
                            mStrangerAdapter.loadMoreComplete();
                        }
                    } else {
                        mStrangerAdapter.setNewData(mFriends);

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

    private void searchGroup() {
        Map<String, Object> params = new HashMap<>();
        params.put("keyWord", content);
        UserInfo userInfo = MyApplication.getUserInfo();
        if (userInfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.searchCrowdsByKeyWord, params, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("group", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        List<Group> groups = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            Group friend = new Gson().fromJson(item.toString(), Group.class);
                            groups.add(friend);
                        }
                        mGroupAdapter = new GroupAdapter(SearchResultActivity.this, groups);
                        lv_result.setAdapter(mGroupAdapter);
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
