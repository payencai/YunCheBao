package com.newversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.rongcloud.sidebar.ContactModel;
import com.rongcloud.sidebar.ContactsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseUserActivity extends AppCompatActivity {
     ArrayList<ContactModel> mContactModels;
     ContactsAdapter mAdapter;
     ArrayList<ContactModel> mSelect=new ArrayList<>();
    @BindView(R.id.main_recycler)
    RecyclerView rv_user;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mContactModels=new ArrayList<>();
        mAdapter=new ContactsAdapter(mContactModels);
        mAdapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, ImageView imageView) {
                ContactModel contactModel = mContactModels.get(position);
                mContactModels.remove(contactModel);
                if (!contactModel.isSelect()) {
                    mSelect.add(contactModel);
                    //userid.add(contactModel.getUserId());
                    imageView.setImageResource(R.mipmap.select);
                    contactModel.setSelect(true);
                } else {
                    mSelect.remove(contactModel);
                   // userid.remove(contactModel.getUserId());
                    imageView.setImageResource(R.mipmap.unselect);
                    contactModel.setSelect(false);
                    //mAdapter.notifyDataSetChanged();
                }
                mContactModels.add(position, contactModel);
                mAdapter.notifyDataSetChanged();
            }
        });
        rv_user.setLayoutManager(new LinearLayoutManager(this));
        rv_user.setAdapter(mAdapter);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelect.size()==0){
                    ToastUtil.showToast(ChooseUserActivity.this,"请至少选择一个成员");
                    return;
                }
                Intent intent=new Intent();
                intent.putExtra("user",mSelect);
                setResult(0,intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getContacts();
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
}
