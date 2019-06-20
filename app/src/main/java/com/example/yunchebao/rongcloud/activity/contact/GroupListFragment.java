package com.example.yunchebao.rongcloud.activity.contact;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yunchebao.MyApplication;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.rongcloud.model.ApplyGroup;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.example.yunchebao.rongcloud.adapter.GroupAdapter;
import com.example.yunchebao.rongcloud.model.Group;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.tv_unhandle)
    TextView tv_unhandle;
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
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mGroups.clear();
                getData();
            }
        });
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
                intent.putExtra("id",mGroups.get(position).getHxCrowdId()+"");
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
                    refresh.finishRefresh();
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
    int count=0;
    private void getGroupApplyCount() {

        HttpProxy.obtain().get(PlatformContans.Chat.getCrowdApplyList, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {

                Log.e("groupapply", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        ApplyGroup applyFriend = new Gson().fromJson(item.toString(), ApplyGroup.class);
                        if(applyFriend.getState()==0){
                            count++;
                        }
                    }
                    if(count>0){
                        tv_unhandle.setText(count+"");
                        tv_unhandle.setVisibility(View.VISIBLE);
                    }else{
                        tv_unhandle.setVisibility(View.GONE);
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

    @Override
    public void onResume() {
        super.onResume();
        count=0;
        getGroupApplyCount();
    }
}
