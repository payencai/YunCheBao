package com.example.yunchebao.drive.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.costans.PlatformContans;
import com.example.yunchebao.drive.adapter.DriverCommentAdapter;
import com.example.yunchebao.drive.model.SubstitubeComment;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.http.HttpProxy;
import com.http.ICallBack;
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

public class DriverCommentActivity extends AppCompatActivity {
    @BindView(R.id.rv_order)
    RecyclerView rv_comment;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    List<SubstitubeComment> mSubstitubeComments;
    DriverCommentAdapter mDriverCommentAdapter;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_comment);
        ButterKnife.bind(this);
        ImmersionBar.with(this).autoDarkModeEnable(true).fitsSystemWindows(true).statusBarColor(R.color.white).init();
        id = getIntent().getStringExtra("id");
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

                mSubstitubeComments.clear();
                mDriverCommentAdapter.setNewData(mSubstitubeComments);
                getData();
            }
        });
        mSubstitubeComments = new ArrayList<>();
        mDriverCommentAdapter = new DriverCommentAdapter(R.layout.item_coash_comment, mSubstitubeComments);
        rv_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_comment.setAdapter(mDriverCommentAdapter);
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", id);
        //  params.put("page", page);
        HttpProxy.obtain().get(PlatformContans.SubstituteDriving.getSubstituteDrivingCommentByOrderId, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                refresh.finishRefresh();
                Log.e("detail", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    SubstitubeComment driveMan = new Gson().fromJson(data.toString(), SubstitubeComment.class);
                    mSubstitubeComments.add(driveMan);
                    mDriverCommentAdapter.setNewData(mSubstitubeComments);
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
