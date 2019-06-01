package com.example.yunchebao.rongcloud.activity.contact;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.nanchen.wavesidebar.Trans2PinYinUtil;
import com.nanchen.wavesidebar.WaveSideBarView;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
import com.example.yunchebao.rongcloud.sidebar.ContactsAdapter;
import com.example.yunchebao.rongcloud.sidebar.PinnedHeaderDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xihubao.ShopInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendListFragment extends Fragment {

    private List<ContactModel> mContactModels;
    private List<ContactModel> mShowModels;
    private RecyclerView mRecyclerView;
    private WaveSideBarView mWaveSideBarView;
    private EditText mSearchEditText;
    private ContactsAdapter mAdapter;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    public FriendListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_friend_list, container, false);
        ButterKnife.bind(this,view);
        initData(view);
        getContacts();
        return view;
    }
    private void getContacts(){
        com.vipcenter.model.UserInfo userinfo= MyApplication.getUserInfo();
        if(userinfo!=null)
            HttpProxy.obtain().get(PlatformContans.Chat.getMyFriendList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    refresh.finishRefresh();
                    Log.e("group",result);
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        JSONArray data=jsonObject.getJSONArray("data");
                        for (int i = 0; i <data.length() ; i++) {
                            JSONObject item=data.getJSONObject(i);
                            ContactModel contactModel=new ContactModel(item.getString("name"));
                            contactModel.setHeadPortrait(item.getString("headPortrait"));
                            contactModel.setHxAccount(item.getString("hxAccount"));
                            contactModel.setId(item.getString("id"));
                            contactModel.setUserType(item.getInt("userType"));
                            contactModel.setUserId(item.getString("userId"));
                            contactModel.setName(item.getString("name"));
                            contactModel.setIsNotice(item.getString("isNotice"));
                            contactModel.setNickName(item.getString("nickName"));
                            contactModel.setSelect(false);
                            mContactModels.add(contactModel);
                            UserInfo userInfo=new UserInfo(contactModel.getUserId(),contactModel.getName(), Uri.parse(contactModel.getHeadPortrait()));
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        }
                        //mContactModels.addAll(ContactModel.getContacts());
                        mShowModels.addAll(mContactModels);
                        mAdapter=new ContactsAdapter(mShowModels);
                        mAdapter.setShow(false);
                        mAdapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position, ImageView imageView) {
                                ContactModel contactModel=mShowModels.get(position);
                                Intent intent;
                                Log.e("userType",contactModel.getUserType()+"");
                                switch (contactModel.getUserType()){
                                    case 1:
                                        intent=new Intent(getContext(),FriendDetailActivity.class);
                                        intent.putExtra("id",contactModel.getUserId());
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        intent=new Intent(getContext(), ShopInfoActivity.class);
                                        intent.putExtra("id",contactModel.getUserId());
                                        startActivity(intent);
                                        break;
                                }
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



    private void initData(View view) {
        mContactModels = new ArrayList<>();
        mShowModels = new ArrayList<>();
        // RecyclerView设置相关
        mRecyclerView = (RecyclerView) view.findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);

        // 侧边设置相关
        mWaveSideBarView = (WaveSideBarView) view.findViewById(R.id.main_side_bar);
        mWaveSideBarView.setOnSelectIndexItemListener(new WaveSideBarView.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String letter) {
                for (int i=0; i<mContactModels.size(); i++) {
                    if (mContactModels.get(i).getIndex().equals(letter)) {
                        ((LinearLayoutManager) mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mShowModels.clear();
                mContactModels.clear();
                getContacts();
            }
        });
        // 搜索按钮相关
        mSearchEditText = (EditText) view.findViewById(R.id.main_search);
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
                if(mAdapter!=null)
                mAdapter.notifyDataSetChanged();
            }
        });
    }


}
