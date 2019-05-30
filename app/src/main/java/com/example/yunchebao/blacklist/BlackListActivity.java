package com.example.yunchebao.blacklist;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yunchebao.MyApplication;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.entity.UserMsg;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class BlackListActivity extends AppCompatActivity {
    BlackuserAdapter BlackuserAdapter;
    @BindView(R.id.rv_black)
    RecyclerView rv_black;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    List<BlackUser> mBlackUsers;
    UserMsg mUserMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mBlackUsers.clear();
                BlackuserAdapter.setNewData(mBlackUsers);
                getData();
            }
        });
        mBlackUsers=new ArrayList<>();
        BlackuserAdapter=new BlackuserAdapter(R.layout.item_black_user,mBlackUsers);
        BlackuserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BlackUser blackUser= (BlackUser) adapter.getItem(position);
                if(R.id.btnDelete==view.getId()){
                      RongIM.getInstance().removeFromBlacklist(blackUser.getUserId(), new RongIMClient.OperationCallback() {
                          @Override
                          public void onSuccess() {
                              ToastUtils.showLongToast(BlackListActivity.this,"移除成功");
                          }

                          @Override
                          public void onError(RongIMClient.ErrorCode errorCode) {

                          }
                      });
                }
            }
        });
        rv_black.setLayoutManager(new LinearLayoutManager(this));
        rv_black.setAdapter(BlackuserAdapter);
        getData();

    }
    private void getData(){
        RongIM.getInstance().getBlacklist(new RongIMClient.GetBlacklistCallback() {
            @Override
            public void onSuccess(String[] strings) {
                if(strings!=null)
                    for (int i = 0; i <strings.length ; i++) {
                        String userId=strings[i];
                        getDetail(userId);
                    }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
    private void getDetail(String id){
        Map<String,Object> params=new HashMap<>();
        params.put("userId",id);
        HttpProxy.obtain().get(PlatformContans.User.getUserResultById, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refreshLayout.finishRefresh();
                Log.e("detail",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONObject data=jsonObject.getJSONObject("data");
                    mUserMsg=new Gson().fromJson(data.toString(), UserMsg.class);
                    BlackUser blackUser=new BlackUser();
                    blackUser.setUserId(id);
                    blackUser.setName(mUserMsg.getName());
                    blackUser.setHeader(mUserMsg.getHeadPortrait());
                    mBlackUsers.add(blackUser);
                    BlackuserAdapter.setNewData(mBlackUsers);
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
