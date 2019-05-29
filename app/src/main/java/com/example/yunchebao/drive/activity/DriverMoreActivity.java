package com.example.yunchebao.drive.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.drive.adapter.DriverMoreAdapter;
import com.example.yunchebao.drive.model.DriveMan;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverMoreActivity extends AppCompatActivity {

    @BindView(R.id.rv_order)
    RecyclerView rv_comment;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    List<DriveMan> mSubstitubeComments;
    DriverMoreAdapter mDriverCommentAdapter;
    String id;
    int page=1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_more);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id=getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                mSubstitubeComments.clear();
                mDriverCommentAdapter.setNewData(mSubstitubeComments);
                getData();
            }
        });
        mSubstitubeComments=new ArrayList<>();
        mDriverCommentAdapter=new DriverMoreAdapter(R.layout.item_coash,mSubstitubeComments);
        mDriverCommentAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_comment);
        mDriverCommentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DriveMan driveMan= (DriveMan) adapter.getItem(position);
                Intent intent=new Intent(DriverMoreActivity.this,DriverDetailActivity.class);
                intent.putExtra("id",driveMan.getId());
                startActivity(intent);
            }
        });
        mDriverCommentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DriveMan driveMan= (DriveMan) adapter.getItem(position);
                if(view.getId()==R.id.tv_select){
                    Intent intent=new Intent();
                    intent.putExtra("data",driveMan);
                    setResult(0,intent);
                    finish();
                }
            }
        });
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mDriverCommentAdapter);
        getData();
    }
    private void getData(){
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", id);
        params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDriverListForApp, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh.finishRefresh();
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray data = jsonObject.getJSONArray("data");
                    List<DriveMan> substitubeComments=new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        DriveMan driveMan = new Gson().fromJson(item.toString(), DriveMan.class);
                        mSubstitubeComments.add(driveMan);
                        substitubeComments.add(driveMan);
                    }
                    if(isLoadMore){
                        isLoadMore=false;
                        if(data.length()==0){
                            mDriverCommentAdapter.loadMoreEnd(true);
                        }else{
                            mDriverCommentAdapter.addData(substitubeComments);
                            mDriverCommentAdapter.loadMoreComplete();
                        }
                    }else{
                        mDriverCommentAdapter.setNewData(mSubstitubeComments);
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
