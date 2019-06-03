package com.example.yunchebao.rongcloud.activity.contact;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.costans.PlatformContans;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.example.yunchebao.mytag.ChooseTagUserActivity;
import com.example.yunchebao.mytag.ContactAdapter;
import com.example.yunchebao.mytag.UserEntity;
import com.example.yunchebao.net.NetUtils;
import com.example.yunchebao.net.OnMessageReceived;
import com.example.yunchebao.rongcloud.sidebar.ContactModel;
import com.example.yunchebao.sidebar.indexablerv.IndexableAdapter;
import com.example.yunchebao.sidebar.indexablerv.IndexableLayout;
import com.google.gson.Gson;
import com.payencai.library.util.ToastUtil;
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
public class ContactsFragment extends Fragment {
    @BindView(R.id.main_search)
    EditText main_search;
    private ContactAdapter mAdapter;
    List<UserEntity> userEntities;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        userEntities = new ArrayList<>();
        main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    String keytag = main_search.getText().toString().trim();
                    if (TextUtils.isEmpty(keytag)) {
                        Toast.makeText(getContext(), "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // 搜索功能主体
                    List<UserEntity> entities = new ArrayList<>();
                    for (int i = 0; i < userEntities.size(); i++) {
                        if (userEntities.get(i).getName().contains(keytag)) {
                            entities.add(userEntities.get(i));
                        }
                    }
                    userEntities.clear();
                    userEntities.addAll(entities);
                    mAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        main_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 0) {
                    userEntities.clear();
                    getContacts();
                }
            }
        });
        setList();
        getContacts();
    }

    private void getContacts() {
        NetUtils.getInstance().get(MyApplication.token, PlatformContans.Chat.getMyFriendList, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Log.e("getContacts", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        UserEntity userEntity = new Gson().fromJson(item.toString(), UserEntity.class);
                        userEntity.setShow(false);
                        userEntities.add(userEntity);
                        UserInfo userInfo=new UserInfo(userEntity.getUserId(),userEntity.getName(), Uri.parse(userEntity.getHeadPortrait()));
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }

    private void setList() {
        indexableLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ContactAdapter(getContext());
        indexableLayout.setAdapter(mAdapter);
        mAdapter.setDatas(userEntities);

        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    UserEntity userEntity=userEntities.get(originalPosition);
                    Intent intent;
                    switch (userEntity.getUserType()){
                        case 1:
                            intent=new Intent(getContext(),FriendDetailActivity.class);
                            intent.putExtra("id",userEntity.getUserId());
                            startActivity(intent);
                            break;
                        case 2:
                            intent=new Intent(getContext(), ShopInfoActivity.class);
                            intent.putExtra("id",userEntity.getUserId());
                            startActivity(intent);
                            break;
                    }

                } else {
                    // ToastUtil.showToast(ChooseTagUserActivity.this, "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                }
            }
        });

    }
}
