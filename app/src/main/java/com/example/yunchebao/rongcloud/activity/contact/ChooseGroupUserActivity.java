package com.example.yunchebao.rongcloud.activity.contact;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
import com.http.HttpProxy;
import com.http.ICallBack;
import com.newversion.NewTag;
import com.payencai.library.util.ToastUtil;
import com.tool.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseGroupUserActivity extends AppCompatActivity {


    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;
    @BindView(R.id.confirm)
    TextView tv_confirm;
    @BindView(R.id.main_search)
    EditText main_search;
    private ContactAdapter mAdapter;
    List<UserEntity> userEntities;
    List<UserEntity> groupUsers;
    int flag;
    String id;
    ArrayList<String> mSelectUser = new ArrayList<>();
    ArrayList<String> mSelectHead = new ArrayList<>();
    ArrayList<String> mSelectName = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_group_user);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        flag = getIntent().getIntExtra("flag", 0);
        initView();
    }

    private void initView() {
        groupUsers=new ArrayList<>();
        userEntities = new ArrayList<>();
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectUser.size()==0){
                    ToastUtil.showToast(ChooseGroupUserActivity.this,"请至少选择一个联系人");
                    return;
                }
                String userList= StringUtils.listToString2(mSelectUser,',');
                String nameList= StringUtils.listToString2(mSelectName,',');
                String headList= StringUtils.listToString2(mSelectHead,',');
                Log.e("userId",userList);
                Intent intent=new Intent();
                intent.putExtra("userList",userList);
                intent.putExtra("headList",headList);
                intent.putExtra("nameList",nameList);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        main_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    String keytag = main_search.getText().toString().trim();
                    if (TextUtils.isEmpty(keytag)) {
                        Toast.makeText(ChooseGroupUserActivity.this, "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    // 搜索功能主体
                    List<UserEntity> entities=new ArrayList<>();
                    for (int i = 0; i <userEntities.size(); i++) {
                        if(userEntities.get(i).getName().contains(keytag)){
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
                if(s.toString().length()==0){
                    userEntities.clear();
                    getContacts();
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setList();
        getGroupDetail();
        if (flag == 1) {

             //添加
        } else {


        }
        //getContacts();
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
                        UserEntity userEntity=new Gson().fromJson(item.toString(),UserEntity.class);
                        userEntity.setShow(true);
                        userEntities.add(userEntity);
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
                        userEntity.setShow(true);
                        userEntities.add(userEntity);
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
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ContactAdapter(this);
        indexableLayout.setAdapter(mAdapter);
        mAdapter.setDatas(userEntities);

        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    UserEntity userEntity=userEntities.get(originalPosition);
                    if(userEntities.get(originalPosition).isSelect()){
                        mSelectUser.remove(userEntity.getUserId());
                        mSelectHead.remove(userEntity.getHeadPortrait());
                        mSelectName.remove(userEntity.getName());
                        userEntities.get(originalPosition).setSelect(false);
                    }else{
                        userEntities.get(originalPosition).setSelect(true);
                        mSelectUser.add(userEntity.getUserId());
                        mSelectHead.add(userEntity.getHeadPortrait());
                        mSelectName.add(userEntity.getName());
                    }
                    mAdapter.notifyItemChange(currentPosition);

                } else {
                    //ToastUtil.showToast(ChooseGroupUserActivity.this, "选中Header/Footer:" + entity.getNick() + "  当前位置:" + currentPosition);
                }
            }
        });

    }
}
