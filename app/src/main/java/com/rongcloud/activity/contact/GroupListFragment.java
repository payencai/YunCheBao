package com.rongcloud.activity.contact;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.application.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.rongcloud.adapter.GroupAdapter;
import com.rongcloud.model.ApplyFriend;
import com.rongcloud.model.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends Fragment {
    @BindView(R.id.groupApply)
    RelativeLayout groupApply;
    @BindView(R.id.lv_group)
    ListView lv_group;
    GroupAdapter mGroupAdapter;
    List<Group> mGroups=new ArrayList<>();
    public GroupListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view=inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
    private void initView(View view){
        groupApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(),GroupApplyActivity.class),1);
            }
        });
        mGroupAdapter=new GroupAdapter(getContext(),mGroups);

        lv_group.setAdapter(mGroupAdapter);
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if(mGroups.get(position).isMyGroup()){
                     intent=new Intent(getContext(),MyGroupDetailActivity.class);
                }else{
                     intent=new Intent(getContext(),GroupDetailActivity.class);
                }
                intent.putExtra("group",mGroups.get(position));
                startActivity(intent);
            }
        });
        getData();
    }
    private void getData(){
        mGroups.clear();
        final com.vipcenter.model.UserInfo userinfo = MyApplication.getUserInfo();
        if (userinfo != null)
            HttpProxy.obtain().get(PlatformContans.Chat.getCrowdsList, MyApplication.token, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Log.e("apply", result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            Group applyFriend = new Gson().fromJson(item.toString(), Group.class);
                            if(userinfo.getId().equals(applyFriend.getCrowdUserId())){
                                applyFriend.setMyGroup(true);
                            }
                            mGroups.add(applyFriend);
                        }
                        mGroupAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(String error) {
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGroups.clear();
        getData();
    }
}
